package pl.edu.mimuw;

public class Block extends Instruction {
    private Declare[] declarations;
    private Instruction[] instructions;

    public Block(Declare[] declaration) {
        this.declarations = declaration;
        this.instructions = new Instruction[0];
        this.instructionName = "begin block";
    }

    public Block(Instruction[] instructions) {
        this.declarations = new Declare[0];
        this.instructions = instructions;
        this.instructionName = "begin block";
    }

    public Block(Declare[] declarations, Instruction[] instructions) {
        this.declarations = declarations;
        this.instructions = instructions;
        this.instructionName = "begin block";
    }

    @Override
    public void run(Program program) {
        int declarationsSize = declarations.length;
        int instructionsSize = instructions.length;

        program.newVariables();
        program.addInstruction(new EndOfBlock());

        for (int i = instructionsSize - 1; i >= 0; i--)
            program.addInstruction(instructions[i]);

        for (int i = declarationsSize - 1; i >= 0; i--)
            program.addInstruction(declarations[i]);
    }
}
