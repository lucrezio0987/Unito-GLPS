#include <stdio.h>
#include <stdlib.h>

#define DISC 100
#define DIM1 10
#define DIM2 101

#include "libreria.h"

void main() {
    //INPUT

    int A_in[DIM1] = {10, 8 , 9, 11, 6, 5, 1, 3, 2, 5};
    int B_in[DIM2] = {10, 8 , 9, 11, 6, 5, 1, 3, 2, 5, 
                      10, 8 , 9, 11, 6, 5, 1, 3, 2, 5,
                      10, 8 , 9, 11, 6, 5, 1, 3, 2, 5,
                      10, 8 , 9, 11, 6, 5, 1, 3, 2, 5,
                      10, 8 , 9, 11, 6, 5, 1, 3, 2, 5,
                      10, 8 , 9, 11, 6, 5, 1, 3, 2, 5,
                      10, 8 , 9, 11, 6, 5, 1, 3, 2, 5,
                      10, 8 , 9, 11, 6, 5, 1, 3, 2, 5,
                      10, 8 , 9, 11, 6, 5, 1, 3, 2, 5,
                      10, 8 , 9, 11, 6, 5, 1, 3, 2, 5, 0};

    Array *A = ArrayCreateInteger(A_in, DIM1);
    Array *B = ArrayCreateInteger(B_in, DIM2);

    printf("Vettore A[%d]\n",A->nitems);
    merge_binary_insertion_sort(A->array, A->nitems, sizeof((A->array)[0]), A->nitems, A->compar);
    printf("Vettore B[%d]\n",B->nitems);
    merge_binary_insertion_sort(B->array, B->nitems, sizeof((B->array)[0]), B->nitems, B->compar);

    return;
}
