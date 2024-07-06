package pl.edu.mimuw;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;


public class TestValid {
    
    public record TestData(
        String expression,
        int expectedValue
    ) {}

    public static Stream<TestData> getTestDataValid() {
        return Stream.of(
            new TestData("2 3 + 5 *", 25),
            new TestData("2 1 + 3 *", 9),
            new TestData("4 13 5 / +", 6),
            new TestData("10 6 9 3 + -11 * / * 17 + 5 +", 22)
        );
    }

    @ParameterizedTest
    @MethodSource("getTestDataValid")
    public void shouldValid(TestData testData) {
        // given
        int result = -1;

        try {
            result = RPNEvaluator.evaluate(testData.expression);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        // then 
        assertEquals(testData.expectedValue, result);
    }
}