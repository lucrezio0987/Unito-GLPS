#include "Interfaccia.h"
#include <time.h>

#define TEST(text) printf("\033[4;36m%s\033[0;31m\n", text);

//--------- PROTOTIPI ---------//

void new_skiplist(struct SkipList **list, size_t max_height, int (*compar)(const void *, const void*));
void clear_skiplist(struct SkipList **list);
void insert_skiplist(struct SkipList *list, void *item);
const void* search_skiplist(struct SkipList *list, void *item);
void find_errors(const char *dictfile, const char *textfile, size_t max_height);

//--------- STRUTTURE ---------//

struct Node { struct Node **next; size_t size;void *item;};struct SkipList { struct Node *head; size_t max_level; size_t max_height;int (*compare)(const void*, const void*);};


//------ IMPLEMENTAZIONI ------//


int random_level(int max){
  srand(time(NULL));
  return max;
}

Node* create_node(size_t size, void *item){
  srand(time(NULL));
  Node *N_New = (Node*) malloc(sizeof(Node));
        N_New->size = size;
        N_New->next = (struct Node**) malloc(sizeof(struct Node*) * size);
        
        if(item != NULL){
          N_New->item =(char *) malloc(sizeof(char)*strlen((char *)item));
          strcpy(N_New->item,(char *)item);
        } else {
          N_New->item = NULL;
        }
  return N_New;
}

