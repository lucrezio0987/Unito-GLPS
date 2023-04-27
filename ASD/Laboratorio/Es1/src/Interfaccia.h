#ifndef ORDERED_ARRAY_H_laokjsdnbudjllvfidkfmqm
#define ORDERED_ARRAY_H_laokjsdnbudjllvfidkfmqm

typedef struct _Array Array;
typedef struct _Record Record;

// Funzioni di comparazione
int ComparePos(Record* i, Record* j);
int CompareInt(Record* i, Record* j);
int CompareFloat(Record* i, Record* j);
int CompareString(Record* i, Record* j);

// Funzioni di supporto
void printRecord(Array* A, int i_el, void *fp);

// Funzioni di Creazione e riempomento Array
Array* ArrayCreate(unsigned short field);
void arrayAdd(Array* A, Record *rec);

// Algoritmo Merge-Sort
void Merge(Array* A, unsigned int l , unsigned int m, unsigned int r, unsigned int nitems);
void MergeSort(Array* A, unsigned int i, unsigned int j, unsigned int nitems);

// Algoritmo di Bynary-Insertion-Sort
void Insert(Array* A, int i, int loc);
int search(int x, Array* A, int i, int j);
void BineryInsertionSort(Array* A, unsigned int nitems);

// Macro-Funzioni
void merge_binary_insertion_sort(void *A, size_t nitems, unsigned short int field, size_t k);
void sort_records(const char *infile, const char *outfile, size_t k, size_t field);

#endif /* ORDERED_ARRAY_H_laokjsdnbudjllvfidkfmqm */
