package pl.edu.mimuw.instructions;

import pl.edu.mimuw.expression.*;
import pl.edu.mimuw.instruction.*;

import org.junit.jupiter.api.*;

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

public class TestDeclare {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final Variables variables = new Variables();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    public static Stream<Expression> getTestDataDeclare() {
        return Stream.of(
                Constant.of(5),
                Addition.of(Constant.of(5), Constant.of(11)),
                Multiplication.of(Constant.of(5), Constant.of(69)),
                Division.of(Constant.of(2137), Constant.of(420)));
    }

    @ParameterizedTest
    @MethodSource("getTestDataDeclare")
    public void shouldDeclare(Expression expression) throws Exception {
        Block block = new Block().declare('x', expression).print(Variable.named('x')).endBlock();

        block.execute();
        assertEquals(Integer.valueOf(expression.compute(variables)).toString(),
                outputStreamCaptor.toString().trim().replace('\n', ' '));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }
}
