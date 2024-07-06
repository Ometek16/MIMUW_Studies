package pl.edu.mimuw;

import pl.edu.mimuw.expression.*;

public class If extends Instruction {
    private Expression expression1;
    private Expression expression2;
    private String operation;
    private Instruction[] ifInstructions;
    private Instruction[] elseInstructions;

    public If(Expression expression1, String operation, Expression expression2, Instruction[] ifInstructions, Instruction[] elseInstructions) {
        this.expression1 = expression1;
        this.operation = operation;
        this.expression2 = expression2;
        this.ifInstructions = ifInstructions;
        this.elseInstructions = elseInstructions;
        this.instructionName = "if (" + expression1.toString() + " " + operation + " " + expression2.toString() + ")";
    }
    
    public If(Expression expression1, String operation, Expression expression2, Instruction[] ifInstructions) {
        this.expression1 = expression1;
        this.operation = operation;
        this.expression2 = expression2;
        this.ifInstructions = ifInstructions;
        this.elseInstructions = new Instruction[0];
        this.instructionName = "if (" + expression1.toString() + " " + operation + " " + expression2.toString() + ")";
    }

    @Override
    public void run(Program program) throws Exception {
        try {
            Variables variables = program.getLastVariables();

            int result1 = expression1.compute(variables);
            int result2 = expression2.compute(variables);

            boolean isTrue = false;

            if (operation.equals("="))
                isTrue = (result1 == result2);
            else if (operation.equals("<>"))
                isTrue = (result1 != result2);
            else if (operation.equals(">"))
                isTrue = (result1 > result2);
            else if (operation.equals(">="))
                isTrue = (result1 >= result2);
            else if (operation.equals("<"))
                isTrue = (result1 < result2);
            else if (operation.equals("<="))
                isTrue = (result1 <= result2);
            else
                throw new Exception("[Syntax error] Invalid comparator");

            if (isTrue) {
                int instructionsSize = ifInstructions.length;
                for (int i = instructionsSize - 1; i >= 0; i--)
                    program.addInstruction(ifInstructions[i]);
            } else {
                int instructionsSize = elseInstructions.length;
                for (int i = instructionsSize - 1; i >= 0; i--)
                    program.addInstruction(elseInstructions[i]);    
            }

        } catch (Exception e) {
            throw e;
        }
    }
}
