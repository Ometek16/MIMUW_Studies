/*
    Kuba Ornatek
*/

#include <stdio.h>
#include <stdlib.h>
#include <errno.h>
#include <stdbool.h>
#include <string.h>
#include "seq.h"

typedef struct trieNode trieNode;
typedef struct classNode classNode;
static trieNode* newTrieNode();
static classNode* newClassNode(const char* name);
static void freeTrieNode(trieNode* tNode);
static void freeClassNode(classNode* cNode);
static void addTrieNodeToClass(classNode* cNode, trieNode* tNode);
static void deleteTrieNodeFromItsClass(trieNode* tNode);
static void mergeClassNodes(classNode* cNode1, classNode* cNode2);
static char* deepCopy(const char* s);
static bool checkIfValid(const char* s);
static trieNode* findCurrNode(seq_t* p, const char* s);

struct trieNode {
    trieNode* nextLetter[3];
    classNode* abstractClass;
    trieNode* next;
    trieNode* prev;
};

struct classNode {
    char* name;
    size_t size;
    trieNode* dummyStart;
    trieNode* dummyEnd;
};

struct seq {
    trieNode* trieRoot;
};

//& Tworzenie i usuwanie structów pomocniczych

static trieNode* newTrieNode() {
    trieNode* newNode = (trieNode*) calloc(sizeof(trieNode), 1);

    //! ENOMEM
    if (newNode == NULL) {
        errno = ENOMEM;
        return NULL;
    }

    for (int i = 0; i < 3; i++)
        newNode->nextLetter[i] = NULL;

    newNode->abstractClass = NULL;
    newNode->prev = NULL;
    newNode->next = NULL;

    return newNode;
}

static classNode* newClassNode(const char* name) {
    classNode* newNode = (classNode*) calloc(sizeof(classNode), 1);

    //! ENOMEM
    if (newNode == NULL) {
        errno = ENOMEM;
        return NULL;
    }

    char* nameCpy = deepCopy(name);

    //! ENOMEM
    if (nameCpy == NULL && name != NULL) {
        free(newNode);
        errno = ENOMEM;
        return NULL;
    }

    newNode->name = nameCpy;

    newNode->size = 0;

    newNode->dummyStart = newTrieNode();

    //! ENOMEM
    if (newNode->dummyStart == NULL) {
        free(nameCpy);
        free(newNode);
        errno = ENOMEM;
        return NULL;
    }

    newNode->dummyEnd = newTrieNode();

    //! ENOMEM
    if (newNode->dummyEnd == NULL) {
        free(newNode->dummyStart);
        free(nameCpy);
        free(newNode);
        errno = ENOMEM;
        return NULL;
    }

    newNode->dummyStart->next = newNode->dummyEnd;
    newNode->dummyEnd->prev = newNode->dummyStart;

    return newNode;
}

static void freeTrieNode(trieNode* tNode) {
    if (tNode == NULL)
        return;

    if (tNode->abstractClass != NULL) {
        classNode* cNode = tNode->abstractClass;
        deleteTrieNodeFromItsClass(tNode);
        if (cNode->size == 0)
            freeClassNode(cNode);
    }

    for (int i = 0; i < 3; i++)
        freeTrieNode(tNode->nextLetter[i]);

    free(tNode);
}

static void freeClassNode(classNode* cNode) {
    free(cNode->name);
    freeTrieNode(cNode->dummyStart);
    freeTrieNode(cNode->dummyEnd);
    free(cNode);
}

//& Podstawowe operacje na nowych structach

static void addTrieNodeToClass(classNode* cNode, trieNode* tNode) {
    cNode->size++;
    tNode->abstractClass = cNode;

    tNode->prev = cNode->dummyEnd->prev;
    tNode->next = cNode->dummyEnd;

    cNode->dummyEnd->prev->next = tNode;
    cNode->dummyEnd->prev = tNode;
}

static void deleteTrieNodeFromItsClass(trieNode* tNode) {
    tNode->prev->next = tNode->next;
    tNode->next->prev = tNode->prev;

    tNode->next = NULL;
    tNode->prev = NULL;

    tNode->abstractClass->size--;
    tNode->abstractClass = NULL;
}

