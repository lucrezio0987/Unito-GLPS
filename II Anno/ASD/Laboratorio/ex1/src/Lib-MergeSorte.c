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

    // In temp vengono inseiriti in maniera ordinata gli elementi di DX e SX finche uno dei due non finisce
    while (i <= m && j <= r)
        if (compar(base[i], base[j]) <= 0)
            temp[k++] = base[i++];
        else
            temp[k++] = base[j++];

    // Si termina riempiendo temp con gli elementi rimanenti del vettore più lungo
    // (quindi verrà eseguito solo uno dei due cicli)
    while (i <= m)
        temp[k++] = base[i++];
    while (j <= r)
        temp[k++] = base[j++];

    // le due parti del vettore da ordinare vengono sovrascritte con il contenuto di temp
    // (ovvero la loro versione "mischiata")
    for (i = l, k = 0; i <= r; ++i, ++k)
        base[i] = temp[k];
}

void merge_sort_ric(void** base, unsigned int i, unsigned int j, int (*compar)(const void*, const void*))
{
    unsigned int m;
    if (i < j) {
        m = (i + j) / 2;

        // il vettore viene diviso fino alla sua componente unitaria in base al paradigma DeI
        // (perche un vettore di un elemento è sempre ordinato (ovviamente) )
        merge_sort_ric(base, i, m, compar);
        merge_sort_ric(base, m + 1, j, compar);

        // viene chiamata la funzione di Merge su tutti i sottovettori ordinati
        merge(base, i, m, j, compar);
    }
    return;
}
