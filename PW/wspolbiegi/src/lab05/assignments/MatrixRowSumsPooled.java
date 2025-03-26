package lab05.assignments;

import lab05.examples.Squares;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.IntBinaryOperator;


public class MatrixRowSumsPooled {
    private static final int N_ROWS = 10;
    private static final int N_COLUMNS = 100;
    private static final int N_THREADS = 4;

    private static IntBinaryOperator matrixDefinition = (row, col) -> {
        int a = 2 * col + 1;
        return (row + 1) * (a % 4 - 2) * a;
    };

    private static void printRowSumsSequentially() {
        for (int r = 0; r < N_ROWS; ++r) {
            int sum = 0;
            for (int c = 0; c < N_COLUMNS; ++c) {
                sum += matrixDefinition.applyAsInt(r, c);
            }
            System.out.println(r + " -> " + sum);
        }
    }

    public static void printRowSumsInParallel() throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(N_THREADS);
        LinkedBlockingQueue<Future<Integer>> queue = new LinkedBlockingQueue<>();
        try {
            for (int r = 0; r < N_ROWS; ++r) {
                for (int c = 0; c < N_COLUMNS; ++c) {
                    Callable<Integer> task = new Task(r, c);
                    Future<Integer> future = pool.submit(task);
                    queue.put(future);
                }
            }
            for (int r = 0; r < N_ROWS; ++r) {
                int sum = 0;
                for (int c = 0; c < N_COLUMNS; ++c) {
                    Future<Integer> future = queue.take();
                    try {
                        sum += future.get();
                    } catch (ExecutionException e) {
                        System.err.println("Task execution failed.");
                    }
                }
                System.out.println(r + " -> " + sum);
            }
        } finally {
            pool.shutdown();
        }
    }

    private static class Task implements Callable<Integer> {
        private final int rowNo;
        private final int columnNo;

        private Task(int rowNo, int columnNo) {
            this.rowNo = rowNo;
            this.columnNo = columnNo;
        }

        @Override
        public Integer call() {
            return matrixDefinition.applyAsInt(rowNo, columnNo);
        }
    }

    public static void main(String[] args) {
        try {
            System.out.println("-- Sequentially --");
            printRowSumsSequentially();
            System.out.println("-- In a FixedThreadPool --");
            printRowSumsInParallel();
            System.out.println("-- End --");
        } catch (InterruptedException e) {
            System.err.println("Main interrupted.");
        }
    }
}
