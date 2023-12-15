#include "Interfaccia.h"

//--------- PROTOTIPI ---------//

void merge(void** base, unsigned int l, unsigned int m, unsigned int r, int (*compar)(const void*, const void*));
void merge_sort(void** base, unsigned int i, unsigned int j, unsigned int k, int (*compar)(const void*, const void*));
void merge_binary_insertion_sort(void** base, size_t nitems, size_t k, int (*compar)(const void*, const void*));
void binary_insertion_sort(void** base, unsigned int l, int r, int (*compar)(const void*, const void*));
int search(unsigned int x, void** base, unsigned int i, unsigned int j, int (*compar)(const void*, const void*));

//-------- GLOBAL VAR --------//

void** temp_merge;

//------ IMPLEMENTATION ------//

void merge_binary_insertion_sort(void** base, size_t nitems, size_t k, int (*compar)(const void*, const void*))
{
    struct timespec ts;
    clock_gettime(CLOCK_THREAD_CPUTIME_ID, &ts);

    if ((temp_merge = (void**)malloc(nitems * sizeof(void*))) == NULL)
        ERROR
    int start = clock();

    merge_sort(base, 0, nitems - 1, k, compar);

    printf("%f s\n", ((double)(clock() - start)) / CLOCKS_PER_SEC);
    free(temp_merge);
}

void binary_insertion_sort(void** base, unsigned int l, int r, int (*compar)(const void*, const void*))
{

    int loc, j, i;
    void* temp;

    for (i = 1; i < r - l; ++i) {
        loc = search(i, base, 0, i - 1, compar);
        for (j = i - 1, temp = base[j]; j >= loc; --j)
            base[j] = base[j - 1];
        base[loc] = temp;
    }
}

int search(unsigned int x, void** base, unsigned int i, unsigned int j, int (*compar)(const void*, const void*))
{
    int l = i, r = j, m;

    while (l <= r) {
        m = (r + l) / 2;
        switch ((compar)(base[x], base[m])) {
        case -1:
            r = m - 1;
            break;
        case 1:
            l = m + 1;
            break;
        default:
            return m;
        }
    }

    return l;
}

void merge_sort(void** base, unsigned int i, unsigned int j, unsigned int k, int (*compar)(const void*, const void*))
{
    unsigned int m;
    if (i < j) {
        m = (i + j) / 2;
        merge_sort(base, i, m, k, compar);
        merge_sort(base, m + 1, j, k, compar);

        if (j - i > k)
            merge(base, i, m, j, compar);
        else
            binary_insertion_sort(base, i, j, compar);
    }
    return;
}

void merge(void** base, unsigned int l, unsigned int m, unsigned int r, int (*compar)(const void*, const void*))
{
    unsigned int i = l, j = m + 1, k = 0;

    while (i <= m && j <= r)
        if (compar(base[i], base[j]) <= 0)
            temp_merge[k++] = base[i++];
        else
            temp_merge[k++] = base[j++];

    while (i <= m)
        temp_merge[k++] = base[i++];
    while (j <= r)
        temp_merge[k++] = base[j++];

    for (i = l, k = 0; i <= r; ++i, ++k)
        base[i] = temp_merge[k];
}
