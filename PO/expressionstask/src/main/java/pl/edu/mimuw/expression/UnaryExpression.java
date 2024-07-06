package pl.edu.mimuw.expression;

public abstract class UnaryExpression extends Expression {

    private final Expression operand;
    private final String name;

    protected UnaryExpression(Expression operand, String name) {
        this.operand = operand;
        this.name = name;
    }

    protected abstract int operation(int operand);

    @Override
    public int compute(int x) {
        return operation(operand.compute(x));
    }

    @Override
    public void print() {
        System.out.print(name);
        System.out.print('(');
        operand.print();
        System.out.print(')');
    }
}
