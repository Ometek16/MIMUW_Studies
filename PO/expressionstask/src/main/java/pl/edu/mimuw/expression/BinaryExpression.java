package pl.edu.mimuw.expression;

public abstract class BinaryExpression extends Expression {

    private final Expression operand1;
    private final Expression operand2;

    protected BinaryExpression(Expression operand1, Expression operand2) {
        this.operand1 = operand1;
        this.operand2 = operand2;
    }

    protected abstract int operation(int operand1, int operand2);

    @Override
    public int compute(int x) {
        return operation(operand1.compute(x), operand2.compute(x));
    }

    protected abstract void printOperatorCharacter();

    @Override
    public void print() {
        System.out.print("(");
        operand1.print();
        System.out.print(" ");
        printOperatorCharacter();
        System.out.print(" ");
        operand2.print();
        System.out.print(")");
    }
}
