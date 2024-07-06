#include <stdio.h>
#include <stdlib.h>
#include <time.h>

#ifndef WIERSZE
#define WIERSZE 10
#endif

#ifndef KOLUMNY
#define KOLUMNY 15
#endif

#ifndef RODZAJE
#define RODZAJE 4
#endif

int randof(int n) {
    return (int) (rand() / (RAND_MAX + 1.0) * n);
}

int main() {
    int seed;
    scanf("%d", &seed);

    srand(seed);

    for (int i=0; i<WIERSZE; i++) {
        for (int j=0; j<KOLUMNY; j++) 
            printf("%d", randof(RODZAJE));
        printf("\n");
    }
}