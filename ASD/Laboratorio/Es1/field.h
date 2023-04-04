// field1: (tipo stringa) contiene parole estratte dalla divina commedia, potete assumere che i valori non contengano spazi o virgole;
// field2: (tipo intero);
// field3: (tipo floating point).

enum FIELD{NONE,FIELD_STRING,FIELD_INT,FIELD_FLOAT}

typedef struct _Array Array;

struct _Array{
  void** array;
  unsigned long nitems;
  unsigned short field;
  int (*compar)(void*,void*);
};

void ArrayCreate(Array* A, size_t field){
    A = (Array*) malloc(sizeof(Array));
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

int CompareInt(const void* i, const void* j){   
    if (*(int *)i < *(int *)j)          return -1;
    else if (*(int *)i == *(int *)j)    return 0;
    else                                return 1;
}

void ArrayAddItem(Array* A, void *item, size_t field) {
    A->nitems = A->nitems + 1;
    switch(A->field){
        case 1: 
            A->array = (char**)realloc(sizeof(char*)*A->nitems);
            (A->array)[i] = (char) *item;
            break;
        case 2: 
            A->array = (int**)realloc(sizeof(int*)*A->nitems);
            (A->array)[i] = (int) *item;
            break;
        case 3:
            A->array = (float**)realloc(sizeof(float*)*A->nitems);
            (A->array)[i] = (float) *item;
            break;
        default: break;
    }
    return;
}