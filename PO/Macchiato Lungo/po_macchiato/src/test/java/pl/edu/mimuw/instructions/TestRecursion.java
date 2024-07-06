package pl.edu.mimuw.instructions;

import pl.edu.mimuw.expression.*;
import pl.edu.mimuw.instruction.*;

import org.junit.jupiter.api.*;

import java.util.List;
import java.beans.Transient;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.stream.Stream;

import javax.swing.plaf.synth.SynthRadioButtonMenuItemUI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

public class TestRecursion {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    public void shouldFibonacci() {
        Block block = new Block()
                .declareProcedure("fib", List.of('p', 'q', 'n'),
                        new Block()
                                .print(Variable.named('p'))
                                .declare('t', Addition.of(Variable.named('p'), Variable.named('q')))
                                .ifBlock(Variable.named('n'), ">", Constant.of(0),
                                        new Block()
                                                .invokeProcedure("fib",
                                                        List.of(Variable.named('q'), Variable.named('t'),
                                                                Substraction.of(Variable.named('n'), Constant.of(1))))
                                                .endBlock())
                                .endBlock())
                .invokeProcedure("fib", List.of(Constant.of(0), Constant.of(1), Constant.of(20)))
                .endBlock();

        block.execute();
        assertEquals("0 1 1 2 3 5 8 13 21 34 55 89 144 233 377 610 987 1597 2584 4181 6765",
                outputStreamCaptor.toString().trim().replace('\n', ' '));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }
}
