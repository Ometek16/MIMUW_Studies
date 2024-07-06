package pl.edu.mimuw.instruction;

public class BlockEnd extends Instruction {
    public BlockEnd() {
        this.instructionName = "end block";
    }

    @Override
    public void run(Program program) {
        program.removeVariables();
        program.removeProcedures();
    }
}