static void mergeClassNodes(classNode* cNode1, classNode* cNode2) {
    bool swap = false;
    if (cNode2->size > cNode1->size) {
        classNode* tmp = cNode1;
        cNode1 = cNode2;
        cNode2 = tmp;
        swap = true;
    }

    if (cNode1->name == NULL && cNode2->name != NULL) {
        cNode1->name = cNode2->name;
        cNode2->name = NULL;
    }
    else if (cNode1->name != NULL && cNode2->name != NULL && strcmp(cNode1->name, cNode2->name) != 0) {
        size_t newSize = strlen(cNode1->name) + strlen(cNode2->name) + 1;
        char* newName = (char*) calloc(sizeof(char), newSize);

        //! ENOMEM
        if (newName == NULL) {
            errno = ENOMEM;
            return;
        }

        if (!swap)
            strcat(newName, cNode1->name);
        strcat(newName, cNode2->name);
        if (swap)
            strcat(newName, cNode1->name);

        free(cNode1->name);
        cNode1->name = newName;
    }

    while (cNode2->dummyStart->next != cNode2->dummyEnd) {
        trieNode* akt = cNode2->dummyStart->next;
        deleteTrieNodeFromItsClass(akt);
        addTrieNodeToClass(cNode1, akt);
    }

    freeClassNode(cNode2);
}

//& Przydatne funkcje

static char* deepCopy(const char* s) {
    if (s == NULL)
        return NULL;

    char* copyS = (char*) calloc(sizeof(char), strlen(s) + 1);

    //! ENOMEM
    if (copyS == NULL) {
        errno = ENOMEM;
        return NULL;
    }

    strcpy(copyS, s);
    return copyS;
}

static bool checkIfValid(const char* s) {
    if (s == NULL)
        return false;

    int lenS = (int) strlen(s);

    for (int i = 0; i < lenS; i++)
        if (s[i] != '0' && s[i] != '1' && s[i] != '2')
            return false;

    return (lenS != 0);
}

static trieNode* findCurrNode(seq_t* p, const char* s) {
    trieNode* currNode = p->trieRoot;

    int lenS = (int) strlen(s);

    for (int i = 0; i < lenS; i++) {
        if (currNode->nextLetter[s[i] - '0'] == NULL)
            return NULL;
        currNode = currNode->nextLetter[s[i] - '0'];
    }

    return currNode;
}

//~ Do implementacji

seq_t* seq_new(void) {
    seq_t* p = (seq_t*) calloc(sizeof(seq_t), 1);

    //! ENOMEM 
    if (p == NULL) {
        errno = ENOMEM;
        return NULL;
    }

    p->trieRoot = newTrieNode();

    //! ENOMEM
    if (p->trieRoot == NULL) {
        free(p);
        errno = ENOMEM;
        return NULL;
    }

    return p;
}

void seq_delete(seq_t* p) {
    if (p == NULL)
        return;

    freeTrieNode(p->trieRoot);
    free(p);
}

int seq_add(seq_t* p, char const* s) {
    //! EINVAL
    if (!checkIfValid(s) || p == NULL) {
        errno = EINVAL;
        return -1;
    }

    trieNode* editNode = NULL;
    int letter = -1;

    trieNode* currNode = p->trieRoot;
    int lenS = (int) strlen(s);

    for (int i = 0; i < lenS; i++) {
        if (currNode->nextLetter[s[i] - '0'] == NULL) {
            if (editNode == NULL) {
                editNode = currNode;
                letter = s[i] - '0';
            }

            currNode->nextLetter[s[i] - '0'] = newTrieNode();

            //! ENOMEM
            if (currNode->nextLetter[s[i] - '0'] == NULL) {
                freeTrieNode(editNode->nextLetter[letter]);
                editNode->nextLetter[letter] = NULL;
                errno = ENOMEM;
                return -1;
            }
        }
        currNode = currNode->nextLetter[s[i] - '0'];
    }

    return (letter != -1);
}

