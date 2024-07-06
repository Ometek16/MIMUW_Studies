package pl.edu.mimuw;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

public class TestException {
    
    @Test
    public void shouldThrowZeroDivision() {
        // given
        String expression = "3 0 /";

        // then 
        assertThrows(ArithmeticException.class, () -> RPNEvaluator.evaluate(expression));
    }

    public record TestData(
        String expression
    ) {}

    public static Stream<TestData> getTestInvalidInput() {
        return Stream.of(
            new TestData("3 0"),
            new TestData("3 .")
        );
    }

    @ParameterizedTest
    @MethodSource("getTestInvalidInput")
    public void shouldThrowInvalindInput(TestData testData) {
        // then 
        assertThrows(IllegalArgumentException.class, () -> RPNEvaluator.evaluate(testData.expression));
    }
}
