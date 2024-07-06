/**
 * "SameGame"
 *
 * Interfejs użytkownika do gry "SameGame".
 *
 * Korzysta z biblioteki ncurses i programu wykonywalnego `zadanie2`,
 * usuwającego grupę klocków.
 *
 * Tworzy pliki pomocnicze o nazwach `pomocniczy1.txt` i `pomocniczy2.txt`.
 *
 * Kompilacja poleceniem:
 *
 * gcc @opcje samegame.c -lncurses -o samegame
 *
 * Program:
 * - losuje początkowy stan gry;
 * - umożliwia wybór klocka za pomocą klawiszy strzałek;
 * - po naciśnięciu spacji usuwa grupę, do której należy aktualny klocek;
 * - za usunięcie grupy `n > 1` klocków daje `(n - 2) * (n - 2)` punkty;
 * - pokazuje aktualną liczbę punktów;
 * - kończy pracę po naciśnięciu Escape.
 *
 * Ograniczenia:
 * - program nie reaguje na zmianę rozmiaru okna terminala podczas gry.
 *
 * autor: Artur Zaroda <zaroda@mimuw.edu.pl>
 * wersja: 1.0.0
 * data: 27 listopada 2022
 */


#include <ncurses.h>

#include <assert.h>
#include <stdbool.h>
#include <stdlib.h>
#include <time.h>


/* postać planszy */

/**
 * Liczba wierszy planszy.
 */
#ifndef WIERSZE
#define WIERSZE 10
#endif

/**
 * Liczba kolumn planszy.
 */
#ifndef KOLUMNY
#define KOLUMNY 15
#endif

/**
 * Liczba rodzajów klocków.
 */
#ifndef RODZAJE
#define RODZAJE 4
#endif

/**
 * Oznaczenie pustego pola.
 */
#define PUSTE '.'


/* pomocnicze pliki i pomocniczy program */

/**
 * Nazwy plików pomocniczych.
 */
#define PIERWSZY "pomocniczy1.txt"
#define DRUGI "pomocniczy2.txt"

/**
 * Nazwa programu usuwającego grupę klocków.
 */
#define PROGRAM "zadanie2"

/**
 * Rozmiar bufora, w którym budowane jest polecenie uruchamiające program
 * usuwający grupę klocków.
 *
 * Zależy od wartości stałych `PROGRAM`, `WIERSZE`, `KOLUMNY`, `PIERWSZY`
 * i `DRUGI`.
 */
#define ROZMIAR_BUFORA 256


/* postać interfejsu użytkownika */

/**
 * Liczba znaków zajmowanych na ekranie przez jeden klocek.
 *
 * Zależy od wartości `RODZAJE`.
 */
#define SZEROKOSC_POLA 2

/**
 * Wiersz lewego górnego rogu planszy.
 */
#define PIERWSZY_WIERSZ (((LINES) - (WIERSZE)) / 2)

/**
 * Kolumna lewego górnego rogu planszy.
 */
#define PIERWSZA_KOLUMNA (((COLS) - (SZEROKOSC_POLA) * (KOLUMNY)) / 2)

/**
 * Liczba znaków zajmowanych na ekranie przez informację o punktach.
 *
 * Zależy od wartości `WIERSZE` i `KOLUMNY` oraz od sposobu punktowania.
 */
#define SZEROKOSC_PUNKTOW 8

/**
 * Wiersz planszy, w którym jest kursor w chwili rozpoczęcia gry.
 */
#define POCZATKOWY_WIERSZ 0

/**
 * Kolumna planszy, w której jest kursor w chwili rozpoczęcia gry.
 */
#define POCZATKOWA_KOLUMNA 0

/**
 * Przerwanie gry (Escape).
 */
#define PRZERWANIE 27

/**
 * Usunięcie grupy.
 */
#define WYBOR ' '

/**
 * Przesunięcie kursora w lewo.
 */
#define LEWO KEY_LEFT

/**
 * Przesunięcie kursora w prawo.
 */
#define PRAWO KEY_RIGHT

/**
 * Przesunięcie kursora w górę.
 */
#define GORA KEY_UP

/**
 * Przesunięcie kursora w dół.
 */
#define DOL KEY_DOWN

/**
 * Wiersz, w którym jest wyświetlana liczba punktów.
 */
