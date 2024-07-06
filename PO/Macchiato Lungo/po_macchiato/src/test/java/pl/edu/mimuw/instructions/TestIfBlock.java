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

public class TestIfBlock {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final Variables variables = new Variables();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    public record TestData(
            Expression expression1,
            Expression expression2,
            int expectedValue) {
    }

    public static Stream<TestData> getTestDataIf() {
        return Stream.of(
                new TestData(Constant.of(0), Constant.of(1), 1),
                new TestData(Constant.of(2137), Multiplication.of(Constant.of(69), Constant.of(31)), 2139));
    }

    @ParameterizedTest
    @MethodSource("getTestDataIf")
    public void shouldIfBlcok(TestData testData) throws Exception {
        Block block = new Block()
                .ifBlock(testData.expression1, ">", testData.expression2,
                        new Block().print(testData.expression1).endBlock(),
                        new Block().print(testData.expression2).endBlock())
                .endBlock();

        block.execute();

        assertEquals(Integer.valueOf(testData.expectedValue).toString(),
                outputStreamCaptor.toString().trim().replace('\n', ' '));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }
}
