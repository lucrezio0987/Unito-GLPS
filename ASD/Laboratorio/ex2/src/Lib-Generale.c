#include "Interfaccia.h"

//--------- PROTOTIPI ---------//

void new_skiplist(struct SkipList **list, size_t max_height, int (*compar)(const void *, const void*));
void clear_skiplist(struct SkipList **list);
void insert_skiplist(struct SkipList *list, void *item);
const void* search_skiplist(struct SkipList *list, void *item);
void find_errors(const char *dictfile, const char *textfile, size_t max_height);

//--------- STRUTTURE ---------//

struct Node {
  struct _Node **next;
  size_t size;
  void *item;
};

struct SkipList {
  Node *head;
  size_t max_level;
  size_t max_height;
  int (*compare)(const void*, const void*);
};


//------ IMPLEMENTAZIONI ------//
void new_skiplist(struct SkipList **list, size_t max_height, int (*compar)(const void *, const void*)){
  
  *list = (SkipList*) malloc(sizeof(SkipList));
  
  // Lista
  (*list)->head = (Node*) malloc(sizeof(Node));
  (*list)->max_level = 0;
  (*list)->max_height = max_height;
  (*list)->compare = compar;
  
  // Primo Nodo (a NULL) 
  (*list)->head->next = (struct Node**) malloc(sizeof(struct Node*));
  (*list)->head->size = 0;
  (*list)->head->item = NULL;
  (*list)->head->next[0] = NULL;

}

void clear_skiplist(struct SkipList **list){
  Node *nodo_attuale = (*list)->head->next[0];

  while (nodo_attuale != NULL) {
        Node *next = node->next[0];
        free(nodo_attuale->next);
        free(nodo_attuale);
        nodo_attuale = next;
    }

  free((*list)->head->next);
  free((*list)->head);
  free(*list);
}

void insert_skiplist(struct SkipList *list, void *item){
  
  Node *nuovo_nodo= (Node*) malloc(sizeof(Node));

  nuovo_nodo->next = (struct Node**) malloc(sizeof(struct Node*));
  nuovo_nodo->size = 0;
  nuovo_nodo->item = item;
  nuovo_nodo->next[0] = NULL;
  
  Node *nodo_attuale = list->head;

  for (int i = list->max_level; i >= 0; i--) {
    while (nodo_attuale->next[i] != NULL && list->compare(nodo_attuale->next[i]->item, item) < 0)
      nodo_attuale = nodo_attuale->next[i];
    if (i <= list->max_height) {
      nuovo_nodo->next[i] = nodo_attuale->next[i];
      nodo_attuale->next[i] = nuovo_nodo;
      nodo_attuale->size++;
    } 
  }
  if (list->max_level < nuovo_nodo->size)
    list->max_level = nodo_attuale->size;

  return;
}

const void* search_skiplist(struct SkipList *list, void *item){
  Node *nodo_attuale = list->head;

  for (int i = list->max_level; i >= 0; i--) {
      while (nodo_attuale->next[i] != NULL && list->compare(nodo_attuale->next[i]->item, item) <= 0) {
          if (list->compare(nodo_attuale->next[i]->item, item) == 0)
              return nodo_attuale->next[i]->item;
          nodo_attuale = nodo_attuale->next[i];
      }
  }
  
  return NULL;
}

void LoadData(struct SkipList *list, const char *file){
  FILE *fp = fopen(file, "r");
  char temp[100];

  while(fscan(fp, "%[^ ]", temp) != 0){
    insert_skiplist(list, temp);
  }

  fclose(fp);

  return;
}

void find_errors(const char *dictfile, const char *textfile, size_t max_height){
  SkipList *Dict;
  new_skiplist(&Dict, max_height, &strcmp);
  LoadData(Dict, dictfile);
  
  char temp[100];

  FILE *fp_textfile = fopen(textfile, "r");

  while(fscan(fp_textfile, "%[^ ]", temp) != 0)
    if(search_skiplist(Dict, temp) == NULL)
      printf("%d\n", temp);
  
  fclose(fp_textfile);

  clear_skiplist(Dict);

  return;
}