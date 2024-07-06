package pl.edu.mimuw.expression;

public class Absolute extends UnaryExpression {

    public Absolute(Expression operand) {
        super(operand, "abs");
    }

    @Override
    protected int operation(int operand) {
        return (operand > -operand ? operand : -operand);
    }
}
