package pl.edu.mimuw;

import pl.edu.mimuw.expression.*;

public class For extends Instruction {
    private char varName;
    private Expression expression;
    private Instruction[] instructions;

    public For(char varName, Expression expression, Instruction[] instructions) {
        this.varName = varName;
        this.expression = expression;
        this.instructions = instructions;
        this.instructionName = "for " + varName + " " + expression.toString();
    }

    @Override
    public void run(Program program) throws Exception {
        Variables variables = program.getLastVariables();
        
        try {
            int endValue = expression.compute(variables);
            for (int i = endValue - 1; i >= 0; i--) {
                program.addInstruction(new Block(new Declare[] {new Declare(this.varName, new Constant(i))}, instructions));
            }
        } catch (Exception e) {
            throw e;
        }
    }
}
