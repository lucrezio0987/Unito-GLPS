// field1: (tipo stringa) contiene parole estratte dalla divina commedia, potete assumere che i valori non contengano spazi o virgole;
// field2: (tipo intero);
// field3: (tipo floating point).

enum FIELD{NONE,FIELD_STRING,FIELD_INT,FIELD_FLOAT};

typedef struct _Array Array;

struct _Array{
  void** array;
  unsigned long nitems;
  unsigned short field;
  int (*compar)(void*,void*);
};

union _temp_element{
    int* field_int;
    char** field_char;
    float* field_float;
};


// -- PROTOTIPI
/* x */ //int CompareInt(const void* i, const void* j);
/*   */ //int CompareFloat(const void* i, const void* j);
/*   */ //int CompareString(const void* i, const void* j);
/* x */ //Array* ArrayCreate(size_t field);
/* x */ //void ArrayAddItem(Array* A, void *item);
/* x */ //void merge_binary_insertion_sort(void *base, size_t nitems, size_t size, size_t k, int (*compar)(const void *, const void*));
/* x */ //void sort_records(const char *infile, const char *outfile, size_t k, size_t field);
/*   */ //void swap(Array* A, unsigned int i, unsigned int j)
/*   */ //void InsertionSort(Array* A, unsigned int nitems, int (*compar)(const void *, const void*));
/*   */ //void Merge(Array* A, unsigned int l , unsigned int m, unsigned int r, unsigned int nitems, int (*compar)(const void *, const void*));
/* x */ //void MergeSort(Array* A, unsigned int i, unsigned int j, unsigned int nitems, int (*compar)(const void *, const void*));
// -------------

int CompareInt(const void* i, const void* j){   
    if (*(int *)i < *(int *)j)          return -1;
    else if (*(int *)i == *(int *)j)    return 0;
    else                                return 1;
}

int CompareFloat(const void* i, const void* j){
    return 0;
}
int CompareString(const void* i, const void* j) {
    return 0;
}

Array* ArrayCreate(size_t field){
    Array* A = (Array*) malloc(sizeof(Array));
    A->nitems = 0;
    A->field=field;
    switch(field){
        case 1: 
            A->array = (char**) malloc(sizeof(char*));
            A->compar = CompareString;  
            break;
        case 2: 
            A->array = (int**) malloc(sizeof(int*));
            A->compar = CompareInt;     
            break;
        case 3:
            A->array = (float**) malloc(sizeof(float*)); 
            A->compar = CompareFloat;   
            break;
        default: break;
    }
    return A;
}

void ArrayAddItem(Array* A, void *item) {
    A->nitems = A->nitems + 1;
    switch(A->field){
        case 1: 
            realloc(A->array, sizeof(char*)*A->nitems);
            A->array[A->nitems-1] = *(char *)item;
            break;
        case 2: 
            realloc(A->array, sizeof(int*)*A->nitems);
            (A->array)[A->nitems-1] = *(int *)item;
            break;
        case 3:
            realloc(A->array, sizeof(float*)*A->nitems);
            (A->array)[A->nitems-1] = item;
            break;
        default: break;
    }
    return;
}

void merge_binary_insertion_sort(void *base, size_t nitems, size_t size, size_t k, int (*compar)(const void *, const void*)){
    if(k>DISC) 
        MergeSort(base, 0, nitems-1,nitems, compar);
    else        
        InsertionSort(base, nitems, compar);  
    return;
}

void sort_records(const char *infile, const char *outfile, size_t k, size_t field){
    FILE *fp;
    char temp[10];
    int i, item;
    
    Array *A = ArrayCreate(field);

    fp = fopen(infile, "r");
    for(i=0; i<k; ++i)
        fscanf("%s,%d\n", temp, item);
        ArrayAddItem(A, item);
    fclose(fp);

    merge_binary_insertion_sort(A, A->nitems, sizeof((A->array)[0]), A->nitems, A->compar);

    fp = fopen(outfile, "w+");
    for(i=0; i<A->nitems; ++i)
        fprintf(fp,"%d\n", A->array[i]);
    fclose(fp);

    return;
}

int search(void *x, Array* A, unsigned int i, unsigned int j, int (*compar)(const void *, const void*)){
    unsigned int m;
    if (i>=j) {
        if((compar)((A->array)[i],*x)==-1)                  return i+1;
        else if((compar)(*x,(A->array)[j])==-1 && j>=0)     return j;
        else                        return i;
    } else {
        m = (i+j)/2;
        if((compar)((A->array)[m],*x)==0)               return m;
        else {
            if((compar)(*x,(A->array)[m])==-1)            return search(x, A, i, m-1,compar);
            else                    return search(x, A, m+1, j,compar);
        }
    }
}

void swap(Array* A, unsigned int i, unsigned int j){
    union _temp_element temp_el;
    switch(A->field){
        case 1: 
            temp_el.field_char = malloc(sizeof(char*));
            temp_el.field_char = (A->array)[i];
            break;
        case 2: 
            temp_el.field_int = malloc(sizeof(int)); 
            temp_el.field_int = (A->array)[i];
            break;
        case 3:
            temp_el.field_float = malloc(sizeof(float));
            temp_el.field_float = (A->array)[i]; 
            break;
        default: break;
    }


    while(i<j) {   
        (A->array)[j] = (A->array)[j-1];
        --j;
    }
    
        switch(A->field){
        case 1: 
            (A->array)[i] = temp_el.field_char;
            break;
        case 2: 
            (A->array)[i] = temp_el.field_int;
            break;
        case 3:
            (A->array)[i] = temp_el.field_float; 
            break;
        default: break;
    }
    
    return;
}

void InsertionSort(Array* A, unsigned int nitems, int (*compar)(const void *, const void*)){
    int i,j;
    for(i=1; i < nitems; ++i) {
        if((compar)((A->array)[i], (A->array)[i-1]) == -1){
            j=search(A->array, (A->array), 0, i-1,compar);
            if(j<0) printf("Errore %d\n",j);
            else swap(A,j,i);
        }
    }
    return;
}

void Merge(Array* A, unsigned int l , unsigned int m, unsigned int r, unsigned int nitems, int (*compar)(const void *, const void*)) {
    int i = l,  j = m+1,   k = 0;
    Array *B = ArrayCreate(A->field);
    switch(A->field){
        case 1: 
            realloc(B->array, sizeof(char*)*A->nitems);
            break;
        case 2: 
            realloc(B->array, sizeof(int*)*A->nitems);
            break;
        case 3:
            realloc(B->array, sizeof(float*)*A->nitems);
            break;
        default: break;
    }

    for(;i<=m && j<=r; ++k){
      if ((compar)((A->array)[i],(A->array)[j]) == -1)   
                       { (B->array)[k] = (A->array)[i];  ++i; } 
      else             { (B->array)[k] = (A->array)[j];  ++j; }
    }
    for(;i<=m; ++k,++i)  (B->array)[k] = (A->array)[i];
    for(;j<=r; ++j,++k)  (B->array)[k] = (A->array)[j];
    for(k=l; k<=r; ++k)  (A->array)[k] = (B->array)[k-l];
    
    free(B);
    return;
}

void MergeSort(Array* A, unsigned int i, unsigned int j, unsigned int nitems, int (*compar)(const void *, const void*)) {
    int m;
    if(i<j) {
        m = (i+j)/2;
        MergeSort(A,i,m,nitems,compar);
        MergeSort(A,m+1,j,nitems,compar);
        Merge(A,i,m,j,nitems,compar);
    }
    return;
}