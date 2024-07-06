#include <stdio.h>

int main() {
    int seed;
    scanf("%d", &seed);

    for (int i=0; i<3; i++) {
        int tab[3] = {0, 0, 0};
        if (seed & 1)
            tab[0] = 1;
        seed >>= 1;
        if (seed & 1)
            tab[1] = 1;
        seed >>= 1;        
        if (seed & 1)
            tab[2] = 1;
        seed >>= 1;
        if (tab[0] || tab[1] || tab[2]) {
            printf("/%d", i+1);
            for (int j=0; j<3; j++)
                if (tab[j])
                    printf(" %d", j+1);
            printf("\n");
        }
    }
    printf("/\n1\n0\n.\n");
}