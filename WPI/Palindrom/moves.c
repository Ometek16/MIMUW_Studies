#include <stdio.h>

struct pair {
    int first;
    int second;
};
const int moves_size = 4;
const struct pair moves[moves_size] = {{1, 0}, {0, 1}, {1, 1}, {1, -1}};

int main(){
    
    for (int i=0; i<moves_size; i++){
        printf("REV: %d, %d\n", -moves[i].first, -moves[i].second);
        printf("NOR: %d, %d\n", moves[i].first, moves[i].second);
    }
}