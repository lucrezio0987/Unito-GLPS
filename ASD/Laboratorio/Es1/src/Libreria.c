#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "Interfaccia.h"

#define DISC 100

struct _Record {
  long int pos; 
  long int item_int;
  double item_float;
  char *item_string;
};

struct _Array{
  Record** records;
  unsigned long nitems;
  unsigned short field;
  int (*compar)(Record*, Record*);
};

int ComparePos(Record* i, Record* j){
    if      (i->pos  < j->pos)    return -1;
    else if (i->pos == j->pos)    return 0;
    else                          return 1;
}

int CompareInt(Record* i, Record* j){
    if      (i->item_int  < j->item_int)    return -1;
    else if (i->item_int == j->item_int)    return 0;
    else                                    return 1;
}

int CompareFloat(Record* i, Record* j){
    if      (i->item_float  < j->item_float)    return -1;
    else if (i->item_float == j->item_float)    return 0;
    else                                        return 1;
}
int CompareString(Record* i, Record* j){
    int res = strcmp(i->item_string,j->item_string);
    if      (res  < 0)    return -1;
    else if (res == 0)    return 0;
    else                  return 1;
    return 0;
}

void printRecord(Array* A, int i_el, void *fp) { 
   fprintf(fp,"%2d%14s%9d%14lf\n", A->records[i_el]->pos, A->records[i_el]->item_string, A->records[i_el]->item_int, A->records[i_el]->item_float);
}

Array* ArrayCreate(unsigned short field){
    Array* A;

    if((A = (Array*) malloc(sizeof(Array))) < 0) return -1;
    A->nitems = 0;
    A->field = field;
    if((A->records = (Record**) malloc(sizeof(Record*))) < 0) return -1;
    
    switch(field) {
        case 0: A->compar = ComparePos; break;
        case 1: A->compar = CompareString; break;
        case 2: A->compar = CompareInt; break;
        case 3: A->compar = CompareFloat; break;
    }
    
    return A;
}

void arrayAdd(Array* A, Record *rec) { 
   A->records = (Record**) realloc(A->records, sizeof(Record*)*(A->nitems+2));
   A->records[A->nitems] = (Record*) malloc(sizeof(Record));
   A->records[A->nitems]->item_string = malloc(sizeof(char)*strlen(rec->item_string));
   
   A->records[A->nitems]->pos         = rec->pos;
   A->records[A->nitems]->item_int    = rec->item_int;
   A->records[A->nitems]->item_float  = rec->item_float;
   strcpy(A->records[A->nitems]->item_string, rec->item_string);

   A->nitems++;
}

void Merge(Array* A, unsigned int l , unsigned int m, unsigned int r, unsigned int nitems) {
    int i = l,  j = m+1,   k = 0;
    Array *B = ArrayCreate(A->field);
    B->records = (Record**) realloc(B->records, sizeof(Record*)*(A->nitems));
    
    for(;i<=m && j<=r; ++k){
      if ((A->compar)(A->records[i],A->records[j]) == -1)   
                       { B->records[k] = A->records[i];  ++i; } 
      else             { B->records[k] = A->records[j];  ++j; }
    }
    for(;i<=m; ++k,++i)  B->records[k] = A->records[i];
    for(;j<=r; ++j,++k)  B->records[k] = A->records[j];
    for(k=l; k<=r; ++k)  A->records[k] = B->records[k-l];

    free(B);
    return;
}

void MergeSort(Array* A, unsigned int i, unsigned int j, unsigned int nitems) {
    int m;
    if(i<j) {
        m = (i+j)/2;
        MergeSort(A,i,m,nitems);
        MergeSort(A,m+1,j,nitems);
        Merge(A,i,m,j,nitems);
    }
    return;
}

void Insert(Array* A, int i, int loc) {
    Record* temp = A->records[i];
    if (i-loc>1) {
        for(; i>loc ; --i){
            A->records[i] = A->records[i-1];
        }
        A->records[loc] = temp;
    } else {
        A->records[i] = A->records[loc];
        A->records[loc] = temp;
    }
}

int search(int x, Array* A, int i, int j) {
    if (i > j) return i;
    int m = (i + j) / 2;
    if ((A->compar)(A->records[x], A->records[m])==0)         return m;
    else if ((A->compar)(A->records[x], A->records[m])==-1)   return search(x, A, i, m - 1);
    else                                                      return search(x, A, m + 1, j);
}

void BineryInsertionSort(Array* A, unsigned int nitems){
    int i,loc, k;
    for(i=1; i < nitems; ++i) {
        if((A->compar)(A->records[i], A->records[i-1]) == -1){
            loc = search(i, A, 0, i-1);
            if(loc<0) printf("Errore %d\n",loc);
            else Insert(A,i,loc);
        }
    }
    return;
}

void merge_binary_insertion_sort(void *A, size_t nitems, unsigned short int field, size_t k){
    if(k>=DISC) 
        MergeSort(A, 0, nitems-1,nitems);
    else        
        BineryInsertionSort(A, nitems); 
    return;
}

int sort_records(const char *infile, const char *outfile, size_t k, size_t field){
    int i, j;
    Record *rec = (Record*) malloc(sizeof(Record));
    rec->item_string = (char*) malloc(sizeof(char)*100);
    
    Array *A = ArrayCreate(field);

    FILE *fp = fopen(infile, "r");
    if(fp == NULL) return 1;
    
    for(i=0; i<k; ++i, j=0) {
        fscanf(fp, "%ld,%[^,],%ld,%lf\n", &rec->pos, rec->item_string, &rec->item_int, &rec->item_float);
        arrayAdd(A, rec);
    }

    fclose(fp);

    merge_binary_insertion_sort(A, A->nitems, field, field);

    fp = fopen(outfile, "w+");
    for(i=0; i<k; ++i, j=0) printRecord(A,i,fp);
    fclose(fp);

    free(A);
    return 0;
}
