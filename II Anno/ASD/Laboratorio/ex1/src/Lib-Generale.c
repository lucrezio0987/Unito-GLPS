#include "Interfaccia.h"

#define MAX_REC 20000000

//--------- PROTOTIPI ---------//

int ComparePos(Records* i, Records* j);
int CompareInt(Records* i, Records* j);
int CompareFloat(Records* i, Records* j);
int CompareString(Records* i, Records* j);

Records** CreateArray();
void arrayAdd(Array* A, Records* rec);
void LoadArray(Array* A, const char* infile);
void LoadArrayMAX(Array* A, const char* infile, unsigned int max_records);
void PrintArray(const char* outfile, Array* A);

void merge_binary_insertion_sort(void** base, size_t nitems, size_t k, int (*compar)(const void*, const void*));
void sort_records(const char* infile, const char* outfile, size_t k, size_t field);
void sort_recordsMAX(const char* infile, const char* outfile, size_t k, size_t field);

//--------- STRUTTURE ---------//

struct _Record {
    long int pos;
    long int item_int;
    double item_float;
    char* item_string;
};

struct _Array {
    Records** base;
    unsigned int nitems;
};

//------ IMPLEMENTAZIONI ------//

int ComparePos(Records* i, Records* j)
{
    if (i->pos < j->pos)
        return -1;
    else if (i->pos == j->pos)
        return 0;
    else
        return 1;
}

int CompareInt(Records* i, Records* j)
{
    if (i->item_int < j->item_int)
        return -1;
    else if (i->item_int == j->item_int)
        return 0;
    else
        return 1;
}

int CompareFloat(Records* i, Records* j)
{
    if (i->item_float < j->item_float)
        return -1;
    else if (i->item_float == j->item_float)
        return 0;
    else
        return 1;
}
int CompareString(Records* i, Records* j)
{
    int res = strcmp(i->item_string, j->item_string);
    if (res < 0)
        return -1;
    else if (res == 0)
        return 0;
    else
        return 1;
    return 0;
}

void merge_binary_insertion_sort(void** base, size_t nitems, size_t k, int (*compar)(const void*, const void*))
{
    if (nitems > k)
        MergeSort(base, 0, nitems - 1, compar);
    else
        BinaryInsertionSort(base, nitems, compar);
}

Records** CreateArray()
{
    Array* A;
    if ((A = (Array*)malloc(sizeof(Array))) == NULL)
        ERROR
    if ((A->base = (Records**)malloc(sizeof(Records*))) == NULL)
        ERROR
    A->nitems = 0;
    return A;
}

void arrayAdd(Array* A, Records* rec)
{
    if (A->nitems % 100 == 0)
        if ((A->base = (Records**)realloc(A->base, sizeof(Records*) * (A->nitems + 100))) == NULL)
            ERROR
    if ((A->base[A->nitems] = (Records*)malloc(sizeof(Records))) == NULL)
        ERROR
    if ((A->base[A->nitems]->item_string = malloc(sizeof(char) * strlen(rec->item_string))) == NULL)
        ERROR

    A->base[A->nitems]->pos = rec->pos;
    A->base[A->nitems]->item_int = rec->item_int;
    A->base[A->nitems]->item_float = rec->item_float;
    strcpy(A->base[A->nitems]->item_string, rec->item_string);

    A->nitems++;
}

void LoadArray(Array* A, const char* infile)
{
    FILE* fp;
    Records* rec;

    if ((rec = (Records*)malloc(sizeof(Records))) == NULL)
        ERROR
    if ((rec->item_string = (char*)malloc(sizeof(char) * 100)) == NULL)
        ERROR

    if ((fp = fopen(infile, "r")) == NULL)
        ERROR

    while ((fscanf(fp, "%ld,%[^,],%ld,%lf\n", &rec->pos, rec->item_string, &rec->item_int, &rec->item_float)) == 4)
        arrayAdd(A, rec);

    fclose(fp);

    free(rec->item_string);
    free(rec);
}

void LoadArrayMAX(Array* A, const char* infile, unsigned int max_records)
{
    unsigned int i = 0;
    FILE* fp;
    Records* rec;

    if ((rec = (Records*)malloc(sizeof(Records))) == NULL)
        ERROR
    if ((rec->item_string = (char*)malloc(sizeof(char) * 100)) == NULL)
        ERROR

    if ((fp = fopen(infile, "r")) == NULL)
        ERROR

    while ((fscanf(fp, "%ld,%[^,],%ld,%lf\n", &rec->pos, rec->item_string, &rec->item_int, &rec->item_float)) == 4 && i++ != max_records)
        arrayAdd(A, rec);
    fclose(fp);

    free(rec->item_string);
    free(rec);
}

void PrintArray(const char* outfile, Array* A)
{
    unsigned int i;
    FILE* fp;
    if ((fp = fopen(outfile, "w+")) == NULL)
        ERROR
    for (i = 0; i < A->nitems; ++i)
        fprintf(fp, "%d,%s,%d,%f\n", A->base[i]->pos, A->base[i]->item_string, A->base[i]->item_int, A->base[i]->item_float);
    fclose(fp);
}

void sort_recordsMAX(const char* infile, const char* outfile, size_t k, size_t field)
{
    printf("Inizio Caricamento\n");
    Array* A = CreateArray();
    LoadArrayMAX(A, infile, MAX_REC);
    printf("Fine Caricamento - Inizio Ordinamento\n");

    switch (field) {
    case 1:
        merge_binary_insertion_sort(A->base, A->nitems, k, CompareString);
        break;
    case 2:
        merge_binary_insertion_sort(A->base, A->nitems, k, CompareInt);
        break;
    case 3:
        merge_binary_insertion_sort(A->base, A->nitems, k, CompareFloat);
        break;
    default:
        merge_binary_insertion_sort(A->base, A->nitems, k, ComparePos);
        break;
    }

    printf("Fine Ordinamento - Inizio Stampa\n");

    PrintArray(outfile, A);

    printf("Fine Stampa\n");

    free(A);
    return;
}

void sort_records(const char* infile, const char* outfile, size_t k, size_t field)
{
    Array* A = CreateArray();
    LoadArray(A, infile);

    switch (field) {
    case 1:
        merge_binary_insertion_sort(A->base, A->nitems, k, CompareString);
        break;
    case 2:
        merge_binary_insertion_sort(A->base, A->nitems, k, CompareInt);
        break;
    case 3:
        merge_binary_insertion_sort(A->base, A->nitems, k, CompareFloat);
        break;
    default:
        merge_binary_insertion_sort(A->base, A->nitems, k, ComparePos);
        break;
    }

    PrintArray(outfile, A);

    free(A);
    return;
}