void new_skiplist(struct SkipList **list, size_t max_height, int (*compar)(const void *, const void*)) {
    *list = (struct SkipList *) malloc(sizeof(struct SkipList));
    (*list)->head = create_node(0,NULL);;
    (*list)->max_level = 1;
    (*list)->max_height = max_height;
    (*list)->compare = compar;
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

int CompareString(char* i, char* j){
    int res = strcmp(i,j);
    if      (res  < 0)    return -1;
    else if (res == 0)    return 0;
    else                  return 1;
    return 0;
}

int list_is_empty(struct SkipList *list){
  if(list->head == NULL) return 1;
  else return 0;
}


/*
searchSkipList(list, item)
    x = list->head

    // loop invariant: x->item < item
    for i = list->max_level downto 1 do
        while x->next[i]->item < item do
            x = x->next[i]

    // x->item < item <= x->next[1]->item
    x = x->next[1]
    if x->item == item then
        return x->item
    else
        return failure
*/

const void* search_skiplist(struct SkipList *list, void *item){
  struct Node *Attuale = list->head;
  //! ERRORE
  for(int k = list->max_level - 1; k >= 0; --k) {
    while(list->compare(Attuale->next[k]->item, item) < 0)
      Attuale = Attuale->next[k]->item;
  }

  Attuale = Attuale->next[0];
  if( list->compare(Attuale->item, item) == 0)
    return Attuale->item;
  else 
    return NULL;


}

const void* search_skiplist_OLD(struct SkipList *list, void *item) {
  if(list_is_empty(list) == 1) return NULL;

  size_t h;
  
  /* cerca l'elemento */
  struct Node *p = list->head;
  for (h = list->max_level; h > 0; h--) {
      while (p->next[h-1] != NULL && list->compare(item, p->next[h-1]->item) > 0) {
          p = p->next[h-1];
      }
  }
  p = p->next[0];
  /* restituisce il risultato */
  if (p != NULL && list->compare(item, p->item) == 0) {
      return p->item;
  } else {
      return NULL;
  }

}
/*
insertSkipList(list, item)

    new = createNode(item, randomLevel())
    if new->size > list->max_level
        list->max_level = new->size

    x = list->head
    for k = list->max_level downto 1 do
        if (x->next[k] == NULL || item < x->next[k]->item)
            if k < new->size {
              new->next[k] = x->next[k]
              x->next[k] = new
            }
        else
            x = x->next[k]
            k++
*/
void insert_skiplist(struct SkipList *list, void *item) {
  Node *New_Node = create_node(1, item);
  Node *Attuale = list->head;

  if(New_Node->size > list->max_level)
    list->max_level = New_Node->size;

  for(int k = list->max_level - 1; k >= 0; --k) {
    if(Attuale->next[k] == NULL || list->compare(item, Attuale->next[k]->item) < 0) {
      if(k < New_Node->size){
        New_Node->next[k] = Attuale->next[k];
        Attuale->next[k] = New_Node;
      } else {
        Attuale = Attuale->next[k];
        ++k;
      } 
    }
  }

  //for (int i = list->max_level - 1; i >= 0; --i) {
  //  while (Attuale->next[i] != NULL && list->compare(Attuale->next[i]->item, item) < 0)
  //    Attuale = Attuale->next[i];
  //  if (i < size) {
  //    New_Node->next[i] = Attuale->next[i];
  //    Attuale->next[i] = New_Node;
  //  }
  //}
}

void insert_skiplist_OLD(struct SkipList *list, void *item) {
    size_t h, i;
    struct Node *p = list->head;
    struct Node **update = (struct Node **) calloc(1, sizeof(struct Node *));;

    /* crea il nuovo nodo */
    struct Node *new_node = create_node(2,item);

    /* ricerca il punto di inserimento */
    for (h = list->max_level; h > 0; h--) {
      while (p->size>h && p->next[h-1] != NULL && list->compare(item, p->next[h-1]->item) > 0) 
        p = p->next[h-1];
      update[h-1] = p;
    }
    p = p->next[h];
    
    /* controlla se l'elemento è già presente */
    if (p != NULL && list->compare(item, p->item) == 0) {
        return; // l'elemento esiste già, ignora l'inserimento
    }

    /* aggiorna i puntatori */
    if (new_node->size > list->max_level) {
        list->max_level = new_node->size;
    }
    for (i = 0; i < new_node->size; i++) {
        new_node->next[i] = update[i]->next[i];
        update[i]->next[i] = new_node;
    }
    free(update);
}



void print_list(struct SkipList *list) {
  struct Node *Attuale = list->head;
  struct Node *p = list->head;
  int i;
  int h;
  printf("\033[0;30m   PRINT LIST: \033[0;31m\n");

  printf("\033[0;30m   | N(\033[1;30m%s\033[0;30m):  \033[0;31m", p->item);
  for (h = list->max_level; h > 0; h--) {
    while (p->next[h-1] != NULL) {
        printf(" \033[0;30m%s \033[0;31m", p->next[h-1]->item);
        p = p->next[h-1];
    }
  }

  printf("\n");

}

void LoadData(struct SkipList *list, const char *file){
  FILE *fp = fopen(file, "r");
  char temp[100];

  while(fscanf(fp, "%s\n", temp) != EOF){
    printf("\033[0;33m  - Inserito: %s \033[0;31m\n", temp);
    insert_skiplist(list, temp);
    print_list(list);
    
  }

  fclose(fp);

  return;
}

void find_errors(const char *dictfile, const char *textfile, size_t max_height){
  SkipList *Dict;
  
  new_skiplist(&Dict, max_height, &strcmp);
  printf("\033[0;32m[ Creato ]\033[0;31m\n");

  LoadData(Dict, dictfile);
  printf("\033[0;32m[ Caricato ]\033[0;31m\n");
  
  int i=0;
  char temp[100];

  FILE *fp_textfile;

  if((fp_textfile = fopen(textfile, "r")) == 0) { printf("Errore: Apertura file (%s)\n", textfile); return 0; }
  printf("\033[0;32m[ File Aperto ]\033[0;31m\n");

  while(fscanf(fp_textfile, "%s", temp) == 1 && ++i !=10)
    if(search_skiplist(Dict, temp) == NULL)
      printf("\033[0;34m\t%s:\t\033[1;34m Non Presente\n", temp);
    else 
      printf("\033[0;34m\t%s:\t\033[1;34m Presente\n", temp);

  fclose(fp_textfile);

  printf("\033[0;32m[ File Chiuso ]\033[0;31m\n");

  clear_skiplist(Dict);
  printf("\033[0;32m[ Deallocazione ]\033[0;31m\n");

  return;
}