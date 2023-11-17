#include <errno.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

/*
- Compare functions used to switch the type of sort
- create_array, array_add, load_array, print_array functions used to create an empty array, add an element, 
  load an input file to the array, print array on file
- merge_binary_insertion_sort, sort_records functions used to choice the algorithm depending on k value, 
  called by main depending on parameter field call the function described previously
- load_array_max and sort_records_max are used to test the code
- merge, merge_sort_ric, merge_sort functions used to execute the merge algorithm
- insert, sort, binary_insertion_sort used to execute the binaryInsertion algorithm
*/

#define ERROR                                                                                                                                     \
    {                                                                                                                                             \
        if (errno) {                                                                                                                              \
            fprintf(stderr, "ERROR - line %d: file \"%s\" (pid %ld) - n %d - (%s)\n", __LINE__, __FILE__, (int)getpid(), errno, strerror(errno)); \
            exit(1);                                                                                                                              \
        }                                                                                                                                         \
    }

#ifndef BINARY_INSERTION_SORT_H_laokjsdnbudjllvfidkfmqm
#define BINARY_INSERTION_SORT_H_laokjsdnbudjllvfidkfmqm

//--------- STRUTTURE ---------//

typedef struct _Array Array;
typedef struct _Record Records;

//--------- PROTOTIPI ---------//

// Funzioni di comparazione
int compare_pos(Records* i, Records* j);
int compare_int(Records* i, Records* j);
int compare_float(Records* i, Records* j);
int compare_string(Records* i, Records* j);

// Funzioni di Creazione e riempimento Array
Records** create_array();
void array_add(Array* A, Records* rec);
void load_array(Array* A, const char* infile);
void load_array_max(Array* A, const char* infile, unsigned int max_records);
void print_array(const char* outfile, Array* A);

// Algoritmo Merge-Sort
void merge(void** base, unsigned int l, unsigned int m, unsigned int r, int (*compar)(const void*, const void*));
void merge_sort_ric(void** base, unsigned int i, unsigned int j, int (*compar)(const void*, const void*));
void merge_sort(void** base, unsigned int i, unsigned int j, int (*compar)(const void*, const void*));

// Algoritmo di Bynary-Insertion-Sort
void insert(void** base, unsigned int i, unsigned int loc, int (*compar)(const void*, const void*));
int search(unsigned int x, void** base, unsigned int i, unsigned int j, int (*compar)(const void*, const void*));
void binary_insertion_sort(void** base, unsigned int nitems, int (*compar)(const void*, const void*));

// Macro-Funzioni
void merge_binary_insertion_sort(void** base, size_t nitems, size_t k, int (*compar)(const void*, const void*));
void sort_records(const char* infile, const char* outfile, size_t k, size_t field);
void sort_records_max(const char* infile, const char* outfile, size_t k, size_t field, size_t max_records);

// Test
int array_is_empty(Array* array);

void setUp(void);
void tearDown(void);

void test_create_array_empty();
void test_create_array_add_not_empty();
void test_load_array();
void test_load_array_max();

void test_compare_pos();
void test_compare_int();
void test_compare_float();
void test_compare_string();

void test_sort_records_Pos();
void test_sort_records_String();
void test_sort_records_Int();
void test_sort_records_Float();

// void test_merge_sorte();
// void test_binary_insertion_sort();
// void test_merge_binary_insertion_sort();

#endif /* BINARY_INSERTION_SORT_H_laokjsdnbudjllvfidkfmqm */
