#include "Interfaccia.h"
//--------- PROTOTIPI ---------//

void merge(void** base, unsigned int l, unsigned int m, unsigned int r, int (*compar)(const void*, const void*));
void merge_sort_ric(void** base, unsigned int i, unsigned int j, int (*compar)(const void*, const void*));
void merge_sort(void** base, unsigned int i, unsigned int j, int (*compar)(const void*, const void*));

//------ IMPLEMENTAZIONI ------//

void** temp;

void merge_sort(void** base, unsigned int i, unsigned int j, int (*compar)(const void*, const void*))
{
    if ((temp = (void**)malloc((j + 1) * sizeof(void*))) == NULL)
        ERROR
    merge_sort_ric(base, i, j, compar);
    free(temp);
}

void merge(void** base, unsigned int l, unsigned int m, unsigned int r, int (*compar)(const void*, const void*))
{
    unsigned int i = l, j = m + 1, k = 0;

    while (i <= m && j <= r)
        if (compar(base[i], base[j]) <= 0)
            temp[k++] = base[i++];
        else
            temp[k++] = base[j++];

    while (i <= m)
        temp[k++] = base[i++];
    while (j <= r)
        temp[k++] = base[j++];

    for (i = l, k = 0; i <= r; ++i, ++k)
        base[i] = temp[k];
}

void merge_sort_ric(void** base, unsigned int i, unsigned int j, int (*compar)(const void*, const void*))
{
    unsigned int m;
    if (i < j) {
        m = (i + j) / 2;
        merge_sort_ric(base, i, m, compar);
        merge_sort_ric(base, m + 1, j, compar);
        merge(base, i, m, j, compar);
    }
    return;
}
