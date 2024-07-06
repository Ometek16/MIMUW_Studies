#include <stdio.h>
#include <ctype.h>
#include <stdbool.h>
#include <stdlib.h>
#include <assert.h>

#ifndef WIERSZE
#define WIERSZE 22
#endif

#ifndef KOLUMNY
#define KOLUMNY 80
#endif

typedef struct node_kolumna {
    int index;
    bool alive;
    int friends;
    int srcX;
    int srcY;
    struct node_kolumna* prev;
    struct node_kolumna* next;
} node_kolumna;

typedef struct node_wiersz {
    int index;
    struct node_wiersz* prev;
    struct node_wiersz* next;
    struct node_kolumna* kolumna;
} node_wiersz;

node_kolumna* new_node_kolumna(int index, bool alive, int friends, node_kolumna* prev, node_kolumna* next) {
    node_kolumna* new_kolumna = malloc(sizeof(node_kolumna));
    new_kolumna->index = index;
    new_kolumna->alive = alive;
    new_kolumna->friends = friends;
    new_kolumna->prev = prev;
    new_kolumna->next = next;
    return new_kolumna;
}

node_wiersz* new_node_wiersz(int index, node_wiersz* prev, node_wiersz* next, node_kolumna* kolumna) {
    node_wiersz* new_wiersz = malloc(sizeof(node_wiersz));
    new_wiersz->index = index;
    new_wiersz->prev = prev;
    new_wiersz->next = next;
    new_wiersz->kolumna = kolumna;
    return new_wiersz;
}

void check(node_wiersz* head) {
    bool t1 = false;
    bool sus = true;
    while (head) {
        assert(sus);
        if (t1)
            assert(head->prev->next == head);
        if (head->next)
            assert(head->next->prev == head);
        else 
            sus = false;
        node_kolumna* kol = head->kolumna;
        bool t2 = false;
        bool sus2 = true;
        while (kol) {
            assert(sus2);
            if (t2)
                assert(kol->prev->next == kol);
            if (kol->next)
                assert(kol->next->prev == kol);
            else 
                sus2 = false;
            t2 = true;
            kol = kol->next;
        }
        t1 = true;
        head = head->next;
    }
}


node_wiersz* wczytajAktualnyStan() {
    node_wiersz* atrapa_wiersz = malloc(sizeof(node_wiersz));
    node_wiersz* poprzedni_wiersz = atrapa_wiersz;
    atrapa_wiersz->next = NULL;

    char slash = getchar();
    int wiersz_index, kolumna_index;

    while (slash == '/') {
        char check = getchar();
        if (check == '\n')
            break;
        ungetc(check, stdin);

        scanf("%d", &wiersz_index);

        node_wiersz* tmp_wiersz = new_node_wiersz(wiersz_index, poprzedni_wiersz, NULL, NULL);
        poprzedni_wiersz->next = tmp_wiersz;
        
        node_kolumna* atrapa_kolumna = malloc(sizeof(node_kolumna));
        node_kolumna* poprzednia_kolumna = atrapa_kolumna;
        atrapa_kolumna->next = NULL;

        char space = ' ';
        while (space == ' ') {
            scanf("%d", &kolumna_index);

            node_kolumna* tmp_kolumna = new_node_kolumna(kolumna_index, true, 0, poprzednia_kolumna, NULL);

            poprzednia_kolumna->next = tmp_kolumna;
            poprzednia_kolumna = tmp_kolumna;

            space = getchar();
        }
        atrapa_kolumna->next->prev = NULL;
        tmp_wiersz->kolumna = atrapa_kolumna->next;
        free(atrapa_kolumna);

        poprzedni_wiersz = tmp_wiersz;
        slash = getchar();
    }

    node_wiersz* head = atrapa_wiersz->next;
    free(atrapa_wiersz);
    head->prev = NULL;
    return head;
}

void zrzutStanuGeneracji(node_wiersz* head) {
    while (head) {
        printf("/%d", head->index);
        node_kolumna* tmp = head->kolumna;
        while (tmp) {
            printf(" %d", tmp->index);
            tmp = tmp->next;
        }
        printf("\n");
        head = head->next;
    }
    printf("/\n");
}