int seq_remove(seq_t* p, char const* s) {
    //! EINVAL
    if (!checkIfValid(s) || p == NULL) {
        errno = EINVAL;
        return -1;
    }

    trieNode* currNode = p->trieRoot;
    trieNode* prevNode = NULL;
    int lenS = (int) strlen(s);

    for (int i = 0; i < lenS; i++) {
        if (currNode->nextLetter[s[i] - '0'] == NULL)
            return 0;
        prevNode = currNode;
        currNode = currNode->nextLetter[s[i] - '0'];
    }

    freeTrieNode(currNode);
    prevNode->nextLetter[s[lenS - 1] - '0'] = NULL;

    return 1;
}

int seq_valid(seq_t* p, char const* s) {
    //! EINVAL
    if (!checkIfValid(s) || p == NULL) {
        errno = EINVAL;
        return -1;
    }

    trieNode* currNode = findCurrNode(p, s);

    if (currNode == NULL)
        return 0;

    return 1;
}

int seq_set_name(seq_t* p, char const* s, char const* n) {
    //! EINVAL
    if (!checkIfValid(s) || p == NULL || n == NULL || strlen(n) == 0) {
        errno = EINVAL;
        return -1;
    }

    trieNode* currNode = findCurrNode(p, s);

    if (currNode == NULL)
        return 0;

    if (currNode->abstractClass == NULL) {
        classNode* newClass = newClassNode(n);

        //! ENOMEM
        if (newClass == NULL) {
            errno = ENOMEM;
            return -1;
        }

        addTrieNodeToClass(newClass, currNode);
    }
    else if (currNode->abstractClass->name != NULL && strcmp(currNode->abstractClass->name, n) == 0) {
        return 0;
    }
    else {
        free(currNode->abstractClass->name);
        currNode->abstractClass->name = deepCopy(n);

        //! ENOMEM
        if (currNode->abstractClass->name == NULL) {
            errno = ENOMEM;
            return -1;
        }
    }

    return 1;
}

char const* seq_get_name(seq_t* p, char const* s) {
    //! EINVAL
    if (!checkIfValid(s) || p == NULL) {
        errno = EINVAL;
        return NULL;
    }

    trieNode* currNode = findCurrNode(p, s);

    //! 0
    if (currNode == NULL || currNode->abstractClass == NULL || currNode->abstractClass->name == NULL) {
        errno = 0;
        return NULL;
    }

    return currNode->abstractClass->name;
}

int seq_equiv(seq_t* p, char const* s1, char const* s2) {
    //! EINVAL
    if (!checkIfValid(s1) || !checkIfValid(s2) || p == NULL) {
        errno = EINVAL;
        return -1;
    }

    trieNode* currNode1 = findCurrNode(p, s1);
    trieNode* currNode2 = findCurrNode(p, s2);

    if (currNode1 == currNode2 || currNode1 == NULL || currNode2 == NULL) {
        return 0;
    }
    else if (currNode1->abstractClass == NULL && currNode2->abstractClass != NULL) {
        addTrieNodeToClass(currNode2->abstractClass, currNode1);
    }
    else if (currNode1->abstractClass != NULL && currNode2->abstractClass == NULL) {
        addTrieNodeToClass(currNode1->abstractClass, currNode2);
    }
    else if (currNode1->abstractClass == NULL && currNode2->abstractClass == NULL) {
        classNode* newClass = newClassNode(NULL);

        //! ENOMEM
        if (newClass == NULL) {
            errno = ENOMEM;
            return -1;
        }

        addTrieNodeToClass(newClass, currNode1);
        addTrieNodeToClass(newClass, currNode2);
    }
    else if (currNode1->abstractClass == currNode2->abstractClass) {
        return 0;
    }
    else {
        mergeClassNodes(currNode1->abstractClass, currNode2->abstractClass);

        //! ENOMEM
        if (errno == ENOMEM) {
            errno = ENOMEM;
            return -1;
        }
    }

    return 1;
}
