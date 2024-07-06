package pl.edu.mimuw;

import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Przyklady2Reduce {

    @Test
    void reduce1() {
        // reduce(⊙)
        // Optional<T> reduce(BinaryOperator<T> accumulator)
        int suma = IntStream.rangeClosed(1, 10)
                .parallel()
                .reduce((x1, x2) -> x1 + x2)
                .getAsInt();
        System.out.println(suma);

        System.out.println("Sekwencyjnie:");
        String operacja = IntStream.rangeClosed(1, 10)
                //.parallel()
                .mapToObj(String::valueOf)
                .reduce((s1, s2) -> "(" + s1 + " ⊙ " + s2 + ")")
                .orElse("");
        System.out.println(operacja);

        System.out.println("Równolegle:");
        operacja = IntStream.rangeClosed(1, 10)
                .parallel()
                .mapToObj(String::valueOf)
                .reduce((s1, s2) -> "(" + s1 + " ⊙ " + s2 + ")")
                .orElse("");
        System.out.println(operacja);

        System.out.println("Uwaga! Te operacje na strumieniach stringów\n" +
                "są teoretycznie nieprawidłowe, bo takie 'nawiasowane'\n" +
                "łączenie napisów nie jest łączne! Ale kto nam zabroni ? :)");
    }

    @Test
    void reduce2() {
        // reduce(o, ⊙)
        // T reduce(T identity, BinaryOperator<T> accumulator)
        int suma = IntStream.rangeClosed(1, 10)
                .parallel()
                .reduce(0, Integer::sum);
        System.out.println(suma);

        System.out.println("Sekwencyjnie:");
        String operacja = IntStream.rangeClosed(1, 10)
                //.parallel()
                .mapToObj(String::valueOf)
                .reduce("o", (s1, s2) -> "(" + s1 + " ⊙ " + s2 + ")");
        System.out.println(operacja);

        System.out.println("Równolegle:");
        operacja = IntStream.rangeClosed(1, 10) // może lepiej 1,30
                .parallel()
                .mapToObj(String::valueOf)
                .reduce("o", (s1, s2) -> "(" + s1 + " ⊙ " + s2 + ")");
        System.out.println(operacja);
    }


    String int2ToSub(int n) {
        if (n < 10)
            return String.valueOf("₀₁₂₃₄₅₆₇₈₉".charAt(n));
        else
            return String.valueOf(new char[]{"₀₁₂₃₄₅₆₇₈₉".charAt(n / 10), "₀₁₂₃₄₅₆₇₈₉".charAt(n % 10)});
    }

    @Test
    void reduce3() {
        // reduce(o, ·, ⊙)
        // <U> U reduce(U identity, BiFunction<U,? super T,U> accumulator, BinaryOperator<U> combiner)
        int liczba = TestujSamochody.zrobSamochody().stream()
                .parallel()
                .reduce(0, (n, samochod) -> n + 1, Integer::sum);
        System.out.println(liczba);

        System.out.println("Sekwencyjnie:");
        String operacja = IntStream.rangeClosed(1, 15)
                //.parallel()
                .mapToObj(n -> "s" + int2ToSub(n))
                .reduce("o",
                        (s, samochod) -> "(" + s + " · " + samochod + ")",
                        (s1, s2) -> "(" + s1 + " ⊙ " + s2 + ")");
        System.out.println(operacja);

        System.out.println("Równolegle:");
        operacja = IntStream.rangeClosed(1, 15)
                .parallel()
                .mapToObj(n -> "s" + int2ToSub(n))
                .reduce("o",
                        (s, samochod) -> "(" + s + " · " + samochod + ")",
                        (s1, s2) -> "(" + s1 + " ⊙ " + s2 + ")");
        System.out.println(operacja);
    }

    @Test
    void uwagaNaŁączenieNapisówWReduce() {

        System.out.println("Demo!");
        String wyn = IntStream.rangeClosed(0, 10)
                //.parallel()
                .mapToObj(String::valueOf)
                .reduce((s1, s2) -> s1 + s2)
                .get();
        System.out.println(wyn + " " + wyn.length());


        int liczba = 400_000;
        long start;
        System.out.println("Wolne!");
        start = System.currentTimeMillis();

        wyn = IntStream.rangeClosed(1, liczba)
                .parallel()
                .mapToObj(String::valueOf)
                .reduce((s1, s2) -> s1 + s2)
                .get();
        System.out.println(wyn.length());
        System.out.println("Czas: " + (System.currentTimeMillis() - start));

        System.out.println("\nSzybkie 1: collect StringBuilder");
        start = System.currentTimeMillis();
        var sb = IntStream.rangeClosed(1, liczba)
                .parallel()
                .mapToObj(String::valueOf)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append);
        System.out.println(sb.toString().length());
        System.out.println("Czas: " + (System.currentTimeMillis() - start));

        System.out.println("\nSzybkie 1: collect Collectors.joining");
        start = System.currentTimeMillis();
        wyn = IntStream.rangeClosed(1, liczba)
                .parallel()
                .mapToObj(String::valueOf)
                .collect(Collectors.joining());
        System.out.println(wyn.length());
        System.out.println("Czas: " + (System.currentTimeMillis() - start));


    }
}