void narysujGeneracje(node_wiersz* head, int wiersz_startowy, int kolumna_startowa) {
    for (int i = wiersz_startowy; i < wiersz_startowy + WIERSZE; i++) {
        while (head && head->index < i)
            head = head->next;
        if (head && head->index == i) {
            node_kolumna* aktualna_kolumna = head->kolumna;
            for (int j = kolumna_startowa; j < kolumna_startowa + KOLUMNY; j++) {
                while (aktualna_kolumna && aktualna_kolumna->index < j)
                    aktualna_kolumna = aktualna_kolumna->next;
                if (aktualna_kolumna && aktualna_kolumna->index == j)
                    printf("0");
                else
                    printf(".");
            }
        }
        else
            for (int j = 0; j < KOLUMNY; j++)
                printf(".");
        printf("\n");
    }
    for (int i = 0; i < KOLUMNY; i++)
        printf("=");
    printf("\n");
}

void testowePrintowanie(node_wiersz* head) {
    while (head) {
        //printf("WIERSZ: %d\n", head->index);
        node_kolumna* kol = head->kolumna;
        int i = 0;
        while (kol) {
            //printf("\tKOLUMNA: %d -> %d | %d\n", kol->index, kol->alive, kol->friends);
            kol = kol->next;
            i++;
        }
        head = head->next;
    }
}

void delete(node_wiersz* head) {
    while(head) {
        node_kolumna* aktualna_kolumna = head->kolumna;
        node_wiersz* usuwany_wiersz = head;
        head = head->next;
        free(usuwany_wiersz);

        while(aktualna_kolumna) {
            node_kolumna* usuwana_kolumna = aktualna_kolumna;
            aktualna_kolumna = aktualna_kolumna->next;
            free(usuwana_kolumna);
        }
    }
}

