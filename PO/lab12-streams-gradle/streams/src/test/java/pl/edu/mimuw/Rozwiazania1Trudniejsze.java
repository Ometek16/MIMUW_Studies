package pl.edu.mimuw;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.stream.Stream;

class Rozwiazania1Trudniejsze {

    @Test
    void strumienLiniiNaStrumienSlowZNowymiLiniami() throws IOException {
        // A teraz z pomocą Stream.concat i Stream.of wstaw na początku zestawu słów dla
        // danej linii
        // napis "NOWA LINIA".
        Files.lines(Path.of("src/main/java/pl/edu/mimuw/Samochod.java"))
                .flatMap(linia -> new Scanner(linia).tokens())
                .forEach(System.out::println);
    }

    @Test
    void cennikSamochodowZDodatkami() {
        // Zrób cennik samochodów z dodatkami: "Marka Model: cena", albo "Marka Model + Dodatek : cena":
        // używając flatMap i Stream.concat
        // TODO
    }

    @Test
    void cennikSamochodowZDodatkamiMapMulti() {
        //  j.w. używając mapMulti
        // TODO
    }

}
