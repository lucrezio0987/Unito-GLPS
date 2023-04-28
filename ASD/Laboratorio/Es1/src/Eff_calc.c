#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include "Interfaccia.h"

#define INPUT_FILE "../records.csv"
#define OUTPUT_FILE "bin/outfile.csv"

void main() {
    int field = 0;
    int n_records = 10;
    int i, j;
    int inizio_merge_sort, fine_merge_sort, inizio_insertio_sort, fine_insertio_sort;
    int time_merge_sort=0, time_insertion_sort=0;
    Array *A = ArrayCreate(0);
    Array *B = ArrayCreate(0);
    
    FILE *fp = fopen("../../records.csv", "r");
    if(fp == NULL) return 1;
    
    for(i=0; i<n_records; ++i, j=0) {
        fscanf(fp, "%ld,%[^,],%ld,%lf\n", &rec->pos, rec->item_string, &rec->item_int, &rec->item_float);
        arrayAdd(A, rec);
        arrayAdd(B, rec);
    }  
  
    fclose(fp);
    
    do{
        inizio_merge_sort = clock();
        MergeSort(A, 0, A->nitems-1, A->nitems);
        fine_merge_sort = clock();
        time_merge_sort =  fine_merge_sort - inizio_merge_sort;

        inizio_insertio_sort = clock();
        BineryInsertionSort(B, A->nitems);    
        fine_insertio_sort = clock();
        time_insertion_sort = fine_insertio_sort - inizio_insertio_sort;
    } while(time_merge_sort != time_insertion_sort);


}