#define WIERSZ_PUNKTOW 0

/**
 * Kolumna, w której jest wyświetlana liczba punktów.
 */
#define KOLUMNA_PUNKTOW ((COLS) - (SZEROKOSC_PUNKTOW))


/* struktury danych */

/**
 * Współrzędne pola na planszy.
 *
 * `wiersz` to numer wiersza, od `0` do `WIERSZE - 1`.
 * `kolumna` to numer kolumny, od `0` do `KOLUMNY - 1`.
 */
typedef struct {
    int wiersz;
    int kolumna;
} wspolrzedne;


/* operacje na współrzędnych */

/**
 * Daje współrzędne pola w wierszu `w` i kolumnie `k`.
 */
wspolrzedne nowe(int w, int k) {
    wspolrzedne wynik;
    wynik.wiersz = w;
    wynik.kolumna = k;
    return wynik;
}

/**
 * Daje sumę współrzędnych `a` i `b`.
 */
wspolrzedne suma(wspolrzedne a, wspolrzedne b) {
    wspolrzedne wynik = a;
    wynik.wiersz += b.wiersz;
    wynik.kolumna += b.kolumna;
    return wynik;
}

/**
 * Sprawdza, czy współrzędne `a` są zerowe.
 */
bool sa_zerowe(wspolrzedne a) {
    return ((a.wiersz == 0) && (a.kolumna == 0));
}

/**
 * Sprawdza, czy `a` to poprawne współrzędne pola.
 */
bool sa_poprawne(wspolrzedne a) {
    return (0 <= a.wiersz) && (a.wiersz < WIERSZE)
            && (0 <= a.kolumna) && (a.kolumna < KOLUMNY);
}

/**
 * Przelicza współrzędne pola planszy `a` na współrzędne ekranu.
 *
 * Numer wiersza ekranu zapisuje na `*w`.
 * Numer kolumny ekranu zapisuje na `*k`.
 */
void na_ekranie(wspolrzedne a, int *w, int *k) {
    *w = PIERWSZY_WIERSZ + a.wiersz;
    *k = PIERWSZA_KOLUMNA + SZEROKOSC_POLA * a.kolumna;
}


/* tekstowy interfejs użytkownika */

/**
 * Pokazuje `x` jako zawartość pola o współrzędnych `a`.
 *
 * Wartość `x` reprezentuje klocek lub pole puste.
 */
void pokaz_jeden(wspolrzedne a, char x) {
    int w, k;
    na_ekranie(a, &w, &k);
    mvprintw(w, k, "%*c", SZEROKOSC_POLA, x);
}

/**
 * Pokazuje liczbę punktów `p`.
 */
void pokaz_punkty(int p) {
    mvprintw(WIERSZ_PUNKTOW, KOLUMNA_PUNKTOW, "%*d", SZEROKOSC_PUNKTOW, p);
}

/**
 * Odswieza interfejs użytkownika.
 */
void odswiez(void) {
    refresh();
}

/**
 * Daje informacje o naciśniętym klawiszu.
 */
int zobacz_klawisz(void) {
    return getch();
}

/**
 * Ustawia kursor na polu planszy o współrzędnych `p`.
 */
void przesun(wspolrzedne p) {
    int w, k;
    na_ekranie(p, &w, &k);
    move(w, k + SZEROKOSC_POLA - 1);
}

/**
 * Daje wektor ruchu określonego przez `c`.
 *
 * Jeśli `c` nie jest poprawnym ruchem, wynikiem są współrzędne zerowe.
 */
wspolrzedne rozpoznaj(int c) {
    switch (c) {
    case LEWO:
        return nowe(0, -1);
    case PRAWO:
        return nowe(0, 1);
    case GORA:
        return nowe(-1, 0);
    case DOL:
        return nowe(1, 0);
    default:
        return nowe(0, 0);
    }
}

/**
 * Zaczyna pracę interfejsu użytkownika.
 */
void inicjuj_interfejs(void) {
    initscr();
    noecho();
    keypad(stdscr, true);
    cbreak();
}

/**
 * Kończy pracę interfejsu użytkownika.
 */
void zamknij_interfejs(void) {
    endwin();
}


/* generator liczb pseudolosowych */

/*
 * Inicjuje generator liczb pseudolosowych.
 */
