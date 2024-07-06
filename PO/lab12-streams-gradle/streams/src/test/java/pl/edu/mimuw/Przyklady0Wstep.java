package pl.edu.mimuw;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class Przyklady0Wstep {

    @Test
    void marki200Kplus() {
        List<Samochod> lista = TestujSamochody.zrobSamochody();
        System.out.println("\nMarki samochodów droższych niż 200000zł:");
        lista.stream()                              // tworzenie
                .filter(s -> s.getCena() > 200_000) // przerabianie
                .map(Samochod::getMarka)            // przerabianie
                .forEach(System.out::println);      // konsumpcja
    }

    @Test
    void marki200KplusSorted() {
        List<Samochod> lista = TestujSamochody.zrobSamochody();
        System.out.println("\nJ.w. posortowane, bez powtórzeń");
        lista.stream()
                .filter(s -> s.getCena() > 200_000)
                .map(Samochod::getMarka)
                .sorted()
                .distinct()
                .forEach(System.out::println);
    }

    @Test
    void strumienieSąJednorazowe() {
        List<Samochod> lista = TestujSamochody.zrobSamochody();

        Stream<String> strumień = lista.stream()
                .filter(s -> s.getCena() > 200_000)
                .map(Samochod::getMarka);

        System.out.println("\nZa pierwszym razem działa:");
        strumień.forEach(System.out::println);

        System.out.println("\nZa drugim już nie...");
        strumień.sorted().distinct().forEach(System.out::println);
    }

    @Test
    void strumienieSąLeniwe() {
        List<Samochod> lista = TestujSamochody.zrobSamochody();
        // LENIWE - tak jak "leniwe vs gorliwe wyliczanie wyrażeń logicznych"
        // ("lazy evaluation vs eager evaluation")
        Stream<String> strumień = lista.stream()
                .filter(s -> { System.out.println("Filtruję " + s); return (s.getCena() > 200_000); })
                .map(Samochod::getMarka);

        System.out.println("A TERAZ:");
        strumień.forEach(System.out::println);
    }

    @Test
    void strumienieSąNawetJeszczeBardziejLeniwe() {
        List<Samochod> lista = TestujSamochody.zrobSamochody();
        System.out.println("\nA nawet jeszcze bardziej LENIWE:");
        Stream<String> strumień = lista.stream()
                .filter(s -> { System.out.println("Filtruję " + s); return (s.getCena() > 200_000); })
                .map(Samochod::getMarka);

        System.out.println("TERAZ:");

        strumień.findAny().ifPresent(System.out::println);

        System.out.println("Dlatego nie 'printujemy' w trakcie przerabiania strumieni!");
    }

    @Test
    void strumienieSięŁatwoZrównolegla(){
        System.out.println("\nStrumienie się łatwo zrównolegla");
        int m = IntStream.rangeClosed(0, 100)
                .parallel()
                .peek(x -> System.out.print(x + ","))
                .map(x -> x*x - 30*x + 2)
                .min().getAsInt();

        System.out.println("\n\nWynik to "+m);
    }

    @Test
    void łatwoAleNieZaŁatwo() {
        System.out.println("\nAle bym nie przesadzał z tym 'łatwo'...");
        List<Integer> ar = new ArrayList<>();
        IntStream.range(0, 1000_000)
                // .parallel()        // uwaga! Mogą się dziać cuda! :)
                .forEach(ar::add);
        System.out.println(ar.size());
    }

    @Test
    void równoległyAlePoKolei(){
        System.out.println("\nZrównoleglony strumień nie traci kolejności!");
        //int a[] =
        IntStream.rangeClosed(0, 30)
                .parallel()
                .peek(x -> System.out.print(x + ","))
                .map(x -> 100+x)
                //.limit(10)
                .forEach(System.out::println);
                //.toArray();
        //System.out.println("\n" + Arrays.toString(a));
    }


}