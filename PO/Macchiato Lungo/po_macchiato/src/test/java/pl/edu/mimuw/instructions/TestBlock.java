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

public class TestBlock {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final Variables variables = new Variables();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    public void shouldBlock() throws Exception {
        Block block = new Block()
                .declare('x', Constant.of(1))
                .declare('y', Constant.of(3))
                .print(Variable.named('x'))
                .print(Variable.named('y'))
                .beginBlock()
                .declare('x', Constant.of(4))
                .assign('y', Constant.of(5))
                .print(Variable.named('x'))
                .print(Variable.named('y'))
                .endBlock()
                .print(Variable.named('x'))
                .print(Variable.named('y'))
                .endBlock();

        String result = "1 3 4 5 1 5";

        block.execute();
        assertEquals(result,
                outputStreamCaptor.toString().trim().replace('\n', ' '));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }
}
