#include <stdio.h>
#include <string.h>
#include <stdbool.h>

#ifndef WIERSZE
#define WIERSZE 8
#endif

#ifndef KOLUMNY
#define KOLUMNY 8
#endif

#ifndef DLUGOSC
#define DLUGOSC 5
#endif

#define MAX_STR_SIZE 50 
#define MOVES_SIZE 4

struct pair {
    int first;
    int second;
};

const struct pair moves[MOVES_SIZE] = {{1, 0}, {0, 1}, {1, 1}, {1, -1}};

static void reverse(char *str) {   
    size_t len = strlen(str);
    for (size_t i = 0; i < len / 2; i++) {   
        char temp = str[i];  
        str[i] = str[len - i - 1];  
        str[len - i - 1] = temp;  
    }  
}  

static void concatenate(char *str1, char c, char *str2) {
    reverse(str1);
    strncat(str1, &c, 1);
    strcat(str1, str2);
}

static void substr(char *ans, char *str, size_t begin, size_t len) {
    if (strlen(str) < len + begin)
        return;
    size_t curr_len = 0;
    while (curr_len < len) {
        ans[curr_len] = str[begin + curr_len];
        curr_len++;
    }
}

static bool czy_ma_palindrom(char *slowo) {
    size_t len = strlen(slowo);

    for (size_t pocz = 0; pocz + DLUGOSC <= len; pocz++) {
        char podslowo[MAX_STR_SIZE] = "";
        substr(podslowo, slowo, pocz, DLUGOSC);
        bool czy_palindromem = true;
        for (size_t i = 0; i <= DLUGOSC / 2 && czy_palindromem; i++)
            if (podslowo[i] != podslowo[DLUGOSC - i - 1])
                czy_palindromem = false;
        if (czy_palindromem)
            return true;
    }
    return false;
}

static void pokaz(char plansza[WIERSZE][KOLUMNY]) {
    for (int i = WIERSZE - 1; i >= 0; i--) {
        for (int j = 0; j < KOLUMNY; j++)
            printf(" %c", plansza[i][j]);
        printf("\n");
    }
    for (int i = 0; i < KOLUMNY; i++)
        printf(" %c", 'a' + i);
    printf("\n");
}

static void ruch(char plansza[WIERSZE][KOLUMNY], int poziom[WIERSZE], int id_gracza, char id_kolumny) {
    plansza[poziom[id_kolumny - 'a']][id_kolumny - 'a'] = (char) ('0' + id_gracza);
    poziom[id_kolumny - 'a']++;
}

static bool czy_koniec(char plansza[WIERSZE][KOLUMNY], int x, int y) {
    for (int i = 0; i < MOVES_SIZE; i++) {
        int new_x = x + moves[i].first;
        int new_y = y + moves[i].second;
        char str_left[MAX_STR_SIZE] = "";
        size_t str_left_size = 0;
        while (0 <= new_x && new_x < WIERSZE && 0 <= new_y && new_y < KOLUMNY && plansza[new_x][new_y] != '-') {
            str_left[str_left_size++] = plansza[new_x][new_y];
            new_x += moves[i].first;
            new_y += moves[i].second;
        }
        new_x = x - moves[i].first;
        new_y = y - moves[i].second;
        char str_right[MAX_STR_SIZE] = "";
        size_t str_right_size = 0;
        while (0 <= new_x && new_x < WIERSZE && 0 <= new_y && new_y < KOLUMNY && plansza[new_x][new_y] != '-') {
            str_right[str_right_size++] = plansza[new_x][new_y];
            new_x -= moves[i].first;
            new_y -= moves[i].second;
        }

        concatenate(str_left, plansza[x][y], str_right);
        if (czy_ma_palindrom(str_left))
            return true;
    }   
    
    return false;
}

int main() {
    char plansza[WIERSZE][KOLUMNY];
    int poziom[KOLUMNY] = {0};
    for (int i = 0; i < WIERSZE; i++)
        for (int j = 0; j < KOLUMNY; j++)
            plansza[i][j] = '-';

    int czyja_kolej = 1;
    bool czy_rezygnacja = false;
    bool czy_wygrana = false;
    int c = '\n';

    while (!czy_rezygnacja && !czy_wygrana) {
        pokaz(plansza);
        printf("%d:\n", czyja_kolej);

        while (c != '\n' && c != EOF)
            c = getchar();
        if (c != EOF)
            c = getchar();

        if (c == '.') {
            czy_rezygnacja = true;
        }
        else {
            ruch(plansza, poziom, czyja_kolej, (char) c);
            czy_wygrana = czy_koniec(plansza, poziom[c - 'a'] - 1, c - 'a');
            czyja_kolej = 3 - czyja_kolej;
        }
    }
    if (czy_wygrana) {
        pokaz(plansza);
        printf("%d!\n", 3 - czyja_kolej);
    }
}
