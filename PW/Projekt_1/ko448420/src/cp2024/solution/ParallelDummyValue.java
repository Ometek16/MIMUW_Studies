package cp2024.solution;

import cp2024.circuit.CircuitValue;

public class ParallelDummyValue extends ParallelCircuitValue {
    ParallelDummyValue() {
        super(null, null);
    }

    @Override
    public boolean getValue() throws InterruptedException {
        throw new InterruptedException();
    }
}
