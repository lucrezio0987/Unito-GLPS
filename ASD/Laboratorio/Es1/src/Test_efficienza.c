#include <stdio.h>
#include <stdlib.h>

#include <time.h>

#include "Interfaccia.h"

#define INPUT_FILE "../records.csv"
#define OUTPUT_FILE "outfile.csv"

#define N_CONTROLLI 10

typedef struct _Clock {
    int start;
    int end;
    int time;
} Clock;

typedef struct _Record {
  long int pos; 
  long int item_int;
  double item_float;
  char *item_string;
} Records;

typedef struct _Array {
    Records **base;
    unsigned int nitems;
} Array;

void main() {
    int field = 2;
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
            printf(":::: test: %d\n", n_records);
            if(n_records == 0) {++flag_trovato; ++k_null;}
            else time = calcDif(n_records, field);
            
            if(time == 0 || min >= max)    ++flag_trovato;
            else if(time < 0 )             min = n_records + 1;
            else if(time > 0)              max = n_records -1; 
        }
        printf("================================ (%d) k = %d\n", i, n_records);
        --flag_trovato;
        media_k += n_records;
    }

    media_k = media_k / (N_CONTROLLI -k_null);

    printf(">>> k_medio = %d\n", media_k);

}

int calcDif(unsigned int n_records,unsigned int field){
    int i;
    Clock ms, is;
    
    Array *A = CreateArray();
    Array *B = CreateArray();
    
    LoadArrayMAX(A, INPUT_FILE, n_records);
    LoadArrayMAX(B, INPUT_FILE, n_records);

    printf(":: (I) Merge - ");
    ms.start = clock();
    switch(field){
        case 1:  MergeSort(A->base, 0, A->nitems-1, CompareString); break;
        case 2:  MergeSort(A->base, 0, A->nitems-1, CompareInt);    break;
        case 3:  MergeSort(A->base, 0, A->nitems-1, CompareFloat);  break;
        default: MergeSort(A->base, 0, A->nitems-1, ComparePos);    break;
    }
    ms.end = clock();
    ms.time =  ms.end - ms.start;

    printf("(F) Merge (%d)\n:: (I) BinaryInsertion - ",ms.time);
    is.start = clock();
    switch(field){
        case 1:  BinaryInsertionSort(B->base, B->nitems, CompareString);    break;
        case 2:  BinaryInsertionSort(B->base, B->nitems, CompareInt);       break;
        case 3:  BinaryInsertionSort(B->base, B->nitems, CompareFloat);     break;
        default: BinaryInsertionSort(B->base, B->nitems, ComparePos);       break;
    }
    is.end = clock();
    is.time = is.end - is.start;
    
    printf("(F) BinaryInsertion (%d)\n", is.time);

    if(controllo(A,B,n_records ) != 0) printf(":: ERRORE: vettori non uguali\n");
    printf("\n");

    free(A);
    free(B);

    return   is.time - ms.time;

}

int controllo(Array *A, Array *B, int n_records) {
    int i;

    //printf("\n%d - %d: %d", A->nitems, B->nitems, n_records);


    if(A->nitems != B->nitems || A->nitems != n_records ) return -2;

    for(i=0; i<A->nitems; ++i) {
        if(A->base[i]->pos != B->base[i]->pos) return -1;
    }

    return 0;
}