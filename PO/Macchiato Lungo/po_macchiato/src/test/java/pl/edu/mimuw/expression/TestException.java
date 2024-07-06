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

public class TestException {
    
    @Test
    public void shouldThrowZeroDivision() {
        // given
        var expression = new Division(
            new Constant(5),
            new Constant(0)
        );

        // then 
        assertThrows(ArithmeticException.class, () -> expression.compute(new Variables()));
    }

    @Test 
    public void shouldThrowZeroModulo() {
        // given 
        var expression = new Modulo(
            new Constant(5),
            new Constant(0)
        );

        // then
        assertThrows(ArithmeticException.class, () -> expression.compute(new Variables()));
    }
}