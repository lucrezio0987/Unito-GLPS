#include "Interfaccia.h"
//--------- PROTOTIPI ---------//

void Merge(void **base, unsigned int l , unsigned int m, unsigned int r, int (*compar)(const void *, const void*));
void MergeSort(void **base, unsigned int i, unsigned int j, int (*compar)(const void *, const void*));

//------ IMPLEMENTAZIONI ------//

void Merge(void **base, unsigned int l , unsigned int m, unsigned int r, int (*compar)(const void *, const void*)) {
    unsigned int i = l,  j = m+1,   k = 0;
    void **temp;

    temp = (void **) malloc((r-l+1)*sizeof(void*));

    while (i <= m && j <= r) 
        if (compar(base[i], base[j]) <= 0) temp[k++] = base[i++];
        else temp[k++] = base[j++];

    while (i <= m) temp[k++] = base[i++];
    while (j <= r) temp[k++] = base[j++];

    for (i = l, k = 0; i <= r; ++i, ++k) base[i] = temp[k];

    free(temp);
}

void MergeSort(void **base, unsigned int i, unsigned int j, int (*compar)(const void *, const void*)) {
    unsigned int m;
    if(i<j) {
        m = (i+j)/2;
        MergeSort(base,i,m,compar);
        MergeSort(base,m+1,j,compar);
        Merge(base,i,m,j,compar);
    }
    return;
}