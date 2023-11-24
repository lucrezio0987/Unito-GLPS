#include "Interfaccia.h"
#include <stdio.h>
#include <time.h>

#define MAX_RECORDS 30
#define MAX_STRING 30
#define TEST(text) printf("\033[4;36m%s\033[0;31m\n", text);

//--------- PROTOTIPI ---------//

void new_skiplist(struct SkipList** list, size_t max_height, int (*compar)(const void*, const void*));
void clear_skiplist(struct SkipList** list);
void clear_skiplist_ric(Node* Attuale);

Node* create_node(size_t size, void* item);
void insert_skiplist(struct SkipList* list, void* item);

void LoadData(struct SkipList* list, const char* file);
void LoadData_no_print(struct SkipList* list, const char* file);

const void* search_skiplist(struct SkipList* list, void* item);
void find_errors(const char* dictfile, const char* textfile, size_t max_height);

size_t random_level(size_t max);
int CompareString(char* i, char* j);
int list_is_empty(struct SkipList* list);
void print_list(struct SkipList* list);

//--------- STRUTTURE ---------//

typedef enum { FALSE,
    TRUE };

struct Node {
    struct Node** next;
    size_t size;
    void* item;
};

struct SkipList {
    struct Node** next;
    size_t max_level;
    size_t max_height;
    int (*compare)(const void*, const void*);
};

//------ IMPLEMENTAZIONI ------//

size_t random_level(size_t max)
{
    size_t liv;
    double randVal;

    for (liv = 1, randVal = (double)random() / RAND_MAX;
         randVal < 0.5 && liv < max;
         ++liv, randVal = (double)random() / RAND_MAX)
        // printf("  > liv: %d(/%d) - rand[%.2f]\n", liv, max, randVal)
        ;

    return liv;
}

Node* create_node(size_t size, void* item)
{
    Node* N_New = (Node*)malloc(sizeof(Node));
    N_New->size = size;
    N_New->next = (struct Node**)malloc(sizeof(struct Node*) * size);

    for (int i = 0; i < size; ++i)
        N_New->next[i] = NULL;

    if (item != NULL) {
        N_New->item = (char*)malloc(sizeof(char) * strlen((char*)item));
        strcpy(N_New->item, (char*)item);
    } else
        N_New->item = NULL;

    return N_New;
}

void new_skiplist(struct SkipList** list, size_t max_height, int (*compar)(const void*, const void*))
{
    *list = (struct SkipList*)malloc(sizeof(struct SkipList));
    (*list)->next = (struct Node**)malloc(sizeof(struct Node*) * max_height);
    (*list)->max_level = 1;
    (*list)->max_height = max_height;
    (*list)->compare = compar;

    for (int i = 0; i < max_height; ++i)
        (*list)->next[i] = NULL;
}

void clear_skiplist_ric(Node* Attuale)
{
    if (Attuale->next == NULL)
        return;
    clear_skiplist_ric(Attuale->next[0]);
    for (int i = 0; i < Attuale->size; ++i)
        free(Attuale->next[i]);
}

void clear_skiplist(struct SkipList** list)
{
    Node* Attuale = (*list)->next[0];

    clear_skiplist_ric(Attuale);

    free((*list)->next);
    free((*list)->next);
    free(*list);
}

int CompareString(char* i, char* j)
{
    int res = strcmp(i, j);
    if (res < 0)
        return -1;
    else if (res == 0)
        return 0;
    else
        return 1;
    return 0;
}

int list_is_empty(struct SkipList* list)
{
    return list->next[0] == NULL;
}

const void* search_skiplist(struct SkipList* list, void* item)
{
    if (list_is_empty(list) == TRUE)
        return NULL;

    if (list->compare(item, list->next) == 0)
        return list->next;

    struct Node* Attuale = list->next;

    for (int i = list->max_level - 1; i >= 0; --i)
        if (i < Attuale->size)
            while (Attuale->next[i] != NULL && list->compare(Attuale->next[i]->item, item) < 0)
                Attuale = Attuale->next[i];

    if ((Attuale = Attuale->next[0]) == NULL)
        return NULL;

    if (list->compare(Attuale->item, item) == 0)
        return Attuale->item;
    else
        return NULL;
}

void insert_skiplist(struct SkipList* list, void* item)
{
    Node* New = create_node(random_level((int) list->max_height), item);

    if (New->size > list->max_level)
        list->max_level = New->size;

    Node** next = list->next;

    for (int i = (int) list->max_level - 1; i >= 0; --i) {

        if (next == NULL || next[i] == NULL || list->compare(item, next[i]->item) < 0) {
            if (i < New->size) {
                New->next[i] = next[i];
                next[i] = New;
            }
        } else
            next = next[i++]->next;
    }
}

void print_list(struct SkipList* list)
{
    struct Node* Attuale = list->next;

    printf("\033[0;30m   PRINT LIST: \033[0;31m\n");
    printf("\033[0;30m   | %s\033[0;31m", Attuale->item);

    while (Attuale->next[0] != NULL) {
        printf(" \033[0;30m%s \033[0;31m", Attuale->next[0]->item);
        Attuale = Attuale->next[0];
    }

    printf("\n");
}

void LoadData(struct SkipList* list, const char* file)
{
    FILE* fp = fopen(file, "r");
    char string[MAX_STRING];
    int i = 0;

    while (fscanf(fp, "%s\n", string) != EOF) {
        printf("\033[0;33m  [\033[0;30m%6d\033[0;33m] Inserito: \033[1;33m%s \033[0;31m\n", i++, string);
        insert_skiplist(list, string);
    }
    // print_list(list);
    fclose(fp);

    struct timespec ts;
    clock_gettime(CLOCK_MONOTONIC, &ts);
    srand((time_t)ts.tv_nsec);

    return;
}

void LoadData_no_print(struct SkipList* list, const char* file)
{
    FILE* fp = fopen(file, "r");
    char string[MAX_STRING];
    int i = 0;

    while (fscanf(fp, "%s\n", string) != EOF)
        insert_skiplist(list, string);

    fclose(fp);
    return;
}

void init()
{
    struct timespec ts;
    clock_gettime(CLOCK_MONOTONIC, &ts);
    srand((time_t)ts.tv_nsec);

    setbuf(stdout, NULL);
}

void find_errors(const char* dictfile, const char* textfile, size_t max_height)
{
    init();
    SkipList* Dict;

    new_skiplist(&Dict, max_height, CompareString);
    printf("\033[0;32m[ Creato ]\033[0;31m\n");

    LoadData_no_print(Dict, dictfile);
    printf("\033[0;32m[ Caricato ]\033[0;31m\n");

    int i = 0;
    char string[MAX_STRING];

    FILE* fp_textfile;

    if ((fp_textfile = fopen(textfile, "r")) == 0) {
        printf("Errore: Apertura file (%s)\n", textfile);
        return 0;
    }
    printf("\033[0;32m[ File Aperto ]\033[0;31m\n");

    while (fscanf(fp_textfile, "%s", string) == 1 && ++i != MAX_RECORDS) {
        printf("\033[0;34m%20s:\033[0;31m", string);
        if (search_skiplist(Dict, string) == NULL)
            printf("\t\033[1;34m Non Presente\033[0;31m\n");
        else
            printf("\t\033[1;34m Presente\033[0;31m\n");
    }

    fclose(fp_textfile);

    printf("\033[0;32m[ File Chiuso ]\033[0;31m\n");

   // clear_skiplist(Dict);
    printf("\033[0;32m[ Deallocazione ]\033[0;31m\n");

    return;
}
