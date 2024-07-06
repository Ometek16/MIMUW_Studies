#include <stdio.h>
#include <ctype.h>
#include <stdbool.h>
#include <stdlib.h>
#include <limits.h>

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

void delete_node_wiersz(node_wiersz* node) {
    free(node);
}

void delete_node_kolumna(node_kolumna* node) {
    free(node);
}

void delete(node_wiersz* head) {
    while(head) {
        node_kolumna* aktualna_kolumna = head->kolumna;
        node_wiersz* usuwany_wiersz = head;
        head = head->next;
        delete_node_wiersz(usuwany_wiersz);

        while(aktualna_kolumna) {
            node_kolumna* usuwana_kolumna = aktualna_kolumna;
            aktualna_kolumna = aktualna_kolumna->next;
            delete_node_kolumna(usuwana_kolumna);
        }
    }
}

node_wiersz* wczytajAktualnyStan() {
    node_wiersz* atrapa_wiersz = new_node_wiersz(0, NULL, NULL, NULL);
    node_wiersz* poprzedni_wiersz = atrapa_wiersz;

    char slash = (char) getchar();
    int wiersz_index, kolumna_index;

    while (slash == '/') {
        char check = (char) getchar();
        if (check == '\n')
            break;
        ungetc(check, stdin);

        scanf("%d", &wiersz_index);

        node_wiersz* tmp_wiersz = new_node_wiersz(wiersz_index, poprzedni_wiersz, NULL, NULL);
        poprzedni_wiersz->next = tmp_wiersz;
        
        node_kolumna* atrapa_kolumna = new_node_kolumna(0, 0, 0, NULL, NULL);
        node_kolumna* poprzednia_kolumna = atrapa_kolumna;

        char space = ' ';
        while (space == ' ') {
            scanf("%d", &kolumna_index);

            node_kolumna* tmp_kolumna = new_node_kolumna(kolumna_index, true, 0, poprzednia_kolumna, NULL);

            poprzednia_kolumna->next = tmp_kolumna;
            poprzednia_kolumna = tmp_kolumna;

            space = (char) getchar();
        }

        atrapa_kolumna->next->prev = NULL;
        tmp_wiersz->kolumna = atrapa_kolumna->next;
        delete_node_kolumna(atrapa_kolumna);

        poprzedni_wiersz = tmp_wiersz;
        slash = (char) getchar();
    }

    node_wiersz* head = atrapa_wiersz->next;
    delete_node_wiersz(atrapa_wiersz);
    if (head)
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

void rozszerzKolumnyPion(node_wiersz** glowny_wiersz, node_wiersz** akutalizowany_wiersz) {
    node_kolumna* head = (*akutalizowany_wiersz)->kolumna;
    node_kolumna* head_copy = head;
    node_kolumna* glowa = (*glowny_wiersz)->kolumna;

    while(glowa) {
        if (!glowa->alive) {
            glowa = glowa->next;
            continue;
        }

        node_kolumna* poprzednia_kolumna = (head) ? head->prev : NULL;
        node_kolumna* aktualna_kolumna = head;
        node_kolumna* nastepna_kolumna = (head) ? head->next : NULL;

        while (head && head->index < glowa->index) {
            poprzednia_kolumna = aktualna_kolumna;
            aktualna_kolumna = nastepna_kolumna;
            nastepna_kolumna = (nastepna_kolumna) ? nastepna_kolumna->next : NULL;
            head = head->next;
        }

        // Aktualizowanie elementu na pozycji (index - 1).
        if (!poprzednia_kolumna) {
            poprzednia_kolumna = new_node_kolumna(glowa->index - 1, 0, 1, NULL, aktualna_kolumna);
            head_copy = poprzednia_kolumna;
            if (aktualna_kolumna)
                aktualna_kolumna->prev = poprzednia_kolumna;
        }
        else if (poprzednia_kolumna->index != glowa->index - 1) {
            node_kolumna* nowa_kolumna = new_node_kolumna(glowa->index - 1, 0, 1, poprzednia_kolumna, aktualna_kolumna);
            poprzednia_kolumna->next = nowa_kolumna;
            if (aktualna_kolumna)
                aktualna_kolumna->prev = nowa_kolumna;
            poprzednia_kolumna = nowa_kolumna;
        }
        else if (aktualna_kolumna && aktualna_kolumna->index == glowa->index - 1)
            aktualna_kolumna->friends = aktualna_kolumna->friends + 1;
        else
            poprzednia_kolumna->friends = poprzednia_kolumna->friends + 1;

        // Aktualizowanie elementu na pozycji (index).
        if (!aktualna_kolumna) {
            aktualna_kolumna = new_node_kolumna(glowa->index, 0, 1, poprzednia_kolumna, NULL);
            poprzednia_kolumna->next = aktualna_kolumna;
        }
        else if (aktualna_kolumna->index == glowa->index)
            aktualna_kolumna->friends = aktualna_kolumna->friends + 1;
        else {
            node_kolumna* nowa_kolumna = new_node_kolumna(glowa->index, 0, 1, poprzednia_kolumna, aktualna_kolumna);
            poprzednia_kolumna->next = nowa_kolumna;
            aktualna_kolumna->prev = nowa_kolumna;
            poprzednia_kolumna = nowa_kolumna;
        }

        // Aktualizowanie elementu na pozycji (index + 1).
        if (aktualna_kolumna->index == glowa->index + 1)
            aktualna_kolumna->friends = aktualna_kolumna->friends + 1;
        else if (nastepna_kolumna && nastepna_kolumna->index == glowa->index + 1)
            nastepna_kolumna->friends = nastepna_kolumna->friends + 1;
        else if (aktualna_kolumna->index > glowa->index + 1) {
            node_kolumna* nowa_kolumna = new_node_kolumna(glowa->index + 1, 0, 1, poprzednia_kolumna, aktualna_kolumna);
            poprzednia_kolumna->next = nowa_kolumna;
            aktualna_kolumna->prev = nowa_kolumna;
            poprzednia_kolumna = nowa_kolumna;
        }
        else {
            node_kolumna* nowa_kolumna = new_node_kolumna(glowa->index + 1, 0, 1, aktualna_kolumna, nastepna_kolumna);
            aktualna_kolumna->next = nowa_kolumna;
            if (nastepna_kolumna)
                nastepna_kolumna->prev = nowa_kolumna;
            nastepna_kolumna = nowa_kolumna;
        }
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

        // Aktualizowanie elementu na pozycji (index - 1).
        if (poprzednia_kolumna == NULL) {
            poprzednia_kolumna = new_node_kolumna(aktualna_kolumna->index - 1, false, 1, NULL, aktualna_kolumna);
            head_copy = poprzednia_kolumna;
            aktualna_kolumna->prev = poprzednia_kolumna;
        }
        else if (poprzednia_kolumna->index != aktualna_kolumna->index - 1) {
            node_kolumna* nowa_kolumna = new_node_kolumna(aktualna_kolumna->index - 1, false, 1, poprzednia_kolumna, aktualna_kolumna);
            poprzednia_kolumna->next = nowa_kolumna;
            aktualna_kolumna->prev = nowa_kolumna;
            poprzednia_kolumna = nowa_kolumna;
        }
        else
            poprzednia_kolumna->friends = poprzednia_kolumna->friends + 1;

        // Aktualizowanie elementu na pozycji (index + 1).
        if (!nastepna_kolumna) {
            nastepna_kolumna = new_node_kolumna(aktualna_kolumna->index + 1, false, 1, aktualna_kolumna, NULL);
            aktualna_kolumna->next = nastepna_kolumna;
            skok = true;
        }
        else if (nastepna_kolumna->index != aktualna_kolumna->index + 1) {
            node_kolumna* nowa_kolumna = new_node_kolumna(aktualna_kolumna->index + 1, false, 1, aktualna_kolumna, nastepna_kolumna);
            aktualna_kolumna->next = nowa_kolumna;
            nastepna_kolumna->prev = nowa_kolumna;
            nastepna_kolumna = nowa_kolumna;
            skok = true;
        }
        else
            nastepna_kolumna->friends = nastepna_kolumna->friends + 1;
            
        if (skok)
            head = head->next;
        head = head-> next;
    }
    (*akutalizowany_wiersz)->kolumna = head_copy;
}

node_wiersz* rozszerzWiersze(node_wiersz* head) {
    node_wiersz* head_copy = head;
    while (head) {
        node_wiersz* poprzedni_wiersz = head->prev;
        node_wiersz* aktualny_wiersz = head;
        node_wiersz* nastepny_wiersz = head->next;
        bool skok = false;

        if (!poprzedni_wiersz) {
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

        if (!nastepny_wiersz) {
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

        rozszerzKolumnyPoziom(&aktualny_wiersz);
        rozszerzKolumnyPion(&aktualny_wiersz, &poprzedni_wiersz);
        rozszerzKolumnyPion(&aktualny_wiersz, &nastepny_wiersz);

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
            if (kolumna->index == INT_MIN || kolumna->index == INT_MAX)
                kolumna->alive = false;
            else if (kolumna->alive && kolumna->friends != 2 && kolumna->friends != 3)
                kolumna->alive = false;
            else if (!kolumna->alive && kolumna->friends == 3)
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
                delete_node_kolumna(usuwana_kolumna);
            }
            else {
                if (nastepna_kolumna)
                    nastepna_kolumna->prev = poprzednia_kolumna;
                poprzednia_kolumna->next = nastepna_kolumna;
                delete_node_kolumna(usuwana_kolumna);
            }
            kolumna = nastepna_kolumna;
        }
        head->kolumna = kolumna_copy;

        if (head->kolumna && head->index != INT_MIN && head->index != INT_MAX)
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
            delete_node_wiersz(usuwany_wiersz);
        }
    }
    return head_copy;
}

