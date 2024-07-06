package pl.edu.mimuw.expression;

public class Signum extends UnaryExpression {

    public Signum(Expression operand) {
        super(operand, "sgn");
    }

    @Override
    protected int operation(int operand) {
        return Integer.compare(operand, 0);
    }
}
