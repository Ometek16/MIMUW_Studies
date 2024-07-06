package pl.edu.mimuw;

import pl.edu.mimuw.expression.*;

public class Main {

    public static void main(String[] args) {

        var expression = new Addition(
            new Addition(
                new Variable(),
                new Constant(10)
            ),
            new Absolute(
                new Negation(
                    new SquareRoot(
                        new Constant(17)
                    )
                )
            )
        );

        expression.print();
        System.out.println();
        System.out.println(expression.compute(2));
    }
}
