package pl.edu.mimuw;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.stream.IntStream;

class Przyklady3Collect {

    @Test
    void collect1(){
        System.out.println("Wersja rozpisana: ");
        var ar = IntStream.range(1,10)
                .collect(() -> new ArrayList<Integer>(),  // produkcja nowej zlewki
                         (a, i) -> a.add(i),                // dolanie z jednej fiolki
                         (a, a1) -> a.addAll(a1));          // zlanie zlewki a1 do a

        System.out.println(ar);

        System.out.println("Wersja z referencjami metod: ");
        ar = IntStream.range(1,10)
                .collect(ArrayList::new,
                         ArrayList::add,
                         ArrayList::addAll);
        System.out.println(ar);

        System.out.println("\nA teraz po kolei...");
        System.out.println(ar);
        ar = IntStream.range(1,10)
                //.parallel()
                .collect(() -> {
                            System.out.println("Nowa zlewka: []");
                            return new ArrayList<Integer>();
                                },
                        (a, i) -> {
                            System.out.print("Dolewanie: " + a + " ← " + i + "  ⟹  ");
                            a.add(i);
                            System.out.println(a);
                                },
                        (a, a1) -> {
                            System.out.print("Zlewanie:  " + a + " ← " + a1 + "  ⟹  ");
                            a.addAll(a1);
                            System.out.println(a);
                                });
        System.out.println("Finał:\n"+ar);

        System.out.println("\nJeszcze raz, zrównoleglone...");
        System.out.println(ar);
        ar = IntStream.range(1,10)
                .parallel()
                .collect(() -> {
                            System.out.println("Nowa zlewka: []");
                            return new ArrayList<Integer>();
                        },
                        (a, i) -> {
                            System.out.print("Dolewanie: " + a + " ← " + i + "  ⟹  ");
                            a.add(i);
                            System.out.println(a);
                        },
                        (a, a1) -> {
                            System.out.print("Zlewanie:  " + a + " ← " + a1 + "  ⟹  ");
                            a.addAll(a1);
                            System.out.println(a);
                        });
        System.out.println("Finał:\n"+ar);
    }

}
