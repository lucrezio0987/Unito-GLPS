#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include "Interfaccia.h"

#define INPUT_FILE "../records.csv"
#define OUTPUT_FILE "bin/outfile.csv"

#define MAX_REC 100000
#define N_CONTROLLI 30

typedef struct _Clock {
    int start;
    int end;
    int time;
} Clock;

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

void main() {
    int field = 1;
    int min = 1;
    int max = MAX_REC;
    int n_records=0;
    int time;
    int flag_trovato = 0;
    int i;
    int media_k=0;
    int k_null = 0;
    
    for(i = 0; i< N_CONTROLLI; ++i){
        min = 1;
        max = MAX_REC;
        while(flag_trovato == 0 ) {
            n_records = (min+max)/2;
            if(n_records == 0) {++flag_trovato; ++k_null;}
            else time = calcDif(n_records, field);
            
//            printf(" %6d                   %6d\n", n_records, time);

            if(time == 0 || min >= max)    ++flag_trovato;
            else if(time < 0 )             min = n_records + 1;
            else if(time > 0)              max = n_records -1; 
        }
        printf("(%d) k = %d\n", i, n_records);
        --flag_trovato;
        media_k += n_records;
    }

    media_k = media_k / (N_CONTROLLI -k_null);

    printf("----------------------------\nk_medio = %d\n", media_k);

}


int calcDif(int n_records, int field){
    int i;
    Clock ms, is;
    
    Record *rec = (Record*) malloc(sizeof(Record));
    rec->item_string = (char*) malloc(sizeof(char)*100);

    Array *A = ArrayCreate(0);
    Array *B = ArrayCreate(0);

    FILE *fp = fopen("../../records.csv", "r");

    if(fp == NULL) return 1;
    
    for(i=0; i<n_records; ++i) {
        fscanf(fp, "%ld,%[^,],%ld,%lf\n", &rec->pos, rec->item_string, &rec->item_int, &rec->item_float);
        arrayAdd(A, rec);
        arrayAdd(B, rec);
    }  
    free(rec);
    fclose(fp);

    ms.start = clock();
    MergeSort(A, 0, A->nitems-1, A->nitems);
    ms.end = clock();
    ms.time =  ms.end - ms.start;

    is.start = clock();
    BineryInsertionSort(B, A->nitems);    
    is.end = clock();
    is.time = is.end - is.start;

    if(controllo(A,B,n_records ) != 0) printf("ERRORE\n");

    return  ms.time - is.time;

}

int controllo(Array *A, Array *B, int n_records) {
    int i;

    if(A->nitems != B->nitems || A->nitems != n_records ) return -1;

    for(i=0; i<A->nitems; ++i) {
        if(A->records[i]->pos != B->records[i]->pos) return -1;
    }

    return 0;
}