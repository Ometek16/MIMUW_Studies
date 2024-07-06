package pl.edu.mimuw;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.LongSupplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.LongStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Main {

    static void strumienLiniiNaStrumienSlowZNowymiLiniami() throws IOException {
        // A teraz z pomocą Stream.concat i Stream.of wstaw na początku zestawu słów dla
        // danej linii
        // napis "NOWA LINIA".
        Files.lines(Path.of("src/main/java/pl/edu/mimuw/Samochod.java"))
            .flatMap(linia -> new Scanner(linia).tokens())
            .forEach(System.out::println);
    }
    

    public static void main(String[] args) throws Exception {
        System.out.println("Hello world");
        strumienLiniiNaStrumienSlowZNowymiLiniami();
    }
}
