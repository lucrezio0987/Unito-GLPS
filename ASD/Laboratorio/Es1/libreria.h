void stampa(int A[], int nitems){
    printf("[ ");
    for(int i=0; i<nitems; ++i) printf("%d ",A[i]);
    printf("]\n");
    return;
}

int search(int x, int A[], int i, int j){
    int m;
    if (i>=j) {
        if(A[i]<x)                  return i+1;
        else if(x<A[j] && j>=0)     return j;
        else                        return i;
    } else {
        m = (i+j)/2;
        if(A[m] == x)               return m;
        else {
            if(x < A[m])            return search(x, A, i, m-1);
            else                    return search(x, A, m+1, j);
        }
    }
}

void swap(int A[], int i, int j){
    int temp_el = A[j];
    for(;i<j;--j)   A[j] = A[j-1];
    A[i] = temp_el;
    return;
}

void InsertionSort(int A[], int nitems){
    int i,j;
    for(i=1; i < nitems; ++i) {
        if(A[i]<A[i-1]){
            j=search(A[i], A, 0, i-1);
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

int Compare(const void* i, const void* j){   
    if (*(int *)i < *(int *)j)       return -1;
    else if (*(int *)i == *(int *)j) return 0;
    else                     return 1;
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
        InsertionSort(base, nitems);
    }  

    stampa(base, nitems);

    // COMPARAZIONE
    printf("\n  ## Comparezione: base[0] e base[1]\n");
    printf("    --> %d\n",(compar)(base,base+size));
    printf("    --> %d\n",(compar)(base,base));
    printf("    --> %d\n\n",(compar)(base+size,base));

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