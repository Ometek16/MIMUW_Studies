#include <stdio.h>
#include <stdlib.h>
#include <time.h>

#ifndef WIERSZE
#define WIERSZE 22
#endif

#ifndef KOLUMNY
#define KOLUMNY 80
#endif

#ifndef CHANCE
#define CHANCE 100
#endif

int randof(int n) {
    return (int) (rand() / (RAND_MAX + 1.0) * n);
}

int tab[WIERSZE][KOLUMNY];

int main() {
    int seed;
    scanf("%d", &seed);

    srand(seed);

    for (int i=0; i<WIERSZE; i++)
        for (int j=0; j<KOLUMNY; j++) 
            tab[i][j] = (randof(KOLUMNY) % CHANCE == 0);

    for (int i=0; i<WIERSZE; i++) {
        int ile = 0;
        for (int j=0; j<KOLUMNY; j++) 
            ile += tab[i][j];
        if (ile) {
            printf("/%d", i);
            for (int j=0; j<KOLUMNY; j++) 
                printf(" %d", j);
            printf("\n");
        }
    }
    printf("/\n\n0\n.");
}