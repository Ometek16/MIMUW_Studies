package pl.edu.mimuw.expression;

public class Constant extends Expression {

    private final int value;

    public Constant(int value) {
        this.value = value;
    }

    @Override
    public int compute(int x) {
        return value;
    }

    @Override
    public void print() {
        System.out.print(value);
    }
}
