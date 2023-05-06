#include "Interfaccia.h"

//--------- PROTOTIPI ---------//

void new_skiplist(struct SkipList **list, size_t max_height, int (*compar)(const void *, const void*));
void clear_skiplist(struct SkipList **list);
void insert_skiplist(struct SkipList *list, void *item);
const void* search_skiplist(struct SkipList *list, void *item);
void find_errors(const char *dictfile, const char *textfile, size_t max_height);

//--------- STRUTTURE ---------//

struct Node {
  struct Node **next;
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
    Node *next = nodo_attuale->next;
    free(nodo_attuale->next);
    free(nodo_attuale);
    nodo_attuale = next;
  }

  free((*list)->head->next);
  free((*list)->head);
  free(*list);
}

void insert_skiplist(struct SkipList *list, void *item) {
    /* Creazione del nuovo nodo */
    struct Node *new_node = (struct Node*)malloc(sizeof(struct Node));
    new_node->next = (struct Node**)malloc(sizeof(struct Node*) * list->max_height);
    new_node->size = 1;
    new_node->item = (char*)item;

    /* Inserimento del nuovo nodo in lista */
    struct Node *current_node = list->head;
    int level = list->max_level - 1;

    while (level >= 0) {
        while (current_node->next[level] && list->compare(current_node->next[level]->item, item) < 0) {
            current_node = current_node->next[level];
        }
        if (level < new_node->size) {
            new_node->next[level] = current_node->next[level];
            current_node->next[level] = new_node;
        }
        level--;
    }
}
const void* search_skiplist(struct SkipList *list, void *item) {
    struct Node *current_node = list->head;
    int level = list->max_level - 1;

    while (level >= 0) {
        while (current_node->next[level] && list->compare(current_node->next[level]->item, item) < 0) {
            current_node = current_node->next[level];
        }
        if (current_node->next[level] && list->compare(current_node->next[level]->item, item) == 0) {
            return current_node->next[level]->item;
        }
        level--;
    }
    return NULL;
}

void LoadData(struct SkipList *list, const char *file){
  FILE *fp = fopen(file, "r");
  char temp[100];

  while(fscanf(fp, "%[^ ]", temp) != 0){
    insert_skiplist(list, temp);
  }

  fclose(fp);

  return;
}

void find_errors(const char *dictfile, const char *textfile, size_t max_height){
  SkipList *Dict;
  new_skiplist(&Dict, max_height, &strcmp);
  LoadData(Dict, dictfile);
  
  int i=0;
  char temp[100];

  FILE *fp_textfile;

  if((fp_textfile = fopen(textfile, "r")) == 0) { printf("Errore: Apertura file (%s)\n", textfile); return 0; }

  while(fscanf(fp_textfile, "%s", temp) == 1 && ++i !=10)
    if(search_skiplist(Dict, temp) == NULL)
      printf("%s\n", temp);
  
  fclose(fp_textfile);

  clear_skiplist(Dict);

  return;
}