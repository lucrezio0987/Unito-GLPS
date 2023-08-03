#include "unity.h"
#include "Interfaccia.h"

#define OUTPUT_FILE "test_outfile.csv"
#define INPUT_FILE "test_records.csv"
//#define INPUT_FILE "../records.csv"
//#define OUTPUT_FILE "bin/outfile.csv"

#define K 50


//--------- PROTOTIPI ---------//

int Array_is_empty(Array *array);

void setUp(void);
void tearDown(void);

void test_create_array_empty();
void test_create_array_add_not_empty();
void test_LoadArray();
void test_LoadArrayMAX();

void test_ComparePos();
void test_CompareInt();
void test_CompareFloat();
void test_CompareString();

void test_sort_records_Pos();
void test_sort_records_String();
void test_sort_records_Int();
void test_sort_records_Float();

//void test_merge_sorte();
//void test_binary_insertion_sort();
//void test_merge_binary_insertion_sort();

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

int n_records;

//--- AMBIENTE ---//

void setUp(void){
  n_records = 5;

  FILE *fin = fopen(INPUT_FILE, "w");
  if (fin != NULL) {
      fprintf(fin, "1,FruttoE,10,6.5\n");
      fprintf(fin, "2,FruttoD,20,5.8\n");
      fprintf(fin, "3,FruttoC,15,4.2\n");
      fprintf(fin, "4,FruttoA,45,1.5\n");
      fprintf(fin, "5,FruttoB,40,7.0\n");
      fclose(fin);
  }

  FILE *fout = fopen(OUTPUT_FILE, "w");
  if (fout != NULL) {
    fclose(fout);
  }
}

void tearDown(void){
  remove(INPUT_FILE);
  remove(OUTPUT_FILE);
  return;
}

//------ IMPLEMENTAZIONI ------//

int Array_is_empty(Array *array) {
  if (array == NULL) {
    fprintf(stderr, "Array_is_empty: array parameter cannot be NULL");
    exit(EXIT_FAILURE);
  }
  return array->nitems == 0;
}


void test_create_array_empty(){
  TEST_ASSERT_NOT_NULL(CreateArray());
}

void test_create_array_add_not_empty(){
  Array *A = CreateArray();
  Records *rec = (Records*) malloc(sizeof(Records));
  
  rec -> item_int = 10;
  rec -> item_float = 10.10;
  rec -> pos = 0;
  rec -> item_string = (char*) malloc(sizeof(char)*100);
  strcpy(rec->item_string , "Frutto");
  
  printf("%s",rec->item_string);
  
  arrayAdd(A,rec);
  
  TEST_ASSERT_FALSE(Array_is_empty(A));

  free(A);
  free(rec);
}

void test_LoadArray() {
  Array *A = CreateArray();
  LoadArray(A, INPUT_FILE);
  TEST_ASSERT_FALSE(Array_is_empty(A));
  free(A);
}

void test_LoadArrayMAX() {
  Array *A = CreateArray();
  LoadArrayMAX(A, INPUT_FILE,n_records);
  TEST_ASSERT_FALSE(Array_is_empty(A));
  free(A);
}

void test_ComparePos() {
  Records *a = (Records*)malloc(sizeof(Records));
  Records *b = (Records*)malloc(sizeof(Records));
  
  a->pos = 1;
  b->pos = 2;

  TEST_ASSERT_EQUAL(-1, ComparePos(a, b));
  TEST_ASSERT_EQUAL(0, ComparePos(a, a));
  TEST_ASSERT_EQUAL(1, ComparePos(b, a));

  free(a);
  free(b);
}

void test_CompareInt() {
  Records *a = (Records*)malloc(sizeof(Records));
  Records *b = (Records*)malloc(sizeof(Records));

  a->item_int = 1;
  b->item_int = 2;

  TEST_ASSERT_EQUAL(-1, CompareInt(a, b));
  TEST_ASSERT_EQUAL(0, CompareInt(a, a));
  TEST_ASSERT_EQUAL(1, CompareInt(b, a));

  free(a);
  free(b);
}

void test_CompareFloat() {
  Records *a = (Records*)malloc(sizeof(Records));
  Records *b = (Records*)malloc(sizeof(Records));

  a->item_float = 1.5;
  b->item_float = 2.5;

  TEST_ASSERT_EQUAL(-1, CompareFloat(a, b));
  TEST_ASSERT_EQUAL(0, CompareFloat(a, a));
  TEST_ASSERT_EQUAL(1, CompareFloat(b, a));

  free(a);
  free(b);
}

void test_CompareString() {
  Records *a = (Records*)malloc(sizeof(Records));
  Records *b = (Records*)malloc(sizeof(Records));

  a->item_string = strdup("fruttoA");
  b->item_string = strdup("fruttoB");
  
  TEST_ASSERT_EQUAL(-1, CompareString(a, b));
  TEST_ASSERT_EQUAL(0, CompareString(a, a));
  TEST_ASSERT_EQUAL(1, CompareString(b, a));

  free(a->item_string);
  free(b->item_string);
  
  free(a);
  free(b);
}


void test_sort_records_Pos(){
    sort_records(INPUT_FILE, OUTPUT_FILE, K, 0);

    Array *A = CreateArray();
    LoadArray(A, OUTPUT_FILE);

    TEST_ASSERT_TRUE(ComparePos(A->base[0], A->base[1]) <= 0);
    TEST_ASSERT_TRUE(ComparePos(A->base[1], A->base[2]) <= 0);
    TEST_ASSERT_TRUE(ComparePos(A->base[2], A->base[3]) <= 0);
    TEST_ASSERT_TRUE(ComparePos(A->base[3], A->base[4]) <= 0);

    free(A);
}
void test_sort_records_String(){
    sort_records(INPUT_FILE, OUTPUT_FILE, K, 1);

    Array *A = CreateArray();
    LoadArray(A, OUTPUT_FILE);

    TEST_ASSERT_TRUE(CompareString(A->base[0], A->base[1]) <= 0);
    TEST_ASSERT_TRUE(CompareString(A->base[1], A->base[2]) <= 0);
    TEST_ASSERT_TRUE(CompareString(A->base[2], A->base[3]) <= 0);
    TEST_ASSERT_TRUE(CompareString(A->base[3], A->base[4]) <= 0);

    free(A);
}
void test_sort_records_Int(){
    sort_records(INPUT_FILE, OUTPUT_FILE, K, 2);

    Array *A = CreateArray();
    LoadArray(A, OUTPUT_FILE);

    TEST_ASSERT_TRUE(CompareInt(A->base[0], A->base[1]) <= 0);
    TEST_ASSERT_TRUE(CompareInt(A->base[1], A->base[2]) <= 0);
    TEST_ASSERT_TRUE(CompareInt(A->base[2], A->base[3]) <= 0);
    TEST_ASSERT_TRUE(CompareInt(A->base[3], A->base[4]) <= 0);

    free(A);
}
void test_sort_records_Float(){
    sort_records(INPUT_FILE, OUTPUT_FILE, K, 3);

    Array *A = CreateArray();
    LoadArray(A, OUTPUT_FILE);

    TEST_ASSERT_TRUE(CompareFloat(A->base[0], A->base[1]) <= 0);
    TEST_ASSERT_TRUE(CompareFloat(A->base[1], A->base[2]) <= 0);
    TEST_ASSERT_TRUE(CompareFloat(A->base[2], A->base[3]) <= 0);
    TEST_ASSERT_TRUE(CompareFloat(A->base[3], A->base[4]) <= 0);

    free(A);
}