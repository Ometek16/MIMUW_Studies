package pl.edu.mimuw.expression;

public class Variable extends Expression {

    @Override
    public int compute(int x) {
        return x;
    }

    @Override
    public void print() {
        System.out.print("x");
    }
}
