#include "Interfaccia.h"

//--------- PROTOTIPI ---------//

void merge_2(void** base, unsigned int l, unsigned int m, unsigned int r, int (*compar)(const void*, const void*));
void merge_sort_ric_2(void** base, unsigned int i, unsigned int j, unsigned int k, int (*compar)(const void*, const void*));
void merge_sort_2(void** base, unsigned int i, unsigned int j, unsigned int k, int (*compar)(const void*, const void*));
void binary_insertion_sort_2(void** base, unsigned int l, int r, int (*compar)(const void*, const void*));

//------ IMPLEMENTAZIONI ------//

void** temp_2;

void merge_sort_2(void** base, unsigned int i, unsigned int j, unsigned int k, int (*compar)(const void*, const void*))
{
    setbuf(stdout, NULL);
    //    printf("\n");
    if ((temp_2 = (void**)malloc((j + 1) * sizeof(void*))) == NULL)
        ERROR
    merge_sort_ric_2(base, i, j, k, compar);
    free(temp_2);
    //    printf("\n");
}

void merge_2(void** base, unsigned int l, unsigned int m, unsigned int r, int (*compar)(const void*, const void*))
{
    unsigned int i = l, j = m + 1, k = 0;

    while (i <= m && j <= r)
        if (compar(base[i], base[j]) <= 0)
            temp_2[k++] = base[i++];
        else
            temp_2[k++] = base[j++];

    while (i <= m)
        temp_2[k++] = base[i++];
    while (j <= r)
        temp_2[k++] = base[j++];

    for (i = l, k = 0; i <= r; ++i, ++k)
        base[i] = temp_2[k];
}

void merge_sort_ric_2(void** base, unsigned int i, unsigned int j, unsigned int k, int (*compar)(const void*, const void*))
{
    unsigned int m;
    if (i < j) {
        m = (i + j) / 2;
        merge_sort_ric_2(base, i, m, k, compar);
        merge_sort_ric_2(base, m + 1, j, k, compar);

        if (j - i > k)
            merge_2(base, i, m, j, compar);
        else
            binary_insertion_sort_2(base, i, j, compar);
    }
    return;
}

void binary_insertion_sort_2(void** base, unsigned int l, int r, int (*compar)(const void*, const void*))
{
    unsigned int i, loc;
    for (i = l + 1; i < r; ++i)
        if ((compar)(base[i], base[i - 1]) == -1) {
            loc = search(i, base, l, i - 1, compar);
            if (loc < 0)
                ERROR
            else
                insert(base, i, loc, compar);
        }
    return;
}
