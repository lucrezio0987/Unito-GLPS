#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define INPUT_FILE "/home/lucrezio0987/Documenti/Unito-GLPS/ASD/Laboratorio/Es1/ordered_array_sample_file.csv"
#define OUTPUT_FILE "outfile.csv"

#define DISC 100

typedef struct _Array Array;

struct _Array{
  void** array;
  size_t size;
  unsigned long nitems;
  unsigned short field;
  int (*compar)(void*,void*);
};

int CompareInt(const void* i, const void* j){   
    if (*(int *)i < *(int *)j)          return -1;
    else if (*(int *)i == *(int *)j)    return 0;
    else                                return 1;
}

int CompareFloat(const void* i, const void* j){
    return 0;
}
int CompareChar(const void* i, const void* j) {
    return 0;
}

void swap(Array* A, int a, int b){
    Array* temp = (Array*) malloc(sizeof(Array));
    temp->array = (int**) malloc(sizeof(int*));
    temp->array[0] = (int*) malloc(sizeof(int));

    temp->array[0] = A->array[b];
    A->array[b] = A->array[a];
    A->array[a] = temp->array[0];
}

arrayAdd(Array* A, void* item) {
    switch(A->field){
    case 1: 
        A->array = realloc(A->array, sizeof(char*)*(A->nitems + 2));
        A->array[A->nitems++] = (char*)item;   
        break;
    case 2: 
        A->array = realloc(A->array, sizeof(int*)*(A->nitems + 2));
        A->array[A->nitems++] = (int*)item;   
        break;
    case 3:
        A->array = realloc(A->array, sizeof(float*)*(A->nitems + 2));
        A->array[A->nitems++] = (float*)item;   
        break;
    }
}

Array* ArrayCreate(size_t field){
    Array* A = (Array*) malloc(sizeof(Array));
    A->nitems = 0;
    A->field=field;
    switch(field){
        case 1: 
            A->array = (char**) malloc(sizeof(char*));
            A->array[0] = (char*) malloc(sizeof(char));
            A->size = sizeof(char);
            A->compar = CompareChar; 
            break;
        case 2: 
            A->array = (int**) malloc(sizeof(int*));
            A->array[0] = (int*) malloc(sizeof(int));
            A->size = sizeof(int);
            A->compar = CompareInt;     
            break;
        case 3:
            A->array = (float**) malloc(sizeof(float*));
            A->array[0] = (float*) malloc(sizeof(float));
            A->size = sizeof(float);
            A->compar = CompareFloat;   
            break;
        default: break;
    }
    return A;
}

void test(){
    Array* A = ArrayCreate(1);

    arrayAdd(A, 1);
    arrayAdd(A, 2);
    arrayAdd(A, 3);

    swap(A,0,1);

    printf("%d\n", A->array[0]);
    printf("%d\n", A->array[1]);
    printf("%d\n", A->array[2]);
}

int search(void *item, Array* A, unsigned int i, unsigned int j, int (*compar)(const void*, const void*)){
    unsigned int m;
    if (i>=j) {
        if((compar)(&(A->array)[i],item)==-1)                  return i+1;
        else if((compar)(item,&(A->array)[j])==-1 && j>=0)     return j;
        else                        return i;
    } else {
        m = (i+j)/2;
        if((compar)(&(A->array)[m],item)==0)               return m;
        else {
            if((compar)(item,&(A->array)[m])==-1)            return search(item, A, i, m-1,compar);
            else                    return search(item, A, m+1, j,compar);
        }
    }
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

void merge_binary_insertion_sort(void *base, size_t nitems, size_t size, size_t k, int (*compar)(const void *, const void*)){
    if(k>DISC) 
        MergeSort(base, 0, nitems-1,nitems, compar);
    else        
        InsertionSort(base, nitems, compar);  
    return;
}

void sort_records(const char *infile, const char *outfile, size_t k, size_t field){
    char temp[15], temp_line[20];
    int i, item, j;
    char* token;
    
    Array *A = ArrayCreate(field);
    

    FILE *fp = fopen(infile, "r");
    if(fp == NULL) printf("errore");

    for(i=0; i<k; ++i, j=0) {
        fscanf(fp, "%s\n", temp_line);
        token = strtok(temp_line, ",");
        strcpy(temp, token);
        token = strtok(NULL, ",");
        item = atoi(token);
        switch(field){
            case 0: arrayAdd(A, temp[0]); break;   // char
            case 1: arrayAdd(A, item); break;   // int 
            case 2: arrayAdd(A, item); break;   // float
        }
    }
    fclose(fp);

    merge_binary_insertion_sort(A, A->nitems, A->size, A->nitems, A->compar);

    fp = fopen(outfile, "w+");
    for(i=0; i<A->nitems; ++i)
        printf("%d\n",A->array[i]);
    fclose(fp);
}

void main() {
    sort_records(INPUT_FILE, OUTPUT_FILE, 10, 1);
}