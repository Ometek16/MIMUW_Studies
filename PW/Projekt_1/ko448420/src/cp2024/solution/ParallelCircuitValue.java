package cp2024.solution;

import cp2024.circuit.*;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class ParallelCircuitValue implements CircuitValue, Runnable {
    private final CircuitNode node;
    private final ParallelCircuitValue parent;
    private NodeType type;

    private volatile Boolean value = null;
    private volatile boolean finished = false;

    private final Semaphore solved = new Semaphore(0);
    private final Semaphore action = new Semaphore(0);

    private final LinkedBlockingDeque<Boolean> children_results;
    private volatile int threshold = -1;
    private volatile int finished_children = 0;
    private int children_count;

    private volatile Semaphore GT_mutex = new Semaphore(1);

    List<ParallelCircuitValue> circuits = new ArrayList<>();
    List<Thread> threads = new ArrayList<>();

    ParallelCircuitValue(CircuitNode node, ParallelCircuitValue parent) {
        children_results = new LinkedBlockingDeque<>();
        this.node = node;
        this.parent = parent;

        try {
            this.children_count = node.getArgs().length;
            this.type = node.getType();
        } catch (Exception e) {
            this.children_count = 0;
            this.type = NodeType.NOT;   // Dummy value
        }
    }

    public void start_children() throws InterruptedException {
        for (CircuitNode arg : node.getArgs()) {
            ParallelCircuitValue circuit = new ParallelCircuitValue(arg, this);
            circuits.add(circuit);

            Thread thread = new Thread(circuit);
            threads.add(thread);
            thread.start();
        }
    }

    @Override
    public boolean getValue() throws InterruptedException {
        if (!finished) {
            solved.acquire();
        }
        if (value == null) {
            throw new InterruptedException();
        }
        return value;
    }

    @Override
    public void run() {
        try {
            // System.out.println("Starting" + Thread.currentThread().getName() + " " + node.getType());
            if (node.getType() == NodeType.LEAF) {
                this.value = ((LeafNode) node).getValue();
                return;
            }

            switch (node.getType()) {
                case IF -> solveIF();
                case NOT -> solveNOT();
                case AND -> solveAND();
                case OR -> solveOR();
                case GT -> solveGT(((ThresholdNode) node).getThreshold());
                case LT -> solveLT(((ThresholdNode) node).getThreshold());
                default -> throw new RuntimeException("Illegal type " + node.getType());
            };
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
            // System.out.println("Interrupted " + Thread.currentThread().getName());
        } finally {
            for (Thread thread : threads) {
                thread.interrupt();
            }

            parent.addChildResult(this.value);
            this.finished = true;
            solved.release();
            if (!Thread.interrupted())
                parent.wakeUp();

            // System.out.println("Finished" + Thread.currentThread().getName());
        }
    }

    public boolean isFinished() {
        return this.finished;
    }

    public void wakeUp() {
        try {
            GT_mutex.acquire();
            switch (this.type) {
                case IF, NOT -> {
                    action.release();
                }
                case GT -> {

                    boolean current_result = children_results.take();

                    this.finished_children += 1;
                    if (current_result) {
                        this.threshold -= 1;
                    }

                    // System.out.println(Thread.currentThread().getName() + " -> " + this.threshold + " Finished children: " + this.finished_children + " / " +  this.children_count);

                    if (this.threshold == -1) {
                        action.release();
                    }
                    else if (this.threshold >= this.children_count - this.finished_children) {
                        action.release();
                    }
                }
            }
        } catch (InterruptedException ignored) {
            action.release();
        }
        finally {
            GT_mutex.release();
        }
    }

    public void addChildResult(Boolean result) {
        if (result != null)
            children_results.add(result);
    }

    private void solveIF() throws InterruptedException {
        this.start_children();

        while (true) {
            action.acquire();

            if (circuits.get(0).isFinished()) {
                int correct = circuits.get(0).getValue() ? 1 : 2;

                threads.get(3 - correct).interrupt();
                while (!circuits.get(correct).isFinished()) {
                    action.acquire();
                }

                this.value = circuits.get(correct).getValue();
                break;
            }

            if (circuits.get(1).isFinished() && circuits.get(2).isFinished()) {
                if (circuits.get(1).getValue() == circuits.get(2).getValue()) {
                    this.value = circuits.get(1).getValue();
                    break;
                }
            }
        }
    }

    private void solveNOT() throws InterruptedException {
        this.start_children();

        action.acquire();
        this.value = !children_results.take();
    }

    private void solveAND() throws InterruptedException {
        this.type = NodeType.GT;
        solveGT(this.children_count - 1);
    }

    private void solveOR() throws InterruptedException {
        this.type = NodeType.GT;
        solveGT(0);
    }

    private void solveLT(int threshold) throws InterruptedException {
        if (threshold == 0) {
            this.value = false;
            return;
        }

        this.type = NodeType.GT;
        solveGT(threshold - 1);
        this.value = !this.value;
    }

    private void solveGT(int threshold) throws InterruptedException {
        this.threshold = threshold;

        if (threshold >= this.children_count) {
            this.value = false;
            return;
        }

        this.start_children();

        action.acquire();

        this.value = (this.threshold == -1);
    }
}
