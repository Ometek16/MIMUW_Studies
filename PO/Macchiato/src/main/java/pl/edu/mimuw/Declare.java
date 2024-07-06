package pl.edu.mimuw;

import pl.edu.mimuw.expression.*;

public class Declare extends Instruction {
    private char varName;
    private Expression expression;

    private final String EXCEPTION_DECLARE = "[Exception] Variable already declared";
    private final String EXCEPTION_VARIABLE = "[Exception] Invalid variable name";

    public Declare(char varName, Expression expression) {
        this.varName = varName;
        this.expression = expression;
        this.instructionName = "var " + varName + " " + expression.toString();
    }

    @Override
    public void run(Program program) throws Exception {
        Variables variables = program.getLastVariables();

        if (!variables.isMacchiato(this.varName))
            throw new Exception(EXCEPTION_VARIABLE);
        if (variables.ifDeclared(this.varName)) {
            throw new Exception(EXCEPTION_DECLARE);
        }

        try {
            int varValue = expression.compute(variables);
            variables.newVariable(this.varName, varValue);
        } catch (Exception e) {
            throw e;
        }
    }
}
