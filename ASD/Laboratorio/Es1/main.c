#include <stdio.h>
#include <stdlib.h>

#define DISC 100
#define DIM1 10
#define DIM2 101

#include "libreria.h"

void main() {
    //INPUT
    int A[DIM1]= {10, 8 , 9, 11, 6, 5, 1, 3, 2, 5};

    int B[DIM2]= {10, 8 , 9, 11, 6, 5, 1, 3, 2, 5, 
                 10, 8 , 9, 11, 6, 5, 1, 3, 2, 5,
                 10, 8 , 9, 11, 6, 5, 1, 3, 2, 5,
                 10, 8 , 9, 11, 6, 5, 1, 3, 2, 5,
                 10, 8 , 9, 11, 6, 5, 1, 3, 2, 5,
                 10, 8 , 9, 11, 6, 5, 1, 3, 2, 5,
                 10, 8 , 9, 11, 6, 5, 1, 3, 2, 5,
                 10, 8 , 9, 11, 6, 5, 1, 3, 2, 5,
                 10, 8 , 9, 11, 6, 5, 1, 3, 2, 5,
                 10, 8 , 9, 11, 6, 5, 1, 3, 2, 5, 0};

    printf("Vettore A[10]\n");
    merge_binary_insertion_sort(A, DIM1, sizeof(A[0]), DIM1, Compare);
    printf("Vettore A[101]\n");
    merge_binary_insertion_sort(B, DIM2, sizeof(B[0]), DIM2, Compare);

    return;
}
