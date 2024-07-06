package pl.edu.mimuw;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

class Rozwiazania4CollectTrudne {

    @Test
    void mapaLiteraLiczba() throws FileNotFoundException {
        // Stwórz mapę słów z ulubionego pliku: litera ↦ liczba słów na daną literę
        // (literą może być dowolny pierwszy znak ze słowa)
        // TODO
    }

    @Test
    void mapaLiteraNajdłuższe() throws FileNotFoundException {
        // j.w, ale stwórz mapę litera  ↦ najdłuższe słowo na daną literę
        // TODO
    }

    @Test
    void sortByCount() throws IOException {
        // Posortuj wiersze wg liczby wystąpień litery "a" - i zrób zestawienie:
        // tyle wystąpień  ↦ tyle wierszy o danej liczbie wystąpień
        // TODO
    }

    @Test
    void sortByCount2() throws IOException {
        // j.w. ale do liczenia liczby wystąpień użyj strumienia String::chars
        // TODO
    }

    @Test
    void samochodyZDodatkami() {
        // Posortuj samochody wg ceny wraz ze wszystkimi dodatkami.
        // Postaraj się nie liczyć łącznej ceny więcej niż raz dla każdego samochodu.
        // TODO
    }

    @Test
    void maksDzielnikow(){
        // Znajdź w przedziale (0,20000) wszystkie liczby o największej liczbie dzielników.
        // Do liczenia dzielników możesz użyć range(...).filter(...).count() :)
        // Nie obliczaj tego więcej niż jednokrotnie dla każdej liczby.
        // TODO
    }


}
