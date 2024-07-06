package pl.edu.mimuw;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FractionTestV2 {

    private Fraction fraction1;
    private Fraction fraction2;
    private Fraction fraction3;

    @BeforeEach
    void setUp() {
        fraction1 = new Fraction(1, 2);
        fraction2 = new Fraction(2, 3);
        fraction3 = new Fraction(3, 4);
    }

    @Test
    void testAdd() {
        fraction1.add(fraction2);
        assertEquals(new Fraction(7, 6), fraction1);
    }

    @Test
    void testSubtract() {
        fraction1.subtract(fraction2);
        assertEquals(new Fraction(-1, 6), fraction1);
    }

    @Test
    void testMultiply() {
        fraction1.multiply(fraction2);
        assertEquals(new Fraction(1, 3), fraction1);
    }

    @Test
    void testDivide() {
        fraction1.divide(fraction2);
        assertEquals(new Fraction(3, 4), fraction1);
    }

    @Test
    void testDivideByZero() {
        assertThrows(ArithmeticException.class, () -> fraction1.divide(new Fraction(0, 1)));
    }

    @Test
    void testToString() {
        assertEquals("1/2", fraction1.toString());
        assertEquals("2/3", fraction2.toString());
        assertEquals("3/4", fraction3.toString());
    }

    @Test
    void testEquals() {
        Fraction anotherFraction1 = new Fraction(1, 2);
        assertEquals(fraction1, anotherFraction1);
    }
}
