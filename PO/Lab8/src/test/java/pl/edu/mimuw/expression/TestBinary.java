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


public class TestBinary {

    public record TestData(
        int constant,
        int variable,
        int expectedValue
    ) {}

    public static Stream<TestData> getTestDataAddition() {
        return Stream.of(
            new TestData(5, 3, 8),
            new TestData(0, 0, 0),
            new TestData(-3, 2, -1),
            new TestData(-4, -2, -6),
            new TestData(10, -6, 4)
        );
    }

    @ParameterizedTest
    @MethodSource("getTestDataAddition")
    public void shouldAddition(TestData testData) {
        // given
        var expression = new Addition(
            new Constant(testData.constant),
            new Variable()
        );

        // then 
        assertEquals(testData.expectedValue, expression.compute(testData.variable));
    }

    public static Stream<TestData> getTestDataSubstraction() {
        return Stream.of(
            new TestData(5, 3, 2),
            new TestData(0, 0, 0),
            new TestData(-3, 2, -5),
            new TestData(-4, -2, -2),
            new TestData(10, -6, 16)
        );
    }

    @ParameterizedTest
    @MethodSource("getTestDataSubstraction")
    public void shouldSubstract(TestData testData) {
        // given
        var expression = new Substraction(
            new Constant(testData.constant),
            new Variable()
        );

        // then 
        assertEquals(testData.expectedValue, expression.compute(testData.variable));
    }

    public static Stream<TestData> getTestDataMultiplication() {
        return Stream.of(
            new TestData(5, 3, 15),
            new TestData(0, 0, 0),
            new TestData(-3, 2, -6),
            new TestData(-4, -2, 8),
            new TestData(10, -6, -60)
        );
    }

    @ParameterizedTest
    @MethodSource("getTestDataMultiplication")
    public void shouldMultiply(TestData testData) {
        // given
        var expression = new Multiplication(
            new Constant(testData.constant),
            new Variable()
        );

        // then 
        assertEquals(testData.expectedValue, expression.compute(testData.variable));
    }


    public static Stream<TestData> getTestDataDivision() {
        return Stream.of(
            new TestData(5, 3, 1),
            new TestData(6, 3, 2),
            new TestData(-3, 2, -1),
            new TestData(-4, -2, 2),
            new TestData(10, -6, -1)
        );
    }

    @ParameterizedTest
    @MethodSource("getTestDataDivision")
    public void shouldDivide(TestData testData) {
        // given
        var expression = new Division(
            new Constant(testData.constant),
            new Variable()
        );

        // then 
        assertEquals(testData.expectedValue, expression.compute(testData.variable));
    }


    public static Stream<TestData> getTestDataPower() {
        return Stream.of(
            new TestData(5, 0, 1),
            new TestData(6, 4, 1296),
            new TestData(-3, 2, 9),
            new TestData(2, 10, 1024),
            new TestData(2, 30, 1073741824)
        );
    }

    @ParameterizedTest
    @MethodSource("getTestDataPower")
    public void shouldPower(TestData testData) {
        // given
        var expression = new Power(
            new Constant(testData.constant),
            new Variable()
        );

        // then 
        assertEquals(testData.expectedValue, expression.compute(testData.variable));
    }

}