void inicjuj_generator(void) {
    srand((unsigned) time(NULL));
}

/**
 * Daje pseudolosową liczbę całkowitą z przedziału od `0` do `n - 1`.
 */
int randof(int n) {
    return (int) (rand() / (RAND_MAX + 1.0) * n);
}


/* rozgrywka */

/**
 * Losuje planszę, zapisuje ją w pliku `nazwa` i pokazuje na ekranie.
 */
void losuj(const char *nazwa) {
    FILE *f = fopen(nazwa, "w");
    assert(f != NULL);
    for (int w = 0; w < WIERSZE; ++w) {
        for (int k = 0; k < KOLUMNY; ++k) {
            char znak = '0' + (char) randof(RODZAJE);
            fputc(znak, f);
            pokaz_jeden(nowe(w, k), znak);
        }
        fputc('\n', f);
    }
    fclose(f);
}

/**
 * Czyta stan planszy i pokazuje ją na ekranie.
 *
 * Jako wynik daje liczbę pustych pól na planszy.
 */
int czytaj(const char *nazwa) {
    int puste = 0;
    FILE *f = fopen(nazwa, "r");
    assert(f);
    int znak;
    for (int w = 0; w < WIERSZE; ++w) {
        for (int k = 0; k < KOLUMNY; ++k) {
            znak = fgetc(f);
            assert(znak != EOF);
            assert((znak == PUSTE) ||
                    (('0' <= znak) && (znak < '0' + RODZAJE)));
            if (znak == PUSTE) {
                ++puste;
            }
            pokaz_jeden(nowe(w, k), (char) znak);
        }
        znak = fgetc(f);
        assert(znak == '\n');
    }
    znak = fgetc(f);
    assert(znak == EOF);
    fclose(f);
    return puste;
}

/**
 * Usuwa grupę zawierającą pole `w`.
 * 
 * Aktualny stan planszy czyta z pliku `aktualny`.
 * Nowy stan planszy zapisuje do pliku `wynikowy`.
 * Korzysta z programu zewnętrznego `PROGRAM`.
 */
void usun(wspolrzedne w, const char *aktualny, const char *wynikowy) {
    char bufor[ROZMIAR_BUFORA];
    int x = snprintf(bufor, sizeof bufor, "./%s %d %d < %s > %s",
            PROGRAM, w.wiersz, w.kolumna, aktualny, wynikowy);
    assert(x > 0);
    x = system(bufor);
    assert(x == 0);
}

/**
 * Daje nagrodę za usunięcie `n` klocków.
 */
int nagroda(int n) {
    if (n > 1) {
        return (n - 2) * (n - 2);
    } else {
        return 0;
    }
}

/**
 * Zamienia wartości `*a` i `*b`.
 */
void zamien(char **a, char **b) {
    char *pomoc = *a;
    *a = *b;
    *b = pomoc;
}

/**
 * Prowadzi grę.
 */
void graj(void) {
    int puste = 0;
    int punkty = 0;
    char *aktualny = PIERWSZY;
    char *wynikowy = DRUGI;
    wspolrzedne w = nowe(POCZATKOWY_WIERSZ, POCZATKOWA_KOLUMNA);
    pokaz_punkty(punkty);
    przesun(w);
    odswiez();
    int c;
    while ((c = zobacz_klawisz()) != PRZERWANIE) {
        if (c == WYBOR) {
            usun(w, aktualny, wynikowy);
            int poprzednio = puste;
            puste = czytaj(wynikowy);
            zamien(&aktualny, &wynikowy);
            punkty += nagroda(puste - poprzednio);
            pokaz_punkty(punkty);
            przesun(w);
            odswiez();
        } else {
            wspolrzedne d = rozpoznaj(c);
            if (!sa_zerowe(d)) {
                wspolrzedne n = suma(w, d);
                if (sa_poprawne(n)) {
                    w = n;
                    przesun(w);
                    odswiez();
                }
            }
        }
    }
}

/**
 * Uruchamia grę.
 */
int main(void) {
    assert((1 <= RODZAJE) && (RODZAJE <= 10));
    inicjuj_generator();
    inicjuj_interfejs();
    losuj(PIERWSZY);
    graj();
    zamknij_interfejs();
    return 0;
}
