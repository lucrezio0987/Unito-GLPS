#include "unity.h"
#include "Interfaccia.h"

#define DICTIONARY_FILE "test_dictionary.csv"
#define INPUT_FILE "test_corectme.csv"

//--------- PROTOTIPI ---------//

void Skiplist_is_empty();

void setUp(void);
void tearDown(void);

void test_create_skiplist_empty();
void test_insert_skiplist();
void test_load_skiplist();

void test_search_skiplist();
void test_find_errors();

//--------- STRUTTURE ---------//

typedef enum {FALSE, TRUE};

struct Node { 
  struct Node **next; 
  size_t size;
  void *item;
}; 

struct SkipList { 
  struct Node *head; 
  size_t max_level; 
  size_t max_height;
  int (*compare)(const void*, const void*);
};

//------ VARIABILI GLOBALI ------//
int max_height;
//--- AMBIENTE ---//

void setUp(void){
	max_height = 5;
  return;
}

void tearDown(void){
  remove(DICTIONARY_FILE);
  remove(INPUT_FILE);
  return;
}

//------ IMPLEMENTAZIONI ------//

int Skiplist_is_empty(struct SkipList *list){
  if(list->head == NULL) return TRUE;
  else return FALSE;
}

void test_create_skiplist_empty(){
  SkipList *new_skiplist;
	new_skiplist(&new_skiplist, max_height, &strcmp);

  TEST_ASSERT_EQUAL(max_height, new_skiplist->max_height);
}

void test_insert_skiplist(){

}

void test_load_skiplist(){

}

void test_search_skiplist(){

}

void test_find_errors(){

}