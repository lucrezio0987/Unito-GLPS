#include "unity.h"
#include "Interfaccia.h"

#define DICTIONARY_FILE "test_dictionary.csv"
#define INPUT_FILE "test_corectme.csv"

//--------- PROTOTIPI ---------//

void setUp(void);
void tearDown(void);

void test_create_skiplist_empty();
void test_create_node();
void test_insert_skiplist();
void test_load_skiplist();

void test_search_skiplist();

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
	max_height = 1;

  FILE *f_dictionary = fopen(DICTIONARY_FILE, "w");
  if (f_dictionary != NULL) {
      fprintf(f_dictionary, "sole\n");
      fprintf(f_dictionary, "cuore\n");
      fprintf(f_dictionary, "amore");
      fclose(f_dictionary);
  }

  FILE *f_corectme = fopen(INPUT_FILE, "w");
  if (f_corectme != NULL) {
    fprintf(f_corectme, "dammi tre parole sole cuore amore");
    fclose(f_corectme);
  }
  return;
}

void tearDown(void){
  remove(DICTIONARY_FILE);
  remove(INPUT_FILE);
  return;
}

//------ IMPLEMENTAZIONI ------//

void test_create_skiplist_empty(){
  SkipList *skiplist;
	new_skiplist(&skiplist, max_height, &strcmp);

  TEST_ASSERT_NULL(skiplist->head);
}

void test_create_node(){
  char test_string_item[6]="pippo";

  Node *new_node = create_node(max_height, test_string_item);

  TEST_ASSERT_EQUAL(0, CompareString(new_node->item, test_string_item));

  free(new_node);
}

void test_insert_skiplist(){
  char test_string_item_0[6]="pippo";
  char test_string_item_1[6]="pluto";
  char test_string_item_2[6]="paperino";

  SkipList *skiplist;
  new_skiplist(&skiplist, max_height, &CompareString);

  insert_skiplist(skiplist, test_string_item_0);
  insert_skiplist(skiplist, test_string_item_1);
  insert_skiplist(skiplist, test_string_item_2);

  TEST_ASSERT_EQUAL(0, CompareString(skiplist->head->item, test_string_item_2));
  TEST_ASSERT_EQUAL(0, CompareString(skiplist->head->next[0]->item, test_string_item_0));
  TEST_ASSERT_EQUAL(0, CompareString(skiplist->head->next[0]->next[0]->item, test_string_item_1));

}

void test_load_skiplist(){
  SkipList *skiplist;
  new_skiplist(&skiplist, max_height, &CompareString);

  LoadData_no_print(skiplist, DICTIONARY_FILE);
  
  TEST_ASSERT_EQUAL(0, CompareString(skiplist->head->item, "amore"));
  TEST_ASSERT_EQUAL(0, CompareString(skiplist->head->next[0]->item, "cuore"));
  TEST_ASSERT_EQUAL(0, CompareString(skiplist->head->next[0]->next[0]->item, "sole"));

}

void test_search_skiplist(){
  char string[8];

  SkipList *skiplist;
  new_skiplist(&skiplist, max_height, &CompareString);

  LoadData_no_print(skiplist, DICTIONARY_FILE);

  FILE *f_corectme = fopen(INPUT_FILE, "r");
  fscanf(f_corectme, "%s", string); TEST_ASSERT_NULL(search_skiplist(skiplist, string));
  fscanf(f_corectme, "%s", string); TEST_ASSERT_NULL(search_skiplist(skiplist, string));
  fscanf(f_corectme, "%s", string); TEST_ASSERT_NULL(search_skiplist(skiplist, string));
  fscanf(f_corectme, "%s", string); TEST_ASSERT_NOT_NULL(search_skiplist(skiplist, string));
  fscanf(f_corectme, "%s", string); TEST_ASSERT_NOT_NULL(search_skiplist(skiplist, string));
  fscanf(f_corectme, "%s", string); TEST_ASSERT_NOT_NULL(search_skiplist(skiplist, string));

}