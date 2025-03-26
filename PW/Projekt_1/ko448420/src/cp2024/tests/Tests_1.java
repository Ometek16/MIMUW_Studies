package cp2024.tests;

import cp2024.circuit.*;
import cp2024.solution.ParallelCircuitSolver;

import java.time.Duration;

public class Tests_1 {
    public static void main(String[] args) throws InterruptedException {
        Circuit c1 = new Circuit(
                CircuitNode.mk(NodeType.IF,
                        CircuitNode.mk(true),
                        CircuitNode.mk(NodeType.GT, 2,
                                CircuitNode.mk(true),
                                CircuitNode.mk(false, Duration.ofSeconds(400)),
                                CircuitNode.mk(true),
                                CircuitNode.mk(false),
                                CircuitNode.mk(false)
                        ),
                        CircuitNode.mk(NodeType.LT, 10,
                                CircuitNode.mk(true),
                                CircuitNode.mk(false),
                                CircuitNode.mk(true),
                                CircuitNode.mk(false),
                                CircuitNode.mk(true)
                        )
                )
        );

        Circuit c2 = new Circuit(
                CircuitNode.mk(NodeType.AND,
                        CircuitNode.mk(NodeType.NOT, CircuitNode.mk(true, Duration.ofSeconds(4))),
                        CircuitNode.mk(NodeType.OR,
                                CircuitNode.mk(true),
                                CircuitNode.mk(NodeType.LT, 5, CircuitNode.mk(true)),
                                CircuitNode.mk(false, Duration.ofSeconds(1))
                        )
                )
        );

        Circuit c3 = new Circuit(
                CircuitNode.mk(NodeType.IF,
                        CircuitNode.mk(NodeType.LT, 3, CircuitNode.mk(false, Duration.ofSeconds(2))),
                        CircuitNode.mk(NodeType.AND,
                                CircuitNode.mk(true),
                                CircuitNode.mk(NodeType.NOT, CircuitNode.mk(false)),
                                CircuitNode.mk(true)
                        ),
                        CircuitNode.mk(NodeType.GT, 4, CircuitNode.mk(false))
                )
        );

        Circuit c4 = new Circuit(
                CircuitNode.mk(NodeType.OR,
                        CircuitNode.mk(NodeType.IF,
                                CircuitNode.mk(false),
                                CircuitNode.mk(false),
                                CircuitNode.mk(false, Duration.ofSeconds(3))
                        ),
                        CircuitNode.mk(NodeType.AND,
                                CircuitNode.mk(true, Duration.ofSeconds(5)),
                                CircuitNode.mk(true)
                        )
                )
        );

        Circuit c5 = new Circuit(
                CircuitNode.mk(NodeType.IF,
                        CircuitNode.mk(false, Duration.ofSeconds(5)),
                        CircuitNode.mk(NodeType.LT, 0,
                                CircuitNode.mk(true),
                                CircuitNode.mk(true),
                                CircuitNode.mk(true)
                        ),
                        CircuitNode.mk(NodeType.GT, 4,
                                CircuitNode.mk(true),
                                CircuitNode.mk(true),
                                CircuitNode.mk(true)
                        )
                )
        );

        CircuitSolver solver = new ParallelCircuitSolver();

        CircuitValue firstValue = solver.solve(c1);
        CircuitValue secondValue = solver.solve(c2);
        CircuitValue thirdValue = solver.solve(c3);
        CircuitValue fourthValue = solver.solve(c4);
        CircuitValue fifthValue = solver.solve(c5);

        Thread.sleep(3000);
        solver.stop();

        try {
            System.out.println("Circuit 1 " + firstValue.getValue());
        } catch (InterruptedException e) {
            System.out.println("Circuit 1 interrupted");
        }

        try {
            System.out.println("Circuit 2 " + secondValue.getValue());
        } catch (InterruptedException e) {
            System.out.println("Circuit 2 interrupted");
        }

        try {
            System.out.println("Circuit 3 " + thirdValue.getValue());
        } catch (InterruptedException e) {
            System.out.println("Circuit 3 interrupted");
        }

        try {
            System.out.println("Circuit 4 " + fourthValue.getValue());
        } catch (InterruptedException e) {
            System.out.println("Circuit 4 interrupted");
        }

        try {
            System.out.println("Circuit 5 " + fifthValue.getValue());
        } catch (InterruptedException e) {
            System.out.println("Circuit 5 interrupted");
        }
    }
}
