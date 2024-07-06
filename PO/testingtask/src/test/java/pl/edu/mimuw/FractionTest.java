package pl.edu.mimuw;

import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.*;

class FractionTest {

    @Test
    public void shouldConvertFractionToString() {
        // given
        Fraction fraction = new Fraction(2, 5);

        // then
        assertEquals("2/5", fraction.toString());
    }

    @Test
    public void shouldConvertFractionToDouble() {
        // given
        Fraction fraction = new Fraction(2, 5);

        // then
        assertEquals(2.0 / 5, fraction.toDouble());
    }

    @Test
    public void shouldThrowExceptionWhenDenominatorIsZero() {
        // then
        assertThrows(ArithmeticException.class, () -> new Fraction(1, 0));
    }

    @Test
    public void shouldTwoSameFractionsBeEqual() {
        // given
        Fraction fraction1 = new Fraction(2, 5);
        Fraction fraction2 = new Fraction(4, 10);

        // then
        assertEquals(fraction1, fraction2);
    }

    @Test
    public void shouldTwoDifferentFractionsNotBeEqual() {
        // given
        Fraction fraction1 = new Fraction(2, 5);
        Fraction fraction2 = new Fraction(3, 5);

        // then
        assertNotEquals(fraction1, fraction2);
    }

    @Test
    public void shouldNegatingNumeratorAndDenominatorCancelEachOtherOut() {
        // given
        Fraction fraction1 = new Fraction(2, 5);
        Fraction fraction2 = new Fraction(-2, -5);

        // then
        assertEquals(fraction1, fraction2);
    }

    @Test
    public void shouldAddTwoFractions() {
        // given
        Fraction fraction1 = new Fraction(2, 5);
        Fraction fraction2 = new Fraction(3, 5);
        Fraction fraction3 = new Fraction(1, 1);

        // when
        fraction1.add(fraction2);

        // then
        assertEquals(fraction3, fraction1);
    }

    @Test
    public void shouldSubtractTwoFractions() {
        // given
        Fraction fraction1 = new Fraction(2, 5);
        Fraction fraction2 = new Fraction(3, 5);
        Fraction fraction3 = new Fraction(-1, 5);

        // when
        fraction1.subtract(fraction2);

        // then
        assertEquals(fraction3, fraction1);
    }

    @Test
    public void shouldAddFractionToItself() {
        // given
        Fraction fraction1 = new Fraction(2, 5);
        Fraction fraction2 = new Fraction(4, 5);

        // when
        fraction1.add(fraction1);

        // then
        assertEquals(fraction2, fraction1);
    }

    @Test
    public void shouldSubtractFractionFromItself() {
        // given
        Fraction fraction1 = new Fraction(2, 5);
        Fraction fraction2 = new Fraction(0, 1);

        // when
        fraction1.subtract(fraction1);

        // then
        assertEquals(fraction2, fraction1);
    }

    @Test
    public void shouldMultiplyTwoFractions() {
        // given
        Fraction fraction1 = new Fraction(2, 5);
        Fraction fraction2 = new Fraction(3, 5);
        Fraction fraction3 = new Fraction(6, 25);

        // when
        fraction1.multiply(fraction2);

        // then
        assertEquals(fraction3, fraction1);
    }

    @Test
    public void shouldDivideTwoFractions() {
        // given
        Fraction fraction1 = new Fraction(2, 5);
        Fraction fraction2 = new Fraction(3, 5);
        Fraction fraction3 = new Fraction(2, 3);

        // when
        fraction1.divide(fraction2);

        // then
        assertEquals(fraction3, fraction1);
    }

    @Test
    public void shouldThrowExceptionWhenDividingByFractionEqualToZero() {
        // given
        Fraction fraction1 = new Fraction(2, 5);
        Fraction fraction2 = new Fraction(0, 1);

        // then
        assertThrows(ArithmeticException.class, () -> fraction1.divide(fraction2));
    }

    @ParameterizedTest
    @CsvSource({
        "1, 2, 1, 4, 3, 4",
        "1, 3, 2, 3, 1, 1",
        "2, 5, 3, 5, 1, 1",
        "-1, 2, 1, 6, -1, 3"
    })
    void testAdd(int num1, int den1, int num2, int den2, int expectedNum, int expectedDen) {
        Fraction fraction1 = new Fraction(num1, den1);
        Fraction fraction2 = new Fraction(num2, den2);
        fraction1.add(fraction2);

        Fraction expectedFraction = new Fraction(expectedNum, expectedDen);
        assertEquals(expectedFraction, fraction1);
    }

    public record TestData(
        int num1,
        int den1,
        int num2,
        int den2,
        int expectedNum,
        int expectedDen
    ) {}

    public static Stream<TestData> getTestData() {
        return Stream.of(
            new TestData(1, 2, 1, 4, 3, 4),
            new TestData(1, 3, 2, 3, 1, 1),
            new TestData(2, 5, 3, 5, 1, 1),
            new TestData(-1, 2, 1, 6, -1, 3)
        );
    }

    @ParameterizedTest
    @MethodSource("getTestData")
    void testAdd2(TestData testData) {
        Fraction fraction1 = new Fraction(testData.num1(), testData.den1());
        Fraction fraction2 = new Fraction(testData.num2(), testData.den2());
        fraction1.add(fraction2);

        Fraction expectedFraction = new Fraction(testData.expectedNum(), testData.expectedDen());
        assertEquals(expectedFraction, fraction1);
    }
}
