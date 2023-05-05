#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

#define MAX_WORD_LEN 50

struct Node {
    void *item;
    size_t size;
    struct Node **next;
};

struct SkipList {
    struct Node *head;
    size_t max_level;
    size_t max_height;
    int (*compare)(const void*, const void*);
};

void new_skiplist(struct SkipList **list, size_t max_height, int (*compar)(const void *, const void*)) {
    *list = malloc(sizeof(struct SkipList));
    (*list)->max_level = 0;
    (*list)->max_height = max_height;
    (*list)->compare = compar;
    struct Node *head = malloc(sizeof(struct Node) + sizeof(struct Node*) * max_height);
    head->item = NULL;
    head->size = 0;
    head->next = calloc(max_height, sizeof(struct Node*));
    (*list)->head = head;
}

void clear_skiplist(struct SkipList **list) {
    if (*list == NULL) {
        return;
    }
    struct Node *cur = (*list)->head;
    while (cur != NULL) {
        struct Node *temp = cur;
        cur = cur->next[0];
        free(temp->next);
        free(temp);
    }
    free(*list);
    *list = NULL;
}

int random_level(struct SkipList *list) {
    int level = 0;
    while (level < list->max_height - 1 && rand() < RAND_MAX/2) {
        level++;
    }
    return level;
}

void insert_skiplist(struct SkipList *list, void *item) {
    struct Node *update[list->max_height];
    struct Node *cur = list->head;
    for (int i = list->max_level; i >= 0; i--) {
        while (cur->next[i] != NULL && list->compare(cur->next[i]->item, item) < 0) {
            cur = cur->next[i];
        }
        update[i] = cur;
    }
    cur = cur->next[0];
    if (cur != NULL && list->compare(cur->item, item) == 0) {
        return;
    }
    int level = random_level(list);
    if (level > list->max_level) {
        for (int i = list->max_level + 1; i <= level; i++) {
            update[i] = list->head;
        }
        list->max_level = level;
    }
    struct Node *new_node = malloc(sizeof(struct Node) + sizeof(struct Node*) * (level + 1));
    new_node->item = item;
    new_node->size = 0;
    for (int i = 0; i <= level; i++) {
        new_node->next[i] = update[i]->next[i];
        update[i]->next[i] = new_node;
    }
}

const void* search_skiplist(struct SkipList *list, void *item) {
    struct Node *cur = list->head;
    for (int i = list->max_level; i >= 0; i--) {
        while (cur->next[i] != NULL && list->compare(cur->next[i]->item, item) < 0) {
            cur = cur->next[i];
        }
    }
    cur = cur->next[0];
    if (cur != NULL && list->compare(cur->item, item) == 0) {
        return cur->item;
    } else {
        return NULL;
    }
}

void find_errors(const char *dictfile, const char *textfile, size_t max_height) {
    struct SkipList *dict;
    new_skiplist(&dict, max_height, strcmp);
    FILE *fp = fopen(dictfile, "r");
    if (fp == NULL) {
        fprintf(stderr, "Error opening dictionary file.\n");
        clear_skiplist(&dict);
        return;
    }
    char word[MAX_WORD_LEN];
    while (fgets(word, MAX_WORD_LEN, fp) != NULL) {
        size_t len = strlen(word);
        if (len > 0 && word[len-1] == '\n') {
            word[len-1] = '\0';
        }
        insert_skiplist(dict, strdup(word));
    }
    fclose(fp);
    fp = fopen(textfile, "r");
    if (fp == NULL) {
        fprintf(stderr, "Error opening text file.\n");
        clear_skiplist(&dict);
        return;
    }
    char buf[MAX_WORD_LEN];
    int line = 1, pos = 1;
    while (fgets(buf, MAX_WORD_LEN, fp) != NULL) {
        size_t len = strlen(buf);
        if (len > 0 && buf[len-1] == '\n') {
            buf[len-1] = '\0';
        }
        char *word = strtok(buf, " \t");
        while (word != NULL) {
            if (search_skiplist(dict, word) == NULL) {
                printf("Line %d, pos %d: %s\n", line, pos, word);
            }
            word = strtok(NULL, " \t");
            pos += strlen(word) + 1;
        }
        line++;
        pos = 1;
    }
    fclose(fp);
    clear_skiplist(&dict);
}

int main() {
    srand(time(NULL));
    find_errors("dictionary.txt", "text.txt", 5);
    return 0;
} 
