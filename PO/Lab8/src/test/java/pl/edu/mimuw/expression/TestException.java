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

public class TestException {
    
    @Test
    public void shouldThrowZeroDivision() {
        // given
        var expression = new Division(
            new Constant(5),
            new Variable()
        );

        // then 
        assertThrows(ArithmeticException.class, () -> expression.compute(0));
    }

    @Test 
    public void shouldThrowZeroModulo() {
        // given 
        var expression = new Modulo(
            new Constant(5),
            new Variable()
        );

        // then
        assertThrows(ArithmeticException.class, () -> expression.compute(0));
    }

    @Test 
    public void shouldThrowNegativeSquareRoot() {
        // given 
        var expression = new SquareRoot(
            new Constant(-1)
        );

        // then
        assertThrows(ArithmeticException.class, () -> expression.compute(1));
    }

    @Test 
    public void shouldThrowNegativePower() {
        // given
        var expression = new Power(
            new Constant(2),
            new Variable()
        );

        // then
        assertThrows(ArithmeticException.class, () -> expression.compute(-1));
    }
}