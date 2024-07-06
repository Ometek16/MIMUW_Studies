package pl.edu.mimuw;

public class EndOfBlock extends Instruction {
    public EndOfBlock() {
        this.instructionName = "end block";
    }

    @Override
    public void run(Program program) {
        program.deleteVariables();
    }
}
