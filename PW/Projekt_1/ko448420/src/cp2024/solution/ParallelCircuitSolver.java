package cp2024.solution;

import cp2024.circuit.CircuitSolver;
import cp2024.circuit.CircuitValue;
import cp2024.circuit.Circuit;

import java.util.ArrayList;
import java.util.List;

public class ParallelCircuitSolver implements CircuitSolver {
    List<ParallelCircuitValue> circuits = new ArrayList<>();
    List<Thread> threads = new ArrayList<>();
    private Boolean acceptComputations = true;

    @Override
    public CircuitValue solve(Circuit c) {
        if (!acceptComputations) {
            return new ParalellBrokenCircuitValue();
        }

        ParallelCircuitValue circuit = new ParallelCircuitValue(c.getRoot(), new ParallelDummyValue());
        circuits.add(circuit);

        Thread thread = new Thread(circuit);
        threads.add(thread);
        thread.start();

        return circuit;
    }

    @Override
    public void stop() {
        acceptComputations = false;
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }
}
