package pl.edu.mimuw.expression;

import pl.edu.mimuw.Variables;

public abstract class BinaryExpression extends Expression {

    private final Expression operand1;
    private final Expression operand2;

    protected BinaryExpression(Expression operand1, Expression operand2) {
        this.operand1 = operand1;
        this.operand2 = operand2;
    }

    protected abstract int operation(int operand1, int operand2) throws Exception;

    @Override
    public int compute(Variables variables) throws Exception {
        try {
            return operation(operand1.compute(variables), operand2.compute(variables));
        } catch (Exception e) {
            throw e;
        }
    }

    protected abstract String getOperatorCharacter();

    @Override
    public String toString() {
        return "(" + operand1.toString() + " " + getOperatorCharacter() + " " + operand2.toString() + ")";
    }
}