void rozszerzKolumnyPion(node_wiersz** glowny_wiersz, node_wiersz** akutalizowany_wiersz) {
    node_kolumna* head = (*akutalizowany_wiersz)->kolumna;
    node_kolumna* head_copy = head;
    node_kolumna* glowa = (*glowny_wiersz)->kolumna;
    //printf("RozszerzamyKolumnyPion: %d -> %d\n", (*glowny_wiersz)->index, (*akutalizowany_wiersz)->index);

    while(glowa) {
        if (!glowa->alive){
            glowa = glowa->next;
            continue;
        }
        //printf("Szukam glowy: %d\n", glowa->index);
        node_kolumna* poprzednia_kolumna = (head) ? head->prev : NULL;
        node_kolumna* aktualna_kolumna = head;
        node_kolumna* nastepna_kolumna = (head) ? head->next : NULL;

        while (head && head->index < glowa->index) {
            poprzednia_kolumna = aktualna_kolumna;
            aktualna_kolumna = nastepna_kolumna;
            nastepna_kolumna = (nastepna_kolumna) ? nastepna_kolumna->next : NULL;
            head = head->next;
        }
        //if (poprzednia_kolumna) 
            //printf("PREHEAD: %d || ", poprzednia_kolumna->index);
        //if (aktualna_kolumna)
            //printf("HEAD: %d || %d || ", aktualna_kolumna->index, aktualna_kolumna->prev ? aktualna_kolumna->prev->index : 69420);


        // aktualizowanie elementu (index - 1)
        if (!poprzednia_kolumna) {
            poprzednia_kolumna = new_node_kolumna(glowa->index - 1, 0, 1, NULL, aktualna_kolumna);
            poprzednia_kolumna->srcX = glowa->index;
            head_copy = poprzednia_kolumna;
            if (aktualna_kolumna)
                aktualna_kolumna->prev = poprzednia_kolumna;
            //printf("L1 ");
        }
        else if (poprzednia_kolumna->index != glowa->index - 1) {
            node_kolumna* nowa_kolumna = new_node_kolumna(glowa->index - 1, 0, 1, poprzednia_kolumna, aktualna_kolumna);
            poprzednia_kolumna->srcX = -glowa->index;
            poprzednia_kolumna->next = nowa_kolumna;
            if (aktualna_kolumna)
                aktualna_kolumna->prev = nowa_kolumna;
            poprzednia_kolumna = nowa_kolumna;
            //printf("L2 ");
        }
        else if (aktualna_kolumna && aktualna_kolumna->index == glowa->index - 1){
            aktualna_kolumna->friends = aktualna_kolumna->friends + 1;
            //printf("L3 ");
        }
        else {
            poprzednia_kolumna->friends = poprzednia_kolumna->friends + 1;
            //printf("L4 ");
        }

        // aktualizowanie elementu (index) 
        if (!aktualna_kolumna) {
            aktualna_kolumna = new_node_kolumna(glowa->index, 0, 1, poprzednia_kolumna, NULL);
            poprzednia_kolumna->next = aktualna_kolumna;
            //printf("M1 ");
        }
        else if (aktualna_kolumna->index == glowa->index) {
            aktualna_kolumna->friends = aktualna_kolumna->friends + 1;
            //printf("M2 ");
        }
        else {
            node_kolumna* nowa_kolumna = new_node_kolumna(glowa->index, 0, 1, poprzednia_kolumna, aktualna_kolumna);
            poprzednia_kolumna->next = nowa_kolumna;
            aktualna_kolumna->prev = nowa_kolumna;
            poprzednia_kolumna = nowa_kolumna;
            //printf("M3 ");
        }

        // aktualizowanie elementu (index + 1)
        if (aktualna_kolumna->index == glowa->index + 1) {
            aktualna_kolumna->friends = aktualna_kolumna->friends + 1;
            //printf("R1 ");
        }
        else if (nastepna_kolumna && nastepna_kolumna->index == glowa->index + 1){
            nastepna_kolumna->friends = nastepna_kolumna->friends + 1;
            //printf("R2 ");
        }
        else if (aktualna_kolumna->index > glowa->index + 1) {
            node_kolumna* nowa_kolumna = new_node_kolumna(glowa->index + 1, 0, 1, poprzednia_kolumna, aktualna_kolumna);
            poprzednia_kolumna->next = nowa_kolumna;
            aktualna_kolumna->prev = nowa_kolumna;
            poprzednia_kolumna = nowa_kolumna;
            //printf("R3 ");  
        }
        else {
            node_kolumna* nowa_kolumna = new_node_kolumna(glowa->index + 1, 0, 1, aktualna_kolumna, nastepna_kolumna);
            aktualna_kolumna->next = nowa_kolumna;
            if (nastepna_kolumna)
                nastepna_kolumna->prev = nowa_kolumna;
            nastepna_kolumna = nowa_kolumna;
            //printf("R4 ");
        }
        //printf(" | %d %d %d\n", poprzednia_kolumna->index, aktualna_kolumna->index, nastepna_kolumna->index);
        head = poprzednia_kolumna;
        glowa = glowa->next;
    }

    (*akutalizowany_wiersz)->kolumna = head_copy;
}

