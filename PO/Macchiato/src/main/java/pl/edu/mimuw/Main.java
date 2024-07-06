package pl.edu.mimuw;

import pl.edu.mimuw.expression.*;

public class Main {

    public static void main(String[] args) {

        Program program = new Program(
            new Block(
                new Declare[] {
                    new Declare('n', new Constant(30))
                },
                new Instruction[] {
                    new For('k', new Substraction(new Variable('n'), new Constant(1)), new Instruction[] {
                        new Block(
                            new Declare[] {
                                new Declare('p', new Constant(1))
                            },
                            new Instruction[] {
                                new Assign('k', new Addition(new Variable('k'), new Constant(2))),
                                new For('i', new Substraction(new Variable('k'), new Constant(2)), new Instruction[] {
                                    new Assign('i', new Addition(new Variable('i'), new Constant(2))),
                                    new If(new Modulo(new Variable('k'), new Variable('i')), "=", new Constant(0),
                                        new Instruction[] {
                                            new Assign('p', new Constant(0))
                                        })
                                }),
                                new If(new Variable('p'), "=", new Constant(1), new Instruction[] {
                                    new Print(new Variable('k'))
                                })
                            })
                    })
                }));

        program.runDebug(0);
    }
}
