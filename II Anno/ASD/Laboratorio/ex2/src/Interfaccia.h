#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#ifndef DIZIONARIO_H_laokjsdnbudjllvfidkfmqm
#define DIZIONARIO_H_laokjsdnbudjllvfidkfmqm


//--------- STRUTTURE ---------//

typedef struct Node Node;
typedef struct SkipList SkipList;

//--------- PROTOTIPI ---------//

void new_skiplist(struct SkipList **list, size_t max_height, int (*compar)(const void *, const void*));
void clear_skiplist(struct SkipList **list);
void clear_skiplist_ric(Node* Attuale);

Node* create_node(size_t size, void *item);
void insert_skiplist(struct SkipList *list, void *item);

void LoadData(struct SkipList *list, const char *file);
void LoadData_no_print(struct SkipList *list, const char *file);

const void* search_skiplist(struct SkipList *list, void *item);
void find_errors(const char *dictfile, const char *textfile, size_t max_height);

size_t random_level(size_t max);
int CompareString(char* i, char* j);
int list_is_empty(struct SkipList *list);
void print_list(struct SkipList *list);

//Test
void setUp(void);
void tearDown(void);

void test_create_skiplist_empty();
void test_create_node();
void test_insert_skiplist();
void test_load_skiplist();

void test_search_skiplist();

#endif /* DIZIONARIO_H_laokjsdnbudjllvfidkfmqm */
