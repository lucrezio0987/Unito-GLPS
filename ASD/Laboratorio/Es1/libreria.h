#include "field.h"

void stampa(void* A, int nitems){
    printf("[ ");
    for(int i=0; i<nitems; ++i) printf("%ld ",((long int*)A)[i]);
    printf("]\n");
    return;
}

int search(int x, void *A, int i, int j, int (*compar)(const void *, const void*)){
    int m;
    if (i>=j) {
        if((compar)(A+sizeof(void)*i,x)==-1)                  return i+1;
        else if((compar)(x,A+sizeof(void)*j)==-1 && j>=0)     return j;
        else                        return i;
    } else {
        m = (i+j)/2;
        if((compar)(A+sizeof(void)*m,x)==0)               return m;
        else {
            if((compar)(x,A+sizeof(void)*m)==-1)            return search(x, A, i, m-1,compar);
            else                    return search(x, A, m+1, j,compar);
        }
    }
}

void swap(void *A, int i, int j){
    void temp_el = *(A+sizeof(void)*j);
    
    for(;i<j;--j)   *(A+sizeof(void)*j) = *(A+sizeof(void)*(j-1));
    
    *(A+sizeof(void)*i) = (void)temp_el;
    
    return;
}

void InsertionSort(void *A, int nitems, int (*compar)(const void *, const void*)){
    int i,j;
    for(i=1; i < nitems; ++i) {
        if((compar)(A+sizeof(void)*i, A+sizeof(void)*(i-1)) == -1){
            j=search(A+sizeof(void)*i, A, 0, i-1,compar);
            if(j<0) printf("Errore %d\n",j);
            else swap(A,j,i);
        }
    }
    return;
}

void Merge(int A[], int l , int m, int r, int nitems) {
    int *B,  i = l,  j = m+1,   k = 0;
    B = malloc(nitems);

    for(;i<=m && j<=r; ++k){
      if (A[i]<A[j])   { B[k] = A[i];  ++i; } 
      else             { B[k] = A[j];  ++j; }
    }
    for(;i<=m; ++k,++i)  B[k] = A[i];
    for(;j<=r; ++j,++k)  B[k] = A[j];
    for(k=l; k<=r; ++k)  A[k] = B[k-l];
    
    free(B);
    return;
}

void MergeSort(int A[], int i, int j, int nitems) {
    int m;
    if(i<j) {
        m = (i+j)/2;
        MergeSort(A,i,m,nitems);
        MergeSort(A,m+1,j,nitems);
        Merge(A,i,m,j,nitems);
    }
    return;
}

int CompareInt(const void* i, const void* j){   
    if (*(int *)i < *(int *)j)          return -1;
    else if (*(int *)i == *(int *)j)    return 0;
    else                                return 1;
}

Array* ArrayCreateInteger(int list[], int nitems){
    Array* A = (Array*) malloc(sizeof(Array));

    A->array = (void**)malloc(sizeof(void*)*nitems);
    A->nitems = nitems;
    A->compar = CompareInt;
    
    int i;

    for(i=0; i<nitems; ++i) {
        (A->array)[i] = (void*)list[i];
    }

    return A;
}

void merge_binary_insertion_sort(void *base, size_t nitems, size_t size, size_t k, int (*compar)(const void *, const void*)){
    
    printf("  ## Ordinamento\n    base: ");
    stampa(base, nitems);

    // ORDINAMENTO
    if(k>DISC){                      // Mag
        printf("    (MergeSorte): ");
        MergeSort(base, 0, nitems-1,nitems);
    } else {                        // Min
        printf("    (BinaryInsertionSorte): ");
        InsertionSort(base, nitems, compar);
    }  

    stampa(base, nitems);

    return;
}

/*
- `base` è un puntatore al primo elemento dell'array da ordinare;
- `nitems` è il numero di elementi nell'array da ordinare;
- `size` è la dimensione in bytes di ogni elemento dell'array;
- `k` è un parametro dell'algoritmo;
- `compar` è il criterio secondo cui ordinare i dati (dati due puntatori a elementi dell'array, 
   restituisce un numero maggiore, uguale o minore di zero se il primo argomento è rispettivamente 
   maggiore, uguale o minore del secondo).
*/

void sort_records(const char *infile, const char *outfile, size_t k, size_t field){
    FILE *fp;
    char temp[10];
    int i, item;
    Array *A;

    ArrayCreate(A, field);

    fp = fopen(infile, "r");
    for(i=0; i<k; ++i)
        fscanf("%s,%d\n", temp, item);
        ArrayAddItem(A, item);
    fclose(fp);

    fp = fopen(outfile, "w+");
    for(i=0; i<A->nitems, ++i)
        fprintf(fp,"%d\n", A->array[i]);
    fclose(fp);




}
