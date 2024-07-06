package pl.edu.mimuw;

public abstract class Instruction {
    protected String instructionName;

    public abstract void run(Program program) throws Exception;

    @Override
    public String toString() {
        return instructionName;
    }
}
