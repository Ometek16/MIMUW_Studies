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

public class TestForBlock {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    public void shouldForBlock() {
        Block block = new Block()
                .declare('x', Constant.of(0))
                .forBlock('i', Constant.of(10),
                        new Block()
                                .assign('x', Addition.of(Variable.named('x'), Constant.of(1)))
                                .endBlock())
                .print(Variable.named('x'))
                .endBlock();

        block.execute();
        assertEquals("10", outputStreamCaptor.toString().trim());
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }
}