void rozszerzKolumnyPoziom(node_wiersz** akutalizowany_wiersz) {
    node_kolumna* head = (*akutalizowany_wiersz)->kolumna;
    node_kolumna* head_copy = head;

    while (head) {
        if (!head->alive) {
            head = head->next;
            continue;
        }
        node_kolumna* poprzednia_kolumna = head->prev;
        node_kolumna* aktualna_kolumna = head;
        node_kolumna* nastepna_kolumna = head->next;
        bool skok = false;
        //printf("KOL: %d || ", head->index);
        // Aktualizowanie elementu (index - 1)
        if (poprzednia_kolumna == NULL) {
            poprzednia_kolumna = new_node_kolumna(aktualna_kolumna->index - 1, false, 1, NULL, aktualna_kolumna);
            head_copy = poprzednia_kolumna;
            aktualna_kolumna->prev = poprzednia_kolumna;
            //printf("Lewy_1 ");
        }
        else if (poprzednia_kolumna->index != aktualna_kolumna->index - 1) {
            node_kolumna* nowa_kolumna = new_node_kolumna(aktualna_kolumna->index - 1, false, 1, poprzednia_kolumna, aktualna_kolumna);
            poprzednia_kolumna->next = nowa_kolumna;
            aktualna_kolumna->prev = nowa_kolumna;
            poprzednia_kolumna = nowa_kolumna;
            //printf("Lewy_2 ");
        }
        else {
            poprzednia_kolumna->friends = poprzednia_kolumna->friends + 1;
            //printf("Lewy_3 ");
        }

        // Aktualizowanie elementu (index + 1)
        if (nastepna_kolumna == NULL) {
            nastepna_kolumna = new_node_kolumna(aktualna_kolumna->index + 1, false, 1, aktualna_kolumna, NULL);
            aktualna_kolumna->next = nastepna_kolumna;
            skok = true;
            //printf("Prawy_1 ");
        }
        else if (nastepna_kolumna->index != aktualna_kolumna->index + 1) {
            node_kolumna* nowa_kolumna = new_node_kolumna(aktualna_kolumna->index + 1, false, 1, aktualna_kolumna, nastepna_kolumna);
            aktualna_kolumna->next = nowa_kolumna;
            nastepna_kolumna->prev = nowa_kolumna;
            nastepna_kolumna = nowa_kolumna;
            skok = true;
            //printf("Prawy_2 ");
        }
        else {
            nastepna_kolumna->friends = nastepna_kolumna->friends + 1;
            //printf("Prawy_3");
        }
        //printf("\n");
        if (skok)
            head = head->next;
        head = head-> next;
    }
    (*akutalizowany_wiersz)->kolumna = head_copy;
}


node_wiersz* rozszerzWiersze(node_wiersz* head) {
    node_wiersz* head_copy = head;
    while (head) {
        //printf("ROZSZERZAM WIERSZ: %d\n", head->index);
        node_wiersz* poprzedni_wiersz = head->prev;
        node_wiersz* aktualny_wiersz = head;
        node_wiersz* nastepny_wiersz = head->next;
        bool skok = false;

        if (poprzedni_wiersz == NULL) {
            poprzedni_wiersz = new_node_wiersz(aktualny_wiersz->index - 1, NULL, aktualny_wiersz, NULL);
            head_copy = poprzedni_wiersz;
            aktualny_wiersz->prev = poprzedni_wiersz;
        }
        else if (poprzedni_wiersz->index != aktualny_wiersz->index - 1) {
            node_wiersz* nowy_wiersz = new_node_wiersz(aktualny_wiersz->index - 1, poprzedni_wiersz, aktualny_wiersz, NULL);
            poprzedni_wiersz->next = nowy_wiersz;
            aktualny_wiersz->prev = nowy_wiersz;
            poprzedni_wiersz = nowy_wiersz;
        }

        if (nastepny_wiersz == NULL) {
            nastepny_wiersz = new_node_wiersz(aktualny_wiersz->index + 1, aktualny_wiersz, NULL, NULL);
            aktualny_wiersz->next = nastepny_wiersz;
            skok = true;
        }
        else if (nastepny_wiersz->index != aktualny_wiersz->index + 1) {
            node_wiersz* nowy_wiersz = new_node_wiersz(aktualny_wiersz->index + 1, aktualny_wiersz, nastepny_wiersz, NULL);
            aktualny_wiersz->next = nowy_wiersz;
            nastepny_wiersz->prev = nowy_wiersz;
            nastepny_wiersz = nowy_wiersz;
            skok = true;
        }

        rozszerzKolumnyPoziom(&aktualny_wiersz); //! DZIALA
        check(head_copy);
        //printf("==========\n");
        testowePrintowanie(head_copy);
        rozszerzKolumnyPion(&aktualny_wiersz, &poprzedni_wiersz); //! DZIALA
        check(head_copy);
        //printf("==========\n");
        testowePrintowanie(head_copy);
        rozszerzKolumnyPion(&aktualny_wiersz, &nastepny_wiersz); //! DZIALA
        check(head_copy);
        //printf("===========\n");
        testowePrintowanie(head_copy);

        if (skok)
            head = head->next;
        head = head->next;
    }
    return head_copy;
}

