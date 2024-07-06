package pl.edu.mimuw;

import pl.edu.mimuw.expression.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;


public class TestUnary {
    
    public record TestData(
        int variable,
        int expectedValue
    ) {}

    public static Stream<TestData> getTestDataSignum() {
        return Stream.of(
            new TestData(5, 1),
            new TestData(0, 0),
            new TestData(-3, -1)
        );
    }

    @ParameterizedTest
    @MethodSource("getTestDataSignum")
    public void shouldSignum(TestData testData) {
        // given
        var expression = new Signum(
            new Variable()
        );

        // then 
        assertEquals(testData.expectedValue, expression.compute(testData.variable));
    }

    public static Stream<TestData> getTestDataAbsolute() {
        return Stream.of(
            new TestData(5, 5),
            new TestData(0, 0),
            new TestData(-3, 3)
        );
    }

    @ParameterizedTest
    @MethodSource("getTestDataAbsolute")
    public void shouldAbsolute(TestData testData) {
        // given
        var expression = new Absolute(
            new Variable()
        );

        // then 
        assertEquals(testData.expectedValue, expression.compute(testData.variable));
    }

    public static Stream<TestData> getTestDataNegation() {
        return Stream.of(
            new TestData(5, -5),
            new TestData(0, 0),
            new TestData(-3, 3)
        );
    }

    @ParameterizedTest
    @MethodSource("getTestDataNegation")
    public void shouldNegation(TestData testData) {
        // given
        var expression = new Negation(
            new Variable()
        );

        // then 
        assertEquals(testData.expectedValue, expression.compute(testData.variable));
    }

    public static Stream<TestData> getTestDataSquareRoot() {
        return Stream.of(
            new TestData(5, 2),
            new TestData(0, 0),
            new TestData(17, 4),
            new TestData(16, 4)
        );
    }

    @ParameterizedTest
    @MethodSource("getTestDataSquareRoot")
    public void shouldSquareRoot(TestData testData) {
        // given
        var expression = new SquareRoot(
            new Variable()
        );

        // then 
        assertEquals(testData.expectedValue, expression.compute(testData.variable));
    }
}
