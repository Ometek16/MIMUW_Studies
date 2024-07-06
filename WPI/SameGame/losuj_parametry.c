#include <ncurses.h>

#include <assert.h>
#include <stdbool.h>
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
    return (int) ((rand() % n) + (rand() % n))%n;
}

int main() {
    int seed;
    scanf("%d", &seed);
    srand(time(NULL) + seed);
    printf("%d %d\n", randof(WIERSZE), randof(KOLUMNY));
}
