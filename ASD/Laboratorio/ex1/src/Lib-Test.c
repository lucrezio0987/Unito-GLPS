#include "../test/unity.h"
#include "Interfaccia.h"

#define OUTPUT_FILE "outfile.csv"

//#define INPUT_FILE "../records.csv"
//#define OUTPUT_FILE "bin/outfile.csv"


//--------- PROTOTIPI ---------//

int Array_is_empty(Array *array);

void setUp(void);
void tearDown(void);

static void test_create_array_empty();
static void test_create_array_add_not_empty();
static void test_LoadArray();

static void test_merge_sorte();
static void test_binary_insertion_sort();
static void test_merge_binary_insertion_sort();

static void test_ComparePos();
static void test_CompareInt();
static void test_CompareFloat();

//--------- STRUTTURE ---------//

struct _Record {
  long int pos; 
  long int item_int;
  double item_float;
  char *item_string;
};

struct _Array {
    Records **base;
    unsigned int nitems;
};

//------ VARIABILI GLOBALI ------//

int field, n_records;
Records *rec;
Array *A;

//------ IMPLEMENTAZIONI ------//

int Array_is_empty(Array *array) {
  if (array == NULL) {
    fprintf(stderr, "Array_is_empty: array parameter cannot be NULL");
    exit(EXIT_FAILURE);
  }
  return array->nitems == 0;
}


//--- TEST ---//

void setUp(void){
  field = 0;
  n_records = 10;
}

void tearDown(void){
    return;
}

//static void test_sort_records(){
//  //TEST_ASSERT_EQUAL(0,sort_records("../../records.csv", "outfile.csv", field, n_records));
//}

static void test_create_array_empty(){
  TEST_ASSERT_NOT_NULL(CreateArray());
}

static void test_create_array_add_not_empty(){
   Array *A = CreateArray();
   rec = (Records*) malloc(sizeof(Records));
   rec -> item_int = 10;
   rec -> item_float = 10.10;
   rec -> pos = 0;
   rec -> item_string = (char*) malloc(sizeof(char)*100);
   strcpy(rec->item_string , "pippo");
   printf("%s",rec->item_string);
   arrayAdd(A,rec);
   TEST_ASSERT_FALSE(Array_is_empty(A));
   PrintArray(OUTPUT_FILE,A);
}


//static void test_merge_sorte(){
//  int i, j;
//  Array *A = ArrayCreate(0);
//  Array *B = ArrayCreate(0);
//  
//  FILE *fp = fopen("../../records.csv", "r");
//  if(fp == NULL) return 1;
//  
//  for(i=0; i<n_records; ++i, j=0) {
//      fscanf(fp, "%ld,%[^,],%ld,%lf\n", &rec->pos, rec->item_string, &rec->item_int, &rec->item_float);
//      arrayAdd(A, rec);
//  }  
//
//  fclose(fp);
//  
//  MergeSort(A, 0, A->nitems-1, A->nitems);
//
//  // TEST_ASSERT_NOT_EQUAL(-1,);
//  // A e B devono essere uguali essendo ordinato per field = 0
//}

//static void test_binary_insertion_sort(){
//  int i, j;
//  Array *A = ArrayCreate(0);
//  Array *B = ArrayCreate(0);
//  
//  FILE *fp = fopen("../../records.csv", "r");
//  if(fp == NULL) return 1;
//  
//  for(i=0; i<n_records; ++i, j=0) {
//      fscanf(fp, "%ld,%[^,],%ld,%lf\n", &rec->pos, rec->item_string, &rec->item_int, &rec->item_float);
//      arrayAdd(A, rec);
//      arrayAdd(B, rec);
//  }  
//
//  fclose(fp);
//  
//  BineryInsertionSort(A, A->nitems);
//
//  // TEST_ASSERT_NOT_EQUAL(-1,);
//  // A e B devono essere uguali essendo ordinato per field = 0
//}


//!--- ANNO SCORSO ---//

