#include <stdio.h>
#include <stdbool.h>
#include <stdlib.h>
#include <assert.h>

#ifndef WIERSZE
#define WIERSZE 10
#endif

#ifndef KOLUMNY
#define KOLUMNY 15
#endif

#ifndef RODZAJE
#define RODZAJE 4
#endif

#define PUSTE '.'
#define MOVES_SIZE 4

struct pair {
    int first;
    int second;
};

const struct pair moves[MOVES_SIZE] = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};

static bool wZakresie(int x, int y) {
    return (0 <= x && x < WIERSZE && 0 <= y && y < KOLUMNY);
}

static void DFS(char plansza[WIERSZE][KOLUMNY], int x, int y, char typ) {
    if (plansza[x][y] == PUSTE)
        return;
    plansza[x][y] = PUSTE;
    for (int i = 0; i < MOVES_SIZE; i++) {
        int tmp_x = x + moves[i].first;
        int tmp_y = y + moves[i].second;
        if (wZakresie(tmp_x, tmp_y) && plansza[tmp_x][tmp_y] == typ)
            DFS(plansza, tmp_x, tmp_y, typ);
    }
}

static void scalWPionie(char plansza[WIERSZE][KOLUMNY]) {
    for (int j = 0; j < KOLUMNY; j++) {
        int puste_ptr = WIERSZE - 1;
        int nie_puste_ptr = WIERSZE - 1;
        while (nie_puste_ptr >= 0 && puste_ptr >= 0 && nie_puste_ptr <= puste_ptr) {
            while (puste_ptr >= 0 && plansza[puste_ptr][j] != PUSTE)
                puste_ptr--;

            nie_puste_ptr = puste_ptr - 1;
            while (nie_puste_ptr >= 0 && plansza[nie_puste_ptr][j] == PUSTE)
                nie_puste_ptr--;

            if (nie_puste_ptr >= 0 && puste_ptr >= 0 && nie_puste_ptr < puste_ptr) {
                char tmp = plansza[puste_ptr][j];
                plansza[puste_ptr][j] = plansza[nie_puste_ptr][j];
                plansza[nie_puste_ptr][j] = tmp;
            }
            puste_ptr--;
            nie_puste_ptr--;
        }
    }
}

static bool sprawdzCzyKolumnaJestPusta(char plansza[WIERSZE][KOLUMNY], int kol) {
    for (int i=0; i < WIERSZE; i++) 
        if (plansza[i][kol] != PUSTE)
            return false;
    return true;
}

static void scalWPoziomie(char plansza[WIERSZE][KOLUMNY], int kolumny_ptr[KOLUMNY]) {
    bool czy_pusta_kolumna[KOLUMNY];
    for (int j = 0; j < KOLUMNY; j++)
        czy_pusta_kolumna[j] = sprawdzCzyKolumnaJestPusta(plansza, j);
    int puste_ptr = 0;
    int nie_puste_ptr = 0;
    while (puste_ptr < KOLUMNY && nie_puste_ptr < KOLUMNY) { 
        while (puste_ptr < KOLUMNY && !czy_pusta_kolumna[kolumny_ptr[puste_ptr]])
            puste_ptr++;

        nie_puste_ptr = puste_ptr + 1;
        while (nie_puste_ptr < KOLUMNY && czy_pusta_kolumna[kolumny_ptr[nie_puste_ptr]])
            nie_puste_ptr++;

        if (puste_ptr < KOLUMNY && nie_puste_ptr < KOLUMNY) {
            int tmp = kolumny_ptr[puste_ptr];
            kolumny_ptr[puste_ptr] = kolumny_ptr[nie_puste_ptr];
            kolumny_ptr[nie_puste_ptr] = tmp;
        }
        puste_ptr++;
        nie_puste_ptr++;
    }
}

static bool czyJestGrupa(char plansza[WIERSZE][KOLUMNY], int x, int y) {
    if (plansza[x][y] == PUSTE)
        return false;
    for (int i = 0; i < MOVES_SIZE; i++) {
        int tmp_x = x + moves[i].first;
        int tmp_y = y + moves[i].second;
        if (wZakresie(tmp_x, tmp_y) && plansza[tmp_x][tmp_y] == plansza[x][y])
            return true;
    }
    return false;
}

int main(int argc, char  *argv[]) {
    assert(argc == 3);
    const int pole_X = atoi(argv[1]);
    const int pole_Y = atoi(argv[2]);

    char plansza[WIERSZE][KOLUMNY];
    int kolumny_ptr[KOLUMNY];

    for (int j = 0; j < KOLUMNY; j++) 
        kolumny_ptr[j] = j;

    char nowa_linia = '\n';
    for (int i = 0; i < WIERSZE; i++) {
        for (int j = 0; j < KOLUMNY; j++) {
            scanf("%c", &plansza[i][j]);
        }
        scanf("%c", &nowa_linia);
    }
       
    if (czyJestGrupa(plansza, pole_X, pole_Y)) {
        DFS(plansza, pole_X, pole_Y, plansza[pole_X][pole_Y]);
        scalWPionie(plansza);
        scalWPoziomie(plansza, kolumny_ptr);
    }

    for (int i = 0; i < WIERSZE; i++) {
        for (int j = 0; j < KOLUMNY; j++)
            printf("%c", plansza[i][kolumny_ptr[j]]);
        printf("\n");
    }
}
