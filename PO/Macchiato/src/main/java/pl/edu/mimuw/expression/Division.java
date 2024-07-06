package pl.edu.mimuw.expression;

public class Division extends BinaryExpression {

    public Division(Expression operand1, Expression operand2) {
        super(operand1, operand2);
    }

    @Override
    protected int operation(int operand1, int operand2) throws ArithmeticException {
        if (operand2 == 0) {
            throw new ArithmeticException("[Exception] Cannot divide by zero");
        }
        return operand1 / operand2;
    }

    @Override
    public String getOperatorCharacter() {
        return "÷";
    }
}