node_wiersz* aktualizujZywotnosc(node_wiersz* head) {
    node_wiersz* head_copy = head;
    while (head) {
        node_kolumna* kolumna = head->kolumna;
        while (kolumna) {
            if (kolumna->alive && kolumna->friends != 2 && kolumna->friends != 3)
                kolumna->alive = false;
            if (!kolumna->alive && kolumna->friends == 3)
                kolumna->alive = true;
            kolumna->friends = 0;
            kolumna = kolumna->next;
        }
        head = head->next;
    }
    return head_copy;
}

node_wiersz* skurczWiersze(node_wiersz* head) {
    node_wiersz* head_copy = head;
    while (head) {
        node_kolumna* kolumna = head->kolumna;
        node_kolumna* kolumna_copy = kolumna;

        while (kolumna) {
            ////printf("%d -> ", kolumna->index);
            if (kolumna->alive) {
                kolumna = kolumna->next;
                continue;
            }
            node_kolumna* usuwana_kolumna = kolumna;
            node_kolumna* nastepna_kolumna = kolumna->next;
            node_kolumna* poprzednia_kolumna = kolumna->prev;
            
            if (!poprzednia_kolumna) {
                if (nastepna_kolumna)
                    nastepna_kolumna->prev = NULL;
                kolumna_copy = nastepna_kolumna;
                free(usuwana_kolumna);
            }
            else {
                if (nastepna_kolumna)
                    nastepna_kolumna->prev = poprzednia_kolumna;
                poprzednia_kolumna->next = nastepna_kolumna;
                free(usuwana_kolumna);
            }
            kolumna = kolumna->next;
        }
        head->kolumna = kolumna_copy;

        if (head->kolumna)
            head = head->next;
        else {
            node_wiersz* usuwany_wiersz = head;
            if (!head->prev)
                head_copy = head->next;
            else 
                head->prev->next = head->next;
            if (head->next)
                head->next->prev = head->prev;
            head = head->next;
            free(usuwany_wiersz);
        }
    }
    return head_copy;
}

node_wiersz* nextGeneration(node_wiersz** head) {
    *head = rozszerzWiersze(*head);
    testowePrintowanie(*head);
    *head = aktualizujZywotnosc(*head);
    //testowePrintowanie(*head);
    *head = skurczWiersze(*head);
    //testowePrintowanie(*head);
    //printf(">>NEXT GEN<<\n");
    return *head;
}

bool wczytajPolecenie(node_wiersz** head, int* wiersz_startowy, int* kolumna_startowa) {
    narysujGeneracje(*head, *wiersz_startowy, *kolumna_startowa);
    char tmp = getchar();
    if (tmp == '.')
        return false;

    if (tmp == '\n') {
        *head = nextGeneration(head);
        testowePrintowanie(*head); //! WYJEBAC
        //return false;   //! DEBUG
    }

    if (isdigit(tmp)){
        ungetc(tmp, stdin);
        int numer1, numer2;
        scanf("%d", &numer1);
        tmp = getchar();

        if (tmp == ' ') {
            scanf("%d", &numer2);
            *wiersz_startowy = -numer1;
            *kolumna_startowa = numer2;
            tmp = getchar();
        }
        else if (numer1 == 0)
            zrzutStanuGeneracji(*head);
        else
            while (numer1--) {
                nextGeneration(head);
                //testowePrintowanie(*head);
            }
    }
    else {
        printf("CO JEST KURWA: %c\n", tmp);
    }
    return true;
}

int main() {
    int wiersz_startowy = 1;
    int kolumna_startowa = 1;
    node_wiersz* head = wczytajAktualnyStan();
    check(head);
    while (wczytajPolecenie(&head, &wiersz_startowy, &kolumna_startowa))
        continue;

    delete(head);
}