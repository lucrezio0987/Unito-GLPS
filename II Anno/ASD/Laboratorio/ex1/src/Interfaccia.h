#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#ifndef BINARY_INSERTION_SORT_H_laokjsdnbudjllvfidkfmqm
#define BINARY_INSERTION_SORT_H_laokjsdnbudjllvfidkfmqm

//--------- STRUTTURE ---------//

typedef struct _Array Array;
typedef struct _Record Records;

//--------- PROTOTIPI ---------//

// Funzioni di comparazione
int ComparePos(Records* i, Records* j);
int CompareInt(Records* i, Records* j);
int CompareFloat(Records* i, Records* j);
int CompareString(Records* i, Records* j);

// Funzioni di Creazione e riempomento Array
Records** CreateArray();
void arrayAdd(Array* A, Records *rec);
void LoadArray(Array *A, const char *infile);
void LoadArrayMAX(Array *A, const char *infile, unsigned int max_records);
void PrintArray(const char *outfile, Array *A);

// Algoritmo Merge-Sort
void Merge(void **base, unsigned int l , unsigned int m, unsigned int r, int (*compar)(const void *, const void*));
void MergeSort(void **base, unsigned int i, unsigned int j, int (*compar)(const void *, const void*));

// Algoritmo di Bynary-Insertion-Sort
void Insert(void** base, unsigned int i, unsigned int loc, int (*compar)(const void *, const void*));
int search(unsigned int x, void** base, unsigned int i, unsigned int j, int (*compar)(const void *, const void*));
void BinaryInsertionSort(void** base,  unsigned int nitems, int (*compar)(const void *, const void*));

// Macro-Funzioni
void merge_binary_insertion_sort(void **base, size_t nitems, size_t k, int (*compar)(const void *, const void*));
void sort_records(const char *infile, const char *outfile, size_t k, size_t field);
void sort_recordsMAX(const char *infile, const char *outfile, size_t k, size_t field);

// Test
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

#endif /* BINARY_INSERTION_SORT_H_laokjsdnbudjllvfidkfmqm */