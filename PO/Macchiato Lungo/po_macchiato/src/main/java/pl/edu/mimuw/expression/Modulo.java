package pl.edu.mimuw.expression;

public class Modulo extends BinaryExpression {

    public Modulo(Expression operand1, Expression operand2) {
        super(operand1, operand2);
    }

    public static Modulo of(Expression operand1, Expression operand2) {
        return new Modulo(operand1, operand2);
    }

    @Override
    protected int operation(int operand1, int operand2) throws ArithmeticException {
        if (operand2 == 0) {
            throw new ArithmeticException("[Exception] Cannot modulo by zero");
        }
        return operand1 % operand2;
    }

    @Override
    public String getOperatorCharacter() {
        return "≡";
    }
}
