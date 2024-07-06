package pl.edu.mimuw.expression;

public class SquareRoot extends UnaryExpression {

    public SquareRoot(Expression operand) {
        super(operand, "√");
    }

    @Override
    protected int operation(int operand) {
        if (operand < 0) {
            throw new ArithmeticException("Cannot SquareRoot negative numbers");
        }

        int low = 0;
        int high = operand;
        int ans = 0;
        while (low <= high) {
            long mid = (long) (low + high) / 2;
            if (mid * mid <= (long) operand) {
                ans = (int) mid;
                low = (int) mid + 1;
            }
            else {
                high = (int) mid - 1;
            }
        }
        return ans;
    }
}
