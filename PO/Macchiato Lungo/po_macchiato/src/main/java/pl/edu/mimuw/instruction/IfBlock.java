package pl.edu.mimuw.instruction;

import pl.edu.mimuw.expression.*;

public class IfBlock extends Instruction {
    private Expression expression1;
    private Expression expression2;
    private String operation;
    private Block ifBlock;
    private Block elseBlock;

    public IfBlock(Expression expression1, String operation, Expression expression2, Block ifBlock, Block elseBlock) {
        this.expression1 = expression1;
        this.operation = operation;
        this.expression2 = expression2;
        this.ifBlock = ifBlock;
        this.elseBlock = elseBlock;
        this.instructionName = "if (" + expression1.toString() + " " + operation + " " + expression2.toString() + ")";
    }
    
    public IfBlock(Expression expression1, String operation, Expression expression2, Block ifBlock) {
        this.expression1 = expression1;
        this.operation = operation;
        this.expression2 = expression2;
        this.ifBlock = ifBlock;
        this.elseBlock = null;
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
                program.addInstruction(ifBlock);
            } else if (elseBlock != null) {
               program.addInstruction(elseBlock);   
            }

        } catch (Exception e) {
            throw e;
        }
    }
}
