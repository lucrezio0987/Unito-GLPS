#include "Interfaccia.h"

//--------- PROTOTIPI ---------//

void new_skiplist(struct SkipList **list, size_t max_height, int (*compar)(const void *, const void*));
void clear_skiplist(struct SkipList **list);
void insert_skiplist(struct SkipList *list, void *item);
const void* search_skiplist(struct SkipList *list, void *item);
void find_errors(const char *dictfile, const char *textfile, size_t max_height);

//--------- STRUTTURE ---------//

struct _Node {
  struct _Node **next;
  size_t size;
  char *item;
};

struct _SkipList {
  Node *head;
  size_t max_level;
  size_t max_height;
  int (*compare)(const void*, const void*);
};



//------ IMPLEMENTAZIONI ------//
void new_skiplist(struct SkipList **list, size_t max_height, int (*compar)(const void *, const void*)){
  *list = (SkipList*) malloc(sizeof(SkipList));
//  *list->head = (Node*) malloc(sizeof(Node));
  *list->max_level = 0;
  *list->max_height = max_height;
  *list->compare = compar;
  return;
}

void clear_skiplist(struct SkipList **list){
  free(list);
  return;
}

void insert_skiplist(struct SkipList *list, void *item){
  
  return;
}

const void* search_skiplist(struct SkipList *list, void *item){
  return;
}

void LoadData(struct SkipList **list, const char *file){
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
  new_skiplist(&Dict, max_height, ComparString);
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