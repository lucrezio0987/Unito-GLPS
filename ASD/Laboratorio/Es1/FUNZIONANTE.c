#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define INPUT_FILE "../records.csv"
#define INPUT_FILE_BIG "ordered_array_sample_file.csv"
#define OUTPUT_FILE "outfile.csv"
#define N_RECORDS 10

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
    if (*(float *)i < *(float *)j)          return -1;
    else if (*(float *)i == *(float *)j)    return 0;
    else                                    return 1;
}

int CompareChar(const void* i, const void* j) {
    for(int k=0; ((char *)i)[k]!='\0' || ((char *)j)[k]!='\0'; ++k) {
        if(((char *)i)[k] < ((char *)j)[k])       return -1;
        else if (((char *)i)[k] > ((char *)j)[k]) return 1;
    }
    return 0;
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

void Insert(Array* A, int i, int loc) {
    Array* temp = (Array*) malloc(sizeof(Array));
    temp->array = (int**) malloc(sizeof(int*));
    temp->array[0] = (int*) malloc(sizeof(int));

    if (i-loc>1) {
        for(temp->array[0] = A->array[i] ; i>loc ; --i){
            A->array[i] = A->array[i-1];
        }
        A->array[loc] = temp->array[0];
    } else {
        temp->array[0] = A->array[i];
        A->array[i] = A->array[loc];
        A->array[loc] = temp->array[0];
    }
}

int search(void* item, Array* A, unsigned int i, unsigned int j){
    unsigned int m;
    if (i>=j) {
        if((A->compar)(&A->array[i], item)==-1)                  return i+1;
        else if((A->compar)(&item,&A->array[j])==-1 && j>=0)     return j;
        else                        return i;
    } else {
        m = (i+j)/2;
        if((A->compar)(&A->array[m],item)==0)               return m;
        else {
            if((A->compar)(item,&A->array[m])==-1)            return search(item, A, i, m-1);
            else                    return search(item, A, m+1, j);
        }
    }
}

void BineryInsertionSort(Array* A, unsigned int nitems){
    int i,loc, k;
    for(i=1; i < nitems; ++i) {
        if((A->compar)(&A->array[i], &A->array[i-1]) == -1){
            loc = search(&A->array[i], A, 0, i-1);
            if(loc<0) printf("Errore %d\n",loc);
            else Insert(A,i,loc);
        }
    }
    return;
};

void Merge(Array* A, unsigned int l , unsigned int m, unsigned int r, unsigned int nitems) {
    int i = l,  j = m+1,   k = 0;
    Array *B = ArrayCreate(A->field);
    switch(A->field){
        case 1: 
            B->array = realloc(B->array, sizeof(char*)*(A->nitems +2));
            break;
        case 2: 
            B->array = realloc(B->array, sizeof(int*)*(A->nitems +2));
            break;
        case 3:
            B->array = realloc(B->array, sizeof(float*)*(A->nitems +2));
            break;
        default: break;
    }

    for(;i<=m && j<=r; ++k){
      if ((A->compar)(&A->array[i],&A->array[j]) == -1)   
                       { B->array[k] = A->array[i];  ++i; } 
      else             { B->array[k] = A->array[j];  ++j; }
    }
    for(;i<=m; ++k,++i)  B->array[k] = A->array[i];
    for(;j<=r; ++j,++k)  B->array[k] = A->array[j];
    for(k=l; k<=r; ++k)  A->array[k] = B->array[k-l];

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

void merge_binary_insertion_sort(void *base, size_t nitems, size_t size, size_t k){
    if(k>DISC) 
        MergeSort(base, 0, nitems-1,nitems);
    else        
        BineryInsertionSort(base, nitems); 
    return;
}

void sort_records(const char *infile, const char *outfile, size_t k, size_t field){
    char temp[15], temp_line[20];
    int i, item, j;
    char* token;
    
    Array *A = ArrayCreate(field);
    
    /* records.csv --> 0,noto,233460,32209.073312*/
    /* ordered_array_sample_file.csv --> eeeini,10*/

    FILE *fp = fopen(infile, "r");
    if(fp == NULL) printf("errore");

    for(i=0; i<k; ++i, j=0) {
        fscanf(fp, "%s\n", temp_line);
        token = strtok(temp_line, ",");
//        strcpy(temp, token);
//        token = strtok(NULL, ",");

        item = atoi(token);
        switch(field){
            case 0: arrayAdd(A, temp[0]); break;   // char
            case 1: arrayAdd(A, item); break;   // int 
            case 2: arrayAdd(A, item); break;   // float
        }
    }
    fclose(fp);

    A->array[0] = 10;
    A->array[2] = 4;

    printf("VETTORE: [");
    for(i=0; i<A->nitems; ++i) {
        printf(" %d",A->array[i]); 
        fprintf(fp, "%d\n",A->array[i]);
    }
    printf(" ]\n\n");
    
    merge_binary_insertion_sort(A, A->nitems, A->size, A->nitems);

    printf("ORDINATO: [");
    for(i=0; i<A->nitems; ++i) {
        printf(" %d",A->array[i]); 
        fprintf(fp, "%d\n",A->array[i]);
    }
    printf(" ]\n\n");
    fclose(fp);

    free(A);
}

void stampa(Array *A) {
    int i;
    printf("VETTORE: [");
    for(i=0; i<A->nitems; ++i) 
        printf(" %d",A->array[i]); 
    printf(" ]");
}
void main() {
    sort_records(INPUT_FILE, OUTPUT_FILE, N_RECORDS, 2);
    
}