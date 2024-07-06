package pl.edu.mimuw.expression;

public class Negation extends UnaryExpression {

    public Negation(Expression operand) {
        super(operand, "¬");
    }

    @Override
    protected int operation(int operand) {
        return -operand;
    }
}
