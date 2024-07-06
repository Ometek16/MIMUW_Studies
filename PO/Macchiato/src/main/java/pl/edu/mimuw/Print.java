package pl.edu.mimuw;

import pl.edu.mimuw.expression.*;

public class Print extends Instruction {
    private Expression expression;

    public Print(Expression expression) {
        this.expression = expression;
        this.instructionName = "print " + expression.toString();
    }

    @Override
    public void run(Program program) throws Exception {
        try {
            System.out.println(expression.compute(program.getLastVariables()));
        } catch (Exception e) {
            throw e;
        }
    }
}
