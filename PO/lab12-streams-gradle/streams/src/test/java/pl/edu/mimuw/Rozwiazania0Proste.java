package pl.edu.mimuw;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.Scanner;
import java.util.function.LongSupplier;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

class Rozwiazania0Proste {

    @Test
    void kwadraty() {
        // Wypisz wszystkie kwadraty liczb naturalnych mniejsze niż 1000
        // TODO
        IntStream.range(1, 1000)
                .map(x -> x * x)
                .takeWhile(y -> y < 1000)
                .forEach(System.out::println);
    }

    @Test
    void sześciany() {
        // Wypisz sześciany liczb naturalnych większe niż 1000, ale mniejsze niż 10000
        IntStream.range(1, 100)
                .map(x -> x * x * x)
                .dropWhile(y -> y <= 1000)
                .takeWhile(y -> y < 10000)
                .forEach(System.out::println);
    }

    @Test
    void potegiDwojki() {
        // Kolejne potęgi dwójki, 20 pierwszych
        IntStream.iterate(2, n -> 2 * n)
            .limit(20)
            .forEach(System.out::println);
    }

    @Test
    void fiby() {
        // 10 liczb Fibonacciego większych od 100
        
        class FiboSupplier implements LongSupplier {
            long fi = 1;
            long fi1 = 1;

            @Override
            public long getAsLong() {
                long fi2 = fi + fi1;
                fi = fi1;
                fi1 = fi2;
                return fi2;
            }
        }
    
        LongStream.generate(new FiboSupplier())
            .dropWhile(f -> f <= 100)
            .limit(10)
            .forEach(System.out::println);

    }

    @Test
    void statystykiSinusów() {
        // Statystyki sinusów liczb całkowitych od 0 do 1000
        var stats = IntStream.range(0, 1000)
                .mapToDouble(x -> Math.sin(x))
                .summaryStatistics();
        System.out.println(stats);
    }

    @Test
    void samochodyPięciodrzwiowe() {
        // Tablica nazw (model + marka) samochodów 5-cio drzwiowych, posortowana
        // malejąco.
        // Tablica ma być typu String[]
        var dobreAuta = TestujSamochody.zrobSamochody()
                .stream()
                .filter(m -> m.getLiczbaDrzwi() == 5)
                .map(s -> s.getMarka() + " " + s.getModel())
                .sorted(Comparator.reverseOrder())
                .toArray(String[]::new);

        Arrays.stream(dobreAuta).forEach(System.out::println);
    }

    @Test
    void liczbaSłówWPliku() throws FileNotFoundException {
        // Liczba słów w pliku
        // TODO
    }

    @Test
    void niepustaLiniaZawierajacaA() throws IOException {
        // Czy w tym (albo jakimś innym ustalonym, np "src/Strumienie.java") pliku jest jakaś
        // niepusta linia niezawierająca literki "a" (String.contains)
        // TODO
    }

    @Test
    void najdłuższaLiniaZawierającaA() throws IOException {
        // Podać najdłuższą linię zawierającą "a" w pliku j.w.
        // TODO
    }

}

