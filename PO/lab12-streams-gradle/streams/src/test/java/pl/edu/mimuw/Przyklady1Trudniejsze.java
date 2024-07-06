package pl.edu.mimuw;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

class Przyklady1Trudniejsze {

    @Test
    void strumienLiniiNaStrumienSlow() throws IOException {
        // Budujemy strumień linii z pliku i za pomocą Scanner(String).tokens() oraz flatMap
        // zamieniamy go na strumień słów z tego pliku;
        Files.lines(Path.of("src/Samochod.java"))
                .flatMap(linia -> new Scanner(linia).tokens())
                .forEach(System.out::println);
    }

    @Test
    void strumienLiniiNaStrumienSlowMapMulti() throws IOException {
        // A tak można by powyższe rzeczy zrobić za pomocą mapMulti
        // (wtedy zamiast pustawych Stream'ów wołamy - lub nie - odp. consumera)
        Files.lines(Path.of("src/Samochod.java"))
                .mapMulti((linia, mniam) -> new Scanner(linia).tokens().forEach(mniam))
                .forEach(System.out::println);
    }

    @Test
    void strumienLiniiNaStrumienSlowZNowymiLiniamiMapMulti() throws IOException {
        // * A teraz z pomocą Stream.concat i Stream.of wstaw na początku zestawu słów dla danej linii napis "NOWA LINIA".
        Files.lines(Path.of("src/Samochod.java"))
                .mapMulti((linia, mniam) -> {
                    mniam.accept("NOWA LINIA");
                    new Scanner(linia).tokens().forEach(mniam);
                })
                .forEach(System.out::println);
    }

}
