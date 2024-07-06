package pl.edu.mimuw.instruction;

import pl.edu.mimuw.expression.*;

public class Assign extends Instruction {
    private char varName;
    private Expression expression;

    private final String EXCEPTION_DECLARE = "[Exception] Variable not declared";
    private final String EXCEPTION_VARIABLE = "[Exception] Invalid variable name";

    public Assign(char varName, Expression expression) {
        this.varName = varName;
        this.expression = expression;
        this.instructionName = varName + " := " + expression.toString();
    }

    @Override
    public void run(Program program) throws Exception {
        Variables variables = program.getLastVariables();

        if (!variables.isMacchiato(this.varName))
            throw new Exception(EXCEPTION_VARIABLE);
        if (!variables.ifLegal(this.varName)) {
            throw new Exception(EXCEPTION_DECLARE);
        }

        try {
            int varValue = expression.compute(variables);
            variables.setVariable(varName, varValue);
        } catch (Exception e) {
            throw e;
        }
    }
}
