#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#ifndef ORDERED_ARRAY_H_laokjsdnbudjllvfidkfmqm
#define ORDERED_ARRAY_H_laokjsdnbudjllvfidkfmqm


//--------- STRUTTURE ---------//

typedef struct _Node Node;
typedef struct _SkipList SkipList;


//--------- PROTOTIPI ---------//

void new_skiplist(struct SkipList **list, size_t max_height, int (*compar)(const void *, const void*));
void clear_skiplist(struct SkipList **list);
void insert_skiplist(struct SkipList *list, void *item);

const void* search_skiplist(struct SkipList *list, void *item);

void find_errors(const char *dictfile, const char *textfile, size_t max_height);

#endif /* ORDERED_ARRAY_H_laokjsdnbudjllvfidkfmqm */
