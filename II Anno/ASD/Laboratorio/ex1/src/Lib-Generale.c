/**
 * @file Lib-Generale.c
 * @author Lucrezio Del Ponte, Simone Bergesio, Mario Corrao
 */

#include "Interfaccia.h"
#include <time.h>

//--------- PROTOTYPES ---------//

int compare_pos(Records* i, Records* j);
int compare_int(Records* i, Records* j);
int compare_float(Records* i, Records* j);
int compare_string(Records* i, Records* j);

Records** create_array();
void array_add(Array* A, Records* rec);
void load_array(Array* A, const char* infile);
void load_array_max(Array* A, const char* infile, unsigned int max_records);
void print_array(const char* outfile, Array* A);

void sort_records(const char* infile, const char* outfile, size_t k, size_t field);
void sort_records_max(const char* infile, const char* outfile, size_t k, size_t field, size_t max_records);

//--------- STRUCTURES ---------//

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

//------ IMPLEMENTATION ------//

int compare_pos(Records* i, Records* j)
{
    if (i->pos < j->pos)
        return -1;
    else if (i->pos == j->pos)
        return 0;
    else
        return 1;
}

int compare_int(Records* i, Records* j)
{
    if (i->item_int < j->item_int)
        return -1;
    else if (i->item_int == j->item_int)
        return 0;
    else
        return 1;
}

int compare_float(Records* i, Records* j)
{
    if (i->item_float < j->item_float)
        return -1;
    else if (i->item_float == j->item_float)
        return 0;
    else
        return 1;
}
int compare_string(Records* i, Records* j)
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

Records** create_array()
{
    Array* A;
    if ((A = (Array*)malloc(sizeof(Array))) == NULL)
        ERROR
    if ((A->base = (Records**)malloc(sizeof(Records*))) == NULL)
        ERROR
    A->nitems = 0;
    return A;
}

void array_add(Array* A, Records* rec)
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

void load_array(Array* A, const char* infile)
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
        array_add(A, rec);

    fclose(fp);

    free(rec->item_string);
    free(rec);
}

void load_array_max(Array* A, const char* infile, unsigned int max_records)
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
        array_add(A, rec);
    fclose(fp);

    free(rec->item_string);
    free(rec);
}

void print_array(const char* outfile, Array* A)
{
    unsigned int i;
    FILE* fp;
    if ((fp = fopen(outfile, "w+")) == NULL)
        ERROR
    for (i = 0; i < A->nitems; ++i)
        fprintf(fp, "%d,%s,%d,%f\n", A->base[i]->pos, A->base[i]->item_string, A->base[i]->item_int, A->base[i]->item_float);
    fclose(fp);
}

void init()
{
    setbuf(stdout, NULL);
    struct timespec ts;
    clock_gettime(CLOCK_THREAD_CPUTIME_ID, &ts);
}

void sort_records_max(const char* infile, const char* outfile, size_t k, size_t field, size_t max_records)
{
    setbuf(stdout, NULL);

    // printf(" >> START (max_records: %d)\n", max_records);

    printf("\033[0;34m|  T_loading: \033[0;30m");
    int start = clock();
    Array* A = create_array();
    load_array_max(A, infile, max_records);
    printf("%f s\n", ((double)(clock() - start)) / CLOCKS_PER_SEC);

    printf("\033[0;34m|  T_Sorting: \033[1;30m");
    switch (field) {
    case 1:
        merge_binary_insertion_sort(A->base, A->nitems, k, compare_string);
        break;
    case 2:
        merge_binary_insertion_sort(A->base, A->nitems, k, compare_int);
        break;
    case 3:
        merge_binary_insertion_sort(A->base, A->nitems, k, compare_float);
        break;
    default:
        merge_binary_insertion_sort(A->base, A->nitems, k, compare_pos);
        break;
    }

    printf("\033[0;34m|  T_printing: \033[0;30m");
    start = clock();
    print_array(outfile, A);
    printf("%f s\n", ((double)(clock() - start)) / CLOCKS_PER_SEC);

    free(A);
    return;
}

void sort_records(const char* infile, const char* outfile, size_t k, size_t field)
{
    init();

    printf("\033[0;34m|  T_loading: \033[0;30m");
    int start = clock();
    Array* A = create_array();
    load_array(A, infile);
    printf("%f s\n", ((double)(clock() - start)) / CLOCKS_PER_SEC);

    printf("\033[0;34m|  T_Sorting: \033[1;30m");
    switch (field) {
    case 1:
        merge_binary_insertion_sort(A->base, A->nitems, k, compare_string);
        break;
    case 2:
        merge_binary_insertion_sort(A->base, A->nitems, k, compare_int);
        break;
    case 3:
        merge_binary_insertion_sort(A->base, A->nitems, k, compare_float);
        break;
    default:
        merge_binary_insertion_sort(A->base, A->nitems, k, compare_pos);
        break;
    }

    printf("\033[0;34m|  T_printing: \033[0;30m");
    start = clock();
    print_array(outfile, A);
    printf("%f s\n", ((double)(clock() - start)) / CLOCKS_PER_SEC);

    free(A);
    return;
}
