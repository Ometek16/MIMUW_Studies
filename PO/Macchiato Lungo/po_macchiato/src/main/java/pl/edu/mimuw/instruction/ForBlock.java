package pl.edu.mimuw.instruction;

import pl.edu.mimuw.expression.*;

public class ForBlock extends Instruction {
    private char varName;
    private Expression expression;
    private Block block;

    public ForBlock(char varName, Expression expression, Block block) {
        this.varName = varName;
        this.expression = expression;
        this.block = block;
        this.instructionName = "for " + varName + " " + expression.toString();
    }

    @Override
    public void run(Program program) throws Exception {
        Variables variables = program.getLastVariables();
        
        try {
            int endValue = expression.compute(variables);
            for (int i = endValue - 1; i >= 0; i--) {
                Block currentBlock = this.block.clone();
                currentBlock.addFirstInstruction(new Declare(this.varName, Constant.of(i)));
                program.addInstruction(currentBlock);
            }
        } catch (Exception e) {
            throw e;
        }
    }
}
