#include <stdio.h>
#include <string.h>
#include <stdbool.h>

#define DLUGOSC 5

const int MAX_STR_SIZE = 50;

void reverse(char *str) {   
    int len = strlen(str);
    for (int i = 0; i < len / 2; i++) {   
        char temp = str[i];  
        str[i] = str[len - i - 1];  
        str[len - i - 1] = temp;  
    }  
}  

void concatenate(char *str1, char c, char *str2) {
    reverse(str1);
    strncat(str1, &c, 1);
    strcat(str1, str2);
}

void substr(char *ans, char *str, int begin, int len) {
    if (strlen(str) < len + begin)
        return;
    int curr_len = 0;
    while (curr_len < len){
        ans[curr_len] = str[begin + curr_len];
        curr_len++;
    }
}

bool czy_ma_palindrom(char *slowo) {
    int len = strlen(slowo);

    for (int pocz = 0; pocz + DLUGOSC <= len; pocz++){
        char podslowo[MAX_STR_SIZE] = "";
        substr(podslowo, slowo, pocz, DLUGOSC);
        // printf("%s\n", podslowo);
        bool czy_palindromem = true;
        for (int i = 0; i <= DLUGOSC / 2 && czy_palindromem; i++)
            if (podslowo[i] != podslowo[DLUGOSC - i - 1])
                czy_palindromem = false;
        if (czy_palindromem)
            return true;
    }
    return false;
}

int main(){
    char str1[MAX_STR_SIZE] = "abca";
    char c = '-';
    char str2[MAX_STR_SIZE] = "dbcbd";

    concatenate(str1, c, str2);

    char str[MAX_STR_SIZE] = "abacaba";


    printf("%s\n", str1);

    int odp = czy_ma_palindrom(str);
    printf("ODP: %d\n", odp);
}