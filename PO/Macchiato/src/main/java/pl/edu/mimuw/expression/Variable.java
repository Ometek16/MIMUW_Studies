package pl.edu.mimuw.expression;

import pl.edu.mimuw.Variables;

public class Variable extends Expression {
    private final char varName;

    public Variable(char varName) {
        this.varName = varName;
    }

    @Override
    public int compute(Variables variables) throws Exception {
        if (!variables.isMacchiato(varName))
            throw new Exception("[Exception] Invalid variable name");
        if (!variables.ifLegal(varName))
            throw new Exception("[Exception] Variable not declared");
        return variables.getVariableValue(varName);
    }

    @Override
    public String toString() {
        return "" + this.varName;
    }
}
