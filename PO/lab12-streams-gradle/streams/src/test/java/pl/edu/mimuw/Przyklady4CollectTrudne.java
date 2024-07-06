package pl.edu.mimuw;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.stream.Collectors;

class Przyklady4CollectTrudne {


//    @Test
//    void jeszczeStatystyki(){
//        // public static <T, R1, R2, R>
//        // Collector<T,?,R> teeing(Collector<? super T,?,R1> downstream1,
//        //                         Collector<? super T,?,R2> downstream2,
//        //                         BiFunction<? super R1,? super R2,R> merger)
//        var stat = TestujSamochody.zrobSamochody().stream()
//                .collect(Collectors.teeing(
//                            Collectors.summingInt(Samochod::getLiczbaDrzwi),
//                            Collectors.summingLong(Samochod::getCena),
//                            StatFun::new));
//        System.out.println(stat);
//        System.out.println("Klasa StatFun użyta wyłącznie do łapania końcowego wyniku.\n" +
//                "Równie dobrze można było od razu zamienić np. na String do wypisania.");
//    }

    @Test
    void grupowanie1(){
        var m = TestujSamochody.zrobSamochody().stream()
                .collect(Collectors.groupingBy(Samochod::getMarka));

        for (var k: m.entrySet()) {
            System.out.println("==============");
            System.out.println(k.getKey());
            System.out.println("-------");
            k.getValue().forEach(System.out::println);
        };
    }

    @Test
    void grupowanie2(){
        var m = TestujSamochody.zrobSamochody().stream()
                .collect(Collectors.groupingBy(
                            Samochod::getMarka,
                            Collectors.summingInt(Samochod::getCena)
                        ));

        m.forEach((n,c) -> System.out.format("%s: %d\n", n, c));
    }

    record Pair<E,F> (E fst, F snd){};

    @Test
    void grupowanie3(){
        var m = TestujSamochody.zrobSamochody().stream()
                .collect(Collectors.groupingBy(
                        Samochod::getMarka,
                        Collectors.teeing(
                                Collectors.minBy(Comparator.comparingInt(Samochod::getCena)),
                                Collectors.maxBy(Comparator.comparingInt(Samochod::getCena)),
                                (smin, smax) -> new Pair<>(smin.get(), smax.get()))
                ));

        m.forEach((n,para) -> {
            System.out.println(n);
            System.out.println("  najtańsszy: "+para.fst);
            System.out.println("  najdroższy: "+para.snd);
        });
    }

    @Test
    void mapowanieInaczej(){
        var m = TestujSamochody.zrobSamochody().stream()
                .collect(Collectors.toMap(
                        s -> s.getMarka() + " " + s.getModel(),
                        Samochod::getCena
                ));

        m.forEach((n, ile) -> System.out.println(n + ": " + ile));
    }

    @Test
    void mapowanieŹle(){
        var m = TestujSamochody.zrobSamochody().stream()
                .collect(Collectors.toMap(
                        Samochod::getMarka,
                        Samochod::getCena
                ));

        m.forEach((n, ile) -> System.out.println(n + ": " + ile));
    }

    @Test
    void mapowanieDobrze(){
        var m = TestujSamochody.zrobSamochody().stream()
                .collect(Collectors.toMap(
                        Samochod::getMarka,
                        Samochod::getCena,
                        (x, y) -> x     // jak się pojawi drugi - to zignorować :)
                        // Integer::sum // jak się pojawi drugi - to zsumować :)
                ));

        System.out.println("Przykładowe ceny :)\n===================");
        m.forEach((n, ile) -> System.out.println(n + ": " + ile));
    }

}
