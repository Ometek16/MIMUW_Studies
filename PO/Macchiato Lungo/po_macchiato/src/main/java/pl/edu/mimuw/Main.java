package pl.edu.mimuw;

import java.util.List;

import pl.edu.mimuw.expression.*;
import pl.edu.mimuw.instruction.*;

public class Main {

    public static void main(String[] args) {

        Block block = 
            new Block()
                .declare('x', Constant.of(101))
                .declare('y', Constant.of(1))
                .declareProcedure("out", List.of('a'), 
                    new Block()
                        .print(Addition.of(Variable.named('a'), Variable.named('x')))
                    .endBlock())  
                .assign('x', Substraction.of(Variable.named('x'), Variable.named('y')))
                .invokeProcedure("out", List.of(Variable.named('x')))
                .invokeProcedure("out", List.of(Constant.of(100)))
                .beginBlock()
                    .declare('x', Constant.of(10))
                    .invokeProcedure("out", List.of(Constant.of(100)))
                .endBlock()
            .endBlock();

        block.executeDebug(0);
    }
}
