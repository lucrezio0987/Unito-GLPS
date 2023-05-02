#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#ifndef ORDERED_ARRAY_H_laokjsdnbudjllvfidkfmqm
#define ORDERED_ARRAY_H_laokjsdnbudjllvfidkfmqm

typedef struct _Array Array;
typedef struct _Record Records;

// Funzioni di comparazione
int ComparePos(Records* i, Records* j);
int CompareInt(Records* i, Records* j);
int CompareFloat(Records* i, Records* j);
int CompareString(Records* i, Records* j);

// Funzioni di Creazione e riempomento Array
Records** CreateArray();
void arrayAdd(Array* A, Records *rec);
void LoadArray(Array *A, const char *infile);
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

#endif /* ORDERED_ARRAY_H_laokjsdnbudjllvfidkfmqm */
