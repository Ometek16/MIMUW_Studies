package pl.edu.mimuw.expression;

import pl.edu.mimuw.expression.*;
import pl.edu.mimuw.instruction.Variables;

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
            int constant1,
            int constant2,
            int expectedValue) {
    }

    private final Variables variables = new Variables();

    public static Stream<TestData> getTestDataAddition() {
        return Stream.of(
                new TestData(5, 3, 8),
                new TestData(0, 0, 0),
                new TestData(-3, 2, -1),
                new TestData(-4, -2, -6),
                new TestData(10, -6, 4));
    }

    @ParameterizedTest
    @MethodSource("getTestDataAddition")
    public void shouldAddition(TestData testData) throws Exception {
        // given
        Expression expression = new Addition(
                new Constant(testData.constant1),
                new Constant(testData.constant2));

        // then
        assertEquals(testData.expectedValue, expression.compute(variables));
    }

    public static Stream<TestData> getTestDataSubstraction() {
        return Stream.of(
                new TestData(5, 3, 2),
                new TestData(0, 0, 0),
                new TestData(-3, 2, -5),
                new TestData(-4, -2, -2),
                new TestData(10, -6, 16));
    }

    @ParameterizedTest
    @MethodSource("getTestDataSubstraction")
    public void shouldSubstract(TestData testData) throws Exception {
        // given
        Expression expression = new Substraction(
                new Constant(testData.constant1),
                new Constant(testData.constant2));

        // then
        assertEquals(testData.expectedValue, expression.compute(variables));
    }

    public static Stream<TestData> getTestDataMultiplication() {
        return Stream.of(
                new TestData(5, 3, 15),
                new TestData(0, 0, 0),
                new TestData(-3, 2, -6),
                new TestData(-4, -2, 8),
                new TestData(10, -6, -60));
    }

    @ParameterizedTest
    @MethodSource("getTestDataMultiplication")
    public void shouldMultiply(TestData testData) throws Exception {
        // given
        Expression expression = new Multiplication(
                new Constant(testData.constant1),
                new Constant(testData.constant2));

        // then
        assertEquals(testData.expectedValue, expression.compute(variables));
    }

    public static Stream<TestData> getTestDataDivision() {
        return Stream.of(
                new TestData(5, 3, 1),
                new TestData(6, 3, 2),
                new TestData(-3, 2, -1),
                new TestData(-4, -2, 2),
                new TestData(10, -6, -1));
    }

    @ParameterizedTest
    @MethodSource("getTestDataDivision")
    public void shouldDivide(TestData testData) throws Exception {
        // given
        Expression expression = new Division(
                new Constant(testData.constant1),
                new Constant(testData.constant2));

        // then
        assertEquals(testData.expectedValue, expression.compute(variables));
    }
}
