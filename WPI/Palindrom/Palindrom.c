#include <stdio.h>

#ifndef WIERSZE
#define WIERSZE 8
#endif

#ifndef KOLUMNY
#define KOLUMNY 8
#endif

#ifndef DLUGOSC
#define DLUGOSC 5
#endif

void pokaz(char plansza[][KOLUMNY]){
    for (int i = WIERSZE-1; i>=0; i--) {
        for (int j = 0; j < KOLUMNY; j++)
            printf(" %c", plansza[i][j]);
        printf("\n");
    }
    for (int i = 0; i < KOLUMNY; i++)
        printf(" %c", 'a'+i);
    printf("\n");
}

void ruch(char plansza[][KOLUMNY], int poziom[], int id_gracza, char id_kolumny){
    plansza[poziom[id_kolumny-'a']][id_kolumny-'a'] = '0' + id_gracza;
    poziom[id_kolumny-'a']++;
}


int main(){
    char plansza[WIERSZE][KOLUMNY];
    int poziom[WIERSZE] = {0};
    for (int i = 0; i < WIERSZE; i++)
        for (int j = 0; j < KOLUMNY; j++)
            plansza[i][j] = '-';

    pokaz(plansza);

    int c = getchar();
    int czyja_kolej = 1;   // Czyja jest teraz kolej
    while ((c != EOF) && (c != '.')){
        printf("%d:\n", czyja_kolej);
        ruch(plansza, poziom, czyja_kolej, (char) c);
        czyja_kolej = 3 - czyja_kolej;
        pokaz(plansza);
        while ((c != '\n') && (c != EOF))
            c = getchar();
        if (c != EOF)
            c = getchar();
    }
    if (c == '.')
        printf("%d:\n", czyja_kolej);
    else 
        printf("%d!\n", 3-czyja_kolej);
}

// valgrind --leak-check=full -q --error-exitcode=1 <test_1.in ./run | diff - test_1.out


/*

while (!koniec_gry and !rezygnacja){
    pokaz(tabela);

    c = input();
    if (c == '.'){
        rezygnacja = true;
        continue;
    }
    ruch(c);
    if (czy_wygral()){
        koniec_gry = true;
        continue;
    }
    printuj_kto_nastepny();
}
if (rezygnacja)
    print_kto_zjebal();
if (koniec_gdy)
    print_kto_wygral();


*/