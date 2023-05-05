#include <stdlib.h>
#include <stdio.h>
#include <string.h>

struct SkipList {
    struct Node *head;
    size_t max_level;
    size_t max_height;
    int (*compare)(const void*, const void*);
};

struct Node {
    struct Node **next;
    size_t size;
    void *item;
};

void new_skiplist(struct SkipList **list, size_t max_height, int (*compar)(const void *, const void*)) {
    // Allocazione della struct SkipList e del nodo di testa
    *list = (struct SkipList*) malloc(sizeof(struct SkipList));
    (*list)->head = (struct Node*) malloc(sizeof(struct Node));
    
    // Inizializzazione del nodo di testa
    (*list)->head->next = (struct Node**) malloc((max_height+1) * sizeof(struct Node*));
    (*list)->head->size = 0;
    (*list)->head->item = NULL;
    for (size_t i = 0; i <= max_height; i++)
        (*list)->head->next[i] = NULL;
        
    // Impostazione degli altri campi della struct SkipList
    (*list)->max_level = 0;
    (*list)->max_height = max_height;
    (*list)->compare = compar;
}

void clear_skiplist(struct SkipList **list) {
    // Deallocazione dei nodi, partendo dal secondo (il primo è il nodo di testa)
    struct Node *node = (*list)->head->next[0];
    while (node != NULL) {
        struct Node *next = node->next[0];
        free(node->next);
        free(node);
        node = next;
    }
    
    // Deallocazione del nodo di testa e della struct SkipList
    free((*list)->head->next);
    free((*list)->head);
    free(*list);
    *list = NULL;
}

// Funzione che restituisce un livello casuale (tra 0 e max_height) per il nuovo nodo
size_t random_level(size_t max_height) {
    size_t level = 0;
    while (level < max_height && rand() < RAND_MAX/2)
        level++;
    return level;
}

void insert_skiplist(struct SkipList *list, void *item) {
    // Allocazione del nuovo nodo e inizializzazione
    size_t level = random_level(list->max_height);
    struct Node *new_node = (struct Node*) malloc(sizeof(struct Node));
    new_node->next = (struct Node**) malloc((level+1) * sizeof(struct Node*));
    new_node->size = 1;
    new_node->item = item;
    for (size_t i = 0; i <= level; i++)
        new_node->next[i] = NULL;
        
    // Inserimento del nodo nella SkipList
    struct Node *node = list->head;
    for (int i = list->max_level; i >= 0; i--) {
        while (node->next[i] != NULL && list->compare(node->next[i]->item, item) < 0)
            node = node->next[i];
        if (i <= level) {
            new_node->next[i] = node->next[i];
            node->next[i] = new_node;
        }
    }
    if (level > list->max_level)
        list->max_level = level;
}

const void* search_skiplist(struct SkipList *list, void *item) {
    struct Node *node = list->head;
    for (int i = list->max_level; i >= 0; i--) {
        while (node->next[i] != NULL && list->compare(node->next[i]->item, item) <= 0) {
            if (list->compare(node->next[i]->item, item) == 0)
                return node->next[i]->item;
            node = node->next[i];
        }
    }
    return NULL;
}

int main(void) {
    struct SkipList *list;
    new_skiplist(&list, 4, &strcmp);
    insert_skiplist(list, "ciao");
    insert_skiplist(list, "come");
    insert_skiplist(list, "stai");
    insert_skiplist(list, "bulabula");

    char str[10] =  "barw";
    const void *res = search_skiplist(list, str);
    printf("%s: %s\n",str, (res != NULL) ? "<found>" : "<not found>");

    strcpy(str,"ciao");
    res = search_skiplist(list, str);
    printf("%s: %s\n",str, (res != NULL) ? "<found>" : "<not found>");
    
    strcpy(str,"stai");
    res = search_skiplist(list, str);
    printf("%s: %s\n",str, (res != NULL) ? "<found>" : "<not found>");
    
    clear_skiplist(&list);

    return 0;
}