node_wiersz* nextGeneration(node_wiersz** head) {
    *head = rozszerzWiersze(*head);
    *head = aktualizujZywotnosc(*head);
    *head = skurczWiersze(*head);
    return *head;
}

bool wczytajPolecenie(node_wiersz** head, int* wiersz_startowy, int* kolumna_startowa) {
    narysujGeneracje(*head, *wiersz_startowy, *kolumna_startowa);
    char tmp = (char) getchar();
    if (tmp == '.')
        return false;

    if (tmp == '\n')
        *head = nextGeneration(head);

    if (isdigit(tmp) || tmp == '-') {
        ungetc(tmp, stdin);
        int numer1, numer2;
        scanf("%d", &numer1);
        tmp = (char) getchar();

        if (tmp == ' ') {
            scanf("%d", &numer2);
            *wiersz_startowy = numer1;
            *kolumna_startowa = numer2;
            tmp = (char) getchar();
        }
        else if (numer1 == 0)
            zrzutStanuGeneracji(*head);
        else
            while (numer1--)
                *head = nextGeneration(head);
    }
    return true;
}

int main() {
    int wiersz_startowy = 1;
    int kolumna_startowa = 1;
    node_wiersz* head = wczytajAktualnyStan();

    while (wczytajPolecenie(&head, &wiersz_startowy, &kolumna_startowa))
        continue;
    
    delete(head);
}
