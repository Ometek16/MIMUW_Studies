package pl.edu.mimuw.expression;

import pl.edu.mimuw.Variables;

public class Constant extends Expression {

    private final int varValue;

    public Constant(int varValue) {
        this.varValue = varValue;
    }

    @Override
    public int compute(Variables variables) {
        return varValue;
    }

    @Override
    public String toString() {
        return Integer.toString(varValue);
    }
}
