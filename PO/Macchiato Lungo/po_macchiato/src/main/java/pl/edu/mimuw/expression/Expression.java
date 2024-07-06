package pl.edu.mimuw.expression;

import pl.edu.mimuw.instruction.Variables;

public abstract class Expression {

    public abstract int compute(Variables variables) throws Exception;

    @Override
    public abstract String toString();
}
