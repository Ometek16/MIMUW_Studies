package pl.edu.mimuw;

import java.util.*;
import java.util.stream.IntStream;

public class Sieve {
    private final int from = 2;
    private int to;
    private FilterableList<Integer> potentialPrimes = new FilterableList<>();

    public Sieve(int to) {
        this.to = to;
        var range = IntStream.rangeClosed(from, to);
        range.forEach(potentialPrimes::add);
    }

    public List<Integer> sift() {
        IntStream.rangeClosed(from, to / 2).forEach(
                currentNumber -> potentialPrimes = potentialPrimes.filter(
                        integer -> integer <= currentNumber || integer % currentNumber != 0));

        return potentialPrimes.toList();
    }
}
