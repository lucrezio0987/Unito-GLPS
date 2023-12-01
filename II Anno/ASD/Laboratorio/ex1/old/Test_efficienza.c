#include <time.h>

#include "Interfaccia.h"

#define INPUT_FILE "../records.csv"
#define OUTPUT_FILE "k_medi.csv"

#define MAX_REC 100
#define N_CONTR 1000

typedef struct _Clock {
    int start;
    int end;
    int time;
} Clock;

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

int controllo(Array* A, Array* B, int n_records);
int calc_dif(unsigned int n_records, unsigned int field);

void main(int argc, const char* argv[])
{
    int field = 2;
    int min = 1;
    int max_rec = MAX_REC;
    int max;
    int n_records = 0;
    int time;
    int flag_trovato = 0;
    int i;
    int media_k = 0;
    int min_k = max_rec;
    int max_k = 0;
    int k_null = 0;
    int n_controlli = N_CONTR;
    FILE* output = fopen(OUTPUT_FILE, "w+");

    for (i = 0; i < n_controlli; ++i) {
        min = 1;
        max = max_rec;
        while (flag_trovato == 0) {
            n_records = (min + max) / 2;
            printf(":::: test on: %d\n", n_records);
            if (n_records == 0) {
                ++flag_trovato;
                ++k_null;
            } else
                time = calc_dif(n_records, field);

            if (time == 0 || min >= max)
                ++flag_trovato;
            else if (time < 0)
                min = n_records + 1;
            else if (time > 0)
                max = n_records - 1;
        }
        if (n_records != 0) {
            printf("================================ (TEST: %d) k = %d\n", i, n_records);
            fprintf(output, "%d %d\n", i, n_records);
        }
        --flag_trovato;
        if (min_k > n_records && n_records != 0)
            min_k = n_records;
        if (max_k < n_records)
            max_k = n_records;
        media_k += n_records;
    }

    media_k = media_k / (n_controlli - k_null);

    fclose(output);

    printf("TEST_EFFICIENZA: TERMINATO\n");
    printf("  [max_records:%d, n_controlli: %d]\n", max_rec, n_controlli);
    printf("  >> K_MEDIO: %d  (K_min: %d, K_max: %d)\n", media_k, min_k, max_k);
}

int calc_dif(unsigned int n_records, unsigned int field)
{
    int i;
    Clock ms, is;

    Array* A = create_array();
    Array* B = create_array();

    load_array_max(A, INPUT_FILE, n_records);
    load_array_max(B, INPUT_FILE, n_records);

    // printf(":: (I) Merge - ");
    ms.start = clock();
    switch (field) {
    case 1:
        merge_sort(A->base, 0, A->nitems - 1, compare_string);
        break;
    case 2:
        merge_sort(A->base, 0, A->nitems - 1, compare_int);
        break;
    case 3:
        merge_sort(A->base, 0, A->nitems - 1, compare_float);
        break;
    default:
        merge_sort(A->base, 0, A->nitems - 1, compare_pos);
        break;
    }
    ms.end = clock();
    ms.time = ms.end - ms.start;

    // printf("(F) Merge (%d)\n:: (I) Binaryinsertion - ", ms.time);
    is.start = clock();
    switch (field) {
    case 1:
        binary_insertion_sort(B->base, B->nitems, compare_string);
        break;
    case 2:
        binary_insertion_sort(B->base, B->nitems, compare_int);
        break;
    case 3:
        binary_insertion_sort(B->base, B->nitems, compare_float);
        break;
    default:
        binary_insertion_sort(B->base, B->nitems, compare_pos);
        break;
    }
    is.end = clock();
    is.time = is.end - is.start;

    // printf("(F) Binaryinsertion (%d)\n", is.time);

    if (controllo(A, B, n_records) != 0)
        printf(":: ERRORE: vettori non uguali\n");
    // printf("\n");

    free(A);
    free(B);

    return is.time - ms.time;
}

int controllo(Array* A, Array* B, int n_records)
{
    int i;

    // printf("\n%d - %d: %d", A->nitems, B->nitems, n_records);

    if (A->nitems != B->nitems || A->nitems != n_records)
        return -2;

    for (i = 0; i < A->nitems; ++i) {
        if (A->base[i]->pos != B->base[i]->pos)
            return -1;
    }

    return 0;
}
