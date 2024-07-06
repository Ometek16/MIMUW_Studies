package pl.edu.mimuw;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.*;

public class PolynomialTest {
    
    @Test
    public void shouldConvertPolynomialToString() {
        // given 
        Polynomial polynomial = new Polynomial(new double[]{1, 1, 0, 3, 1});
        String answer = "x^4 + 3.0x^3 + x + 1.0";

        // then
        assertEquals(answer, polynomial.toString());
    }

    @Test
    public void shouldAddTwoPolynomials() {
        // given 
        Polynomial polynomial1 = new Polynomial(new double[]{1, 2, 3, 4});
        Polynomial polynomial2 = new Polynomial(new double[]{1, 0, -2.5});
        Polynomial answer = new Polynomial(new double[]{2, 2, 0.5, 4});

        // when
        Polynomial result = polynomial1.add(polynomial2);

        // then
        assertEquals(answer.toString(), result.toString());
    }

    @Test
    public void shouldAddPolynomialToItself() {
        // given
        Polynomial polynomial = new Polynomial(new double[]{2, 3, 0.75, 443.2, 0, 0, 1.0});
        Polynomial answer = new Polynomial(new double[]{4, 6, 1.5, 886.4, 0, 0, 2}); 

        //when
        Polynomial result = polynomial.add(polynomial);

        //then
        assertEquals(answer.toString(), result.toString());
    }

    @Test 
    public void shouldSubstractTwoPolynomials() {
        // given 
        Polynomial polynomial1 = new Polynomial(new double[]{1, 2, 3, 4});
        Polynomial polynomial2 = new Polynomial(new double[]{1, 0, -2.5});
        Polynomial answer = new Polynomial(new double[]{0, 2, 5.5, 4});

        // when 
        Polynomial result = polynomial1.subtract(polynomial2);

        //then
        assertEquals(answer.toString(), result.toString());
    }

    @Test 
    public void shouldSubstractPolynomialFromItself() {
        // given
        Polynomial polynomial = new Polynomial(new double[]{1, 2, 5, 0, 4.4});
        Polynomial answer = new Polynomial(new double[]{0});

        // when
        Polynomial result = polynomial.subtract(polynomial);

        // then 
        assertEquals(answer.toString(), result.toString());
    }

    @Test 
    public void shouldMultiplyTwoPolynomials() {
        // given 
        Polynomial polynomial1 = new Polynomial(new double[]{1, 2, 1});
        Polynomial polynomial2 = new Polynomial(new double[]{2, 2});
        Polynomial answer = new Polynomial(new double[]{2, 6, 6, 2});

        //when 
        Polynomial result = polynomial1.multiply(polynomial2);

        // then
        assertEquals(answer.toString(), result.toString());
    }

    @Test 
    public void shouldMultiplyPolynomialByItself() {
        // given 
        Polynomial polynomial = new Polynomial(new double[]{2, 0.5, 3, 0.125});
        String answer = "0.015625x^6 + 0.75x^5 + 9.125x^4 + 3.5x^3 + 12.25x^2 + 2.0x + 4.0";

        //when 
        Polynomial result =  polynomial.multiply(polynomial);

        // then 
        assertEquals(answer, result.toString());
    }

    public record TestData(
        double[] coefficients,
        double x,
        double expectedValue
    ) {}

    public static Stream<TestData> getTestData() {
        return Stream.of(
            new TestData(new double[]{1, 2, 3}, 1, 6),
            new TestData(new double[]{0, 0, 0, 0, 0, 0, 1}, 2, 64),
            new TestData(new double[]{1, -1, 1, -1, 1, -1}, 1, 0),
            new TestData(new double[]{738219, 9, 2, 3, 9}, 0, 738219),
            new TestData(new double[]{6, 5, 1}, -2, 0)
        );
    }

    @ParameterizedTest
    @MethodSource("getTestData")
    public void testEvaluateMultiple(TestData testData) {
        // given
        Polynomial polynomial = new Polynomial(testData.coefficients);

        // when
        double ans = polynomial.evaluate(testData.x);

        // then
        assertEquals(ans, testData.expectedValue);
    }

    @Test
    public void shouldCalculateDerivativeOfPolynomial() {
        // given
        Polynomial polynomial = new Polynomial(new double[]{1, 2, 3, 4, 5});
        Polynomial answer = new Polynomial(new double[]{2, 6, 12, 20});

        // when
        Polynomial result = polynomial.derivative();

        // then
        assertEquals(answer.toString(), result.toString());
    }

    @Test 
    public void shouldCalculateIntegralOfPolynomial() {
        // given 
        Polynomial polynomial = new Polynomial(new double[]{1, 2, 3, 4, 5});
        Polynomial answer = new Polynomial(new double[]{0, 1, 1, 1, 1, 1});

        // when
        Polynomial result = polynomial.integral();
        
        // then
        assertEquals(answer.toString(), result.toString());
    }
}
