package pl.edu.mimuw.expression;

public class Power extends BinaryExpression {

    public Power(Expression operand1, Expression operand2) {
        super(operand1, operand2);
    }

    @Override
    protected int operation(int operand1, int operand2) {
        if (operand2 < 0) {
            throw new ArithmeticException("Cannot power to a negative number");
        }
        if (operand2 == 0)
            return 1;
        if (operand2 % 2 == 1)
            return operand1 * operation(operand1, operand2 - 1);
        int tmp = operation(operand1, operand2 / 2);
        return tmp * tmp;
    }

    @Override
    protected void printOperatorCharacter() {
        System.out.print('^');
    }
}