/*static void test_ComparePos() {
  Records *rec1 = (Records*)malloc(sizeof(Records));
  Records *rec2 = (Records*)malloc(sizeof(Records));
  rec1->pos = 10;
  rec2->pos = 20;
  TEST_ASSERT_EQUAL(-1, ComparePos(rec1, rec2));
  TEST_ASSERT_EQUAL(1, ComparePos(rec2, rec1));
  TEST_ASSERT_EQUAL(0, ComparePos(rec1, rec1));
  free(rec1);
  free(rec2);
}

static void test_CompareInt() {
  Records *rec1 = (Records*)malloc(sizeof(Records));
  Records *rec2 = (Records*)malloc(sizeof(Records));
  rec1->item_int = 10;
  rec2->item_int = 20;
  TEST_ASSERT_EQUAL(-1, CompareInt(rec1, rec2));
  TEST_ASSERT_EQUAL(1, CompareInt(rec2, rec1));
  TEST_ASSERT_EQUAL(0, CompareInt(rec1, rec1));
  free(rec1);
  free(rec2);
}

static void test_CompareFloat() {
  Records *rec1 = (Records*)malloc(sizeof(Records));
  Records *rec2 = (Records*)malloc(sizeof(Records));
  rec1->item_float = 10.5;
  rec2->item_float = 20.5;
  TEST_ASSERT_EQUAL(-1, CompareFloat(rec1, rec2));
  TEST_ASSERT_EQUAL(1, CompareFloat(rec2, rec1));
  TEST_ASSERT_EQUAL(0, CompareFloat(rec1, rec1));
  free(rec1);
  free(rec2);
}

static void test_CompareString() {
  Records *rec1 = (Records*)malloc(sizeof(Records));
  Records *rec2 = (Records*)malloc(sizeof(Records));
  rec1->item_string = strdup("hello");
  rec2->item_string = strdup("world");
  TEST_ASSERT_EQUAL(-1, CompareString(rec1, rec2));
  TEST_ASSERT_EQUAL(1, CompareString(rec2, rec1));
  TEST_ASSERT_EQUAL(0, CompareString(rec1, rec1));
  free(rec1->item_string);
  free(rec2->item_string);
  free(rec1);
  free(rec2);
}

static void test_CreateArray() {
  A = CreateArray();
  TEST_ASSERT_NOT_NULL(A);
  TEST_ASSERT_TRUE(Array_is_empty(A));
}

static void test_arrayAdd() {
  A = CreateArray();
  rec = (Records*)malloc(sizeof(Records));
  rec->item_int = 10;
  rec->item_float = 10.10;
  rec->pos = 0;
  rec->item_string = strdup("pippo");
  arrayAdd(A, rec);
  TEST_ASSERT_FALSE(Array_is_empty(A));
  free(rec->item_string);
  free(rec);
}

static void test_LoadArray() {
  A = CreateArray();
  LoadArray(A, INPUT_FILE);
  TEST_ASSERT_FALSE(Array_is_empty(A));
}*/


/*#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "../src/Interfaccia.h"
#include "unity.h"

#define INPUT_FILE "inputfile.csv"
#define OUTPUT_FILE "outputfile.csv"

// Test suite setup
void setUp(void) {
    // Crea un file di input con dati di test non ordinati
    FILE *fp = fopen(INPUT_FILE, "w");
    if (fp != NULL) {
        fprintf(fp, "3,Apple,50,2.5\n");
        fprintf(fp, "1,Orange,30,1.8\n");
        fprintf(fp, "2,Banana,40,2.2\n");
        fprintf(fp, "4,Grape,20,1.5\n");
        fprintf(fp, "5,Strawberry,60,3.0\n");
        fclose(fp);
    }
}

// Test suite cleanup
void tearDown(void) {
    // Elimina il file di input
    remove(INPUT_FILE);

    // Elimina il file di output
    remove(OUTPUT_FILE);
}

void test_sort_records() {
    // Esegui la funzione sort_records
    sort_records(INPUT_FILE, OUTPUT_FILE, 1, 0);  // Ordina in base al campo 1 (item_string)

    // Carica il file di output generato dalla funzione
    Array *sortedArray = CreateArray();
    LoadArray(sortedArray, OUTPUT_FILE);

    // Verifica se l'output è ordinato correttamente
    // Verifica che i record siano ordinati in base al campo 1 (item_string)
    TEST_ASSERT_TRUE(CompareString(sortedArray->base[0], sortedArray->base[1]) <= 0);
    TEST_ASSERT_TRUE(CompareString(sortedArray->base[1], sortedArray->base[2]) <= 0);
    TEST_ASSERT_TRUE(CompareString(sortedArray->base[2], sortedArray->base[3]) <= 0);
    TEST_ASSERT_TRUE(CompareString(sortedArray->base[3], sortedArray->base[4]) <= 0);

    // Pulisci i dati di test
    free(sortedArray);

    // Elimina il file di output generato
    remove(OUTPUT_FILE);
}

int main(void) {
    UNITY_BEGIN();

    // Run the test cases
    RUN_TEST(test_sort_records);

    return UNITY_END();
}*/