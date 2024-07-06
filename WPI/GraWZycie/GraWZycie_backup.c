#include <stdio.h>
#include <ctype.h>
#include <stdbool.h>
#include <stdlib.h>

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

void pokaSowe(node_wiersz* head) {
    while (head) {
        printf("WIERSZ: %d\n", head->index);
        node_kolumna* kol = head->kolumna;
        while (kol) {
            printf("\tKOLUMNA: %d -> %d | %d\n", kol->index, kol->alive, kol->friends);
            kol = kol->next;
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

void rozszerzKolumnyPion(node_wiersz** aktualny_wiersz, node_wiersz** pomocniczy_wiersz) {
    node_kolumna* head_pom_poprzedni = NULL;
    node_kolumna* head_pom = (*pomocniczy_wiersz)->kolumna;
    node_kolumna* head = (*aktualny_wiersz)->kolumna;
    
    while(head) {
        //printf("\t SUKA\n");
        while (head && !head->alive) 
            head = head->next;
        if (head) {
            //printf("XD: %d\n", head->index);
            
            while(head_pom && head_pom->index < head->index - 1) {
                head_pom_poprzedni = head_pom;
                head_pom = head_pom->next;
            }
            // Aktualizowanie elementu (index - 1)
            if (!head_pom_poprzedni && (!head_pom || head_pom->index != head->index - 1)) {
                node_kolumna* nowa_kolumna = new_node_kolumna(head->index - 1, false, 1, NULL, head_pom);
                (*pomocniczy_wiersz)->kolumna = nowa_kolumna;
                head_pom = nowa_kolumna;
            }
            else if (!head_pom || head_pom->index != head->index - 1) {
                node_kolumna* nowa_kolumna = new_node_kolumna(head->index - 1, false, 1, head_pom_poprzedni, head_pom);
                head_pom_poprzedni->next = nowa_kolumna;
                head_pom = nowa_kolumna;    
            }
            else {
                //printf("SUS...\n");
                head_pom->friends = head_pom->friends + 1;
            }

            // Aktualizowanie elementy (index) 
            head_pom_poprzedni = head_pom;
            head_pom = head_pom -> next;
            if (!head_pom || head_pom->index != head->index) {
                node_kolumna* nowa_kolumna = new_node_kolumna(head->index, false, 1, head_pom_poprzedni, head_pom);
                head_pom_poprzedni->next = nowa_kolumna;
                head_pom = nowa_kolumna;    
            }
            else {
                //printf("SUS...\n");
                head_pom->friends = head_pom->friends + 1;
            }

            // Aktualizowanie elementy (index + 1)
            node_kolumna* head_pom_copy = head_pom;
            head_pom_poprzedni = head_pom;
            head_pom = head_pom -> next;
            if (!head_pom || head_pom->index != head->index + 1) {
                node_kolumna* nowa_kolumna = new_node_kolumna(head->index + 1, false, 1, head_pom_poprzedni, head_pom);
                head_pom_poprzedni->next = nowa_kolumna;
                head_pom = nowa_kolumna;    
            }
            else {
                //printf("SUS...\n");
                head_pom->friends = head_pom->friends + 1;
            }
            
            head_pom = head_pom_copy;
            head = head->next;
        }
    }
}

void rozszerzKolumnyPoziom(node_wiersz** aktualny_wiersz) {
    node_kolumna* head = (*aktualny_wiersz)->kolumna;
    //printf("YOLO: %d\n", (head->index));

    while (head) {
        node_kolumna* poprzednia_kolumna = head->prev;
        node_kolumna* aktualna_kolumna = head;
        node_kolumna* nastepna_kolumna = head->next;
        bool skok = false;

        // Aktualizowanie elementu (index - 1)
        if (poprzednia_kolumna == NULL) {
            poprzednia_kolumna = new_node_kolumna(aktualna_kolumna->index - 1, false, 1, NULL, aktualna_kolumna);
            (*aktualny_wiersz)->kolumna = poprzednia_kolumna;
            aktualna_kolumna->prev = poprzednia_kolumna;
            //printf("KOL SKRR");
        }
        else if (poprzednia_kolumna->index != aktualna_kolumna->index - 1) {
            node_kolumna* nowa_kolumna = new_node_kolumna(aktualna_kolumna->index - 1, false, 1, poprzednia_kolumna, aktualna_kolumna);
            poprzednia_kolumna->next = nowa_kolumna;
            aktualna_kolumna->prev = nowa_kolumna;
            poprzednia_kolumna = nowa_kolumna;
        }
        else {
            poprzednia_kolumna->friends = poprzednia_kolumna->friends + 1;
        }

        // Aktualizowanie elementu (index + 1)
        if (nastepna_kolumna == NULL) {
            nastepna_kolumna = new_node_kolumna(aktualna_kolumna->index + 1, false, 1, aktualna_kolumna, NULL);
            aktualna_kolumna->next = nastepna_kolumna;
            skok = true;
            //printf("KOL YEET\n");
        }
        else if (nastepna_kolumna->index != aktualna_kolumna->index + 1) {
            node_kolumna* nowa_kolumna = new_node_kolumna(aktualna_kolumna->index + 1, false, 1, aktualna_kolumna, nastepna_kolumna);
            aktualna_kolumna->next = nowa_kolumna;
            nastepna_kolumna->prev = nowa_kolumna;
            nastepna_kolumna = nowa_kolumna;
            skok = true;
        }
        else {
            nastepna_kolumna->friends = nastepna_kolumna->friends + 1;
        }

        if (skok)
            head = head->next;
        head = head-> next;
    }
}


node_wiersz* rozszerzWiersze(node_wiersz* head) {
    node_wiersz* head_copy = head;
    while (head) {
        node_wiersz* poprzedni_wiersz = head->prev;
        node_wiersz* aktualny_wiersz = head;
        node_wiersz* nastepny_wiersz = head->next;
        bool skok = false;

        if (poprzedni_wiersz == NULL) {
            poprzedni_wiersz = new_node_wiersz(aktualny_wiersz->index - 1, NULL, aktualny_wiersz, NULL);
            head_copy = poprzedni_wiersz;
            aktualny_wiersz->prev = poprzedni_wiersz;
            //printf(">SKRRR<\n");
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
            //printf(">YEET<\n");
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
            //printf("%d -> ", kolumna->index);
            if (kolumna->alive) {
                kolumna = kolumna->next;
                continue;
            }
            node_kolumna* usuwana_kolumna = kolumna;
            node_kolumna* nastepna_kolumna = kolumna->next;
            node_kolumna* poprzednia_kolumna = kolumna->prev;
            //printf("JEBAC\n");
            
            if (!poprzednia_kolumna) {
                if (nastepna_kolumna)
                    nastepna_kolumna->prev = NULL;
                kolumna_copy = nastepna_kolumna;
                free(usuwana_kolumna);
            }
            else {
                if (nastepna_kolumna)
                    nastepna_kolumna->prev = poprzednia_kolumna;
                //printf("CYCKI\n");
                poprzednia_kolumna->next = nastepna_kolumna;
                //printf("DUZE\n");
                //kolumna = nastepna_kolumna;
                //printf("AHA\n");
                free(usuwana_kolumna);
            }
            //printf("SUKA");
            kolumna = kolumna->next;
        }
        //printf("\n");
        //printf("TU\n");
        head->kolumna = kolumna_copy;
        //printf("A CHUJ JEDNAK TU\n");

        //if (head->kolumna)
            head = head->next;
            /*
        else {
            node_wiersz* usuwany_wiersz = head;
            if (!head->prev)
                head_copy = head->next;
            else 
                head->prev->next = head->next;

            head->next->prev = head->prev;
            head = head->next;
            free(usuwany_wiersz);
        }
        */
        //printf("CO JEST KURRRRRRRWA");
    }
    //printf(">>>Gitara\n");
    return head_copy;
}

node_wiersz* nextGeneration(node_wiersz** head) {
    *head = rozszerzWiersze(*head);
    pokaSowe(*head);
    *head = aktualizujZywotnosc(*head);
    pokaSowe(*head);
    *head = skurczWiersze(*head);
    pokaSowe(*head);
    printf(">>NEXT GEN<<\n");
    return *head;
}

bool wczytajPolecenie(node_wiersz** head, int* wiersz_startowy, int* kolumna_startowa) {
    narysujGeneracje(*head, *wiersz_startowy, *kolumna_startowa);
    char tmp = getchar();
    if (tmp == '.')
        return false;

    if (tmp == '\n') {
        *head = nextGeneration(head);
        pokaSowe(*head); //! WYJEBAC
        //return false;   //! DEBUG
    }

    if (isdigit(tmp)){
        ungetc(tmp, stdin);
        int numer1, numer2;
        scanf("%d", &numer1);
        tmp = getchar();

        if (tmp == ' ') {
            scanf("%d", &numer2);
            *wiersz_startowy = numer1;
            *kolumna_startowa = numer2;
            tmp = getchar();
        }
        else if (numer1 == 0)
            zrzutStanuGeneracji(*head);
        else
            while (numer1--) {
                nextGeneration(head);
                //pokaSowe(*head);
            }
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