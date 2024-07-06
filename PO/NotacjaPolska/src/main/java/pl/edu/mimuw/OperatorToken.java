package pl.edu.mimuw;

import java.util.Deque;

public class OperatorToken implements Token {

    private String operator;

    public OperatorToken(String operator) {
        this.operator = operator;
    }

    public void evaluate(Deque<Integer> stack) throws ArithmeticException {
        int operand2 = stack.removeFirst();
        int operand1 = stack.removeFirst();

        if (this.operator.equals("*")) {
            stack.addFirst(operand1 * operand2);
        }
        else if (this.operator.equals("+")) {
            stack.addFirst(operand1 + operand2);
        }
        else if (this.operator.equals("-")) {
            stack.addFirst(operand1 - operand2);
        }
        else if (this.operator.equals("/")) {
            if (operand2 == 0)
                throw new ArithmeticException("Division by 0 :/");
            stack.addFirst(operand1 / operand2);
        }
    }
}
