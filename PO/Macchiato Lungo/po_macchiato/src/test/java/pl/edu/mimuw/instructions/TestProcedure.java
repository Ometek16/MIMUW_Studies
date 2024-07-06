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

public class TestProcedure {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    public void shouldDeclareProcedureException() {
        Block block = new Block()
                .declareProcedure("foo", List.of(), new Block().endBlock())
                .declareProcedure("foo", List.of(), new Block().endBlock())
                .endBlock();

        String result = """
            [Exception] Procedure already declared 
            def foo () 
            ===== ===== ===== VARIABLES ===== ===== ===== 
            a: ~             b: ~             c: ~             d: ~              e: ~             f: ~             g: ~             h: ~              i: ~             j: ~             k: ~             l: ~              m: ~             n: ~             o: ~             p: ~              q: ~             r: ~             s: ~             t: ~              u: ~             v: ~             w: ~             x: ~              y: ~             z: ~""";

        block.execute();

        assertEquals(result.trim().replace('\n', ' '),
                outputStreamCaptor.toString().trim().replace('\n', ' '));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }
}
