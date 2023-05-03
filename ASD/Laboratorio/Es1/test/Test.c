#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "Interfaccia.h"
#include "unity.h"

//#define INPUT_FILE "../records.csv"
//#define OUTPUT_FILE "bin/outfile.csv"

/*
 * Test suite for ordered array data structure and algorithms
 */

int field, n_records;
Record *rec;
Array *A;

void setUp(void){
  field = 0;
  n_records = 10;

//  rec = (Record*) malloc(sizeof(Record));
//  rec->item_string = (char*) malloc(sizeof(char)*100);
}

void tearDown(void){
    return;
}

static void test_sort_records(){
  TEST_ASSERT_EQUAL(0,sort_records("../../records.csv", "outfile.csv", field, n_records));
}

static void test_crate_array_empty(){
  TEST_ASSERT_NOT_EQUAL(-1,ArrayCreate(field));
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

void main() {
  UNITY_BEGIN();

  RUN_TEST(test_sort_records);
  RUN_TEST(test_crate_array_empty);
  
  //RUN_TEST(test_merge_sorte);
  //RUN_TEST(test_binary_insertion_sort);


  
  return UNITY_END();
}