package pl.edu.mimuw;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

public class TestFilterableList {

    @Test
    public void shouldPositive() {
        // given
        FilterableList<Integer> list = new FilterableList<>();
        list.madd(1).madd(2).madd(-2);

        FilterableList<Integer> answer = new FilterableList<>();
        answer.madd(1).madd(2);

        // when

        FilterableList<Integer> result = list.filter(integer -> integer > 0);

        // then
        assertEquals(answer, result);
    }

    @Test 
    public void shouldSieve() {
        // given
        Sieve sito = new Sieve(10);
        List<Integer> answer = new ArrayList<>();
        answer.add(2);
        answer.add(3);
        answer.add(5);
        answer.add(7);

        // when
        var result = sito.sift();

        // then
        assertEquals(answer, result);
    }

}
