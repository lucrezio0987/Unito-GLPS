#include "Interfaccia.h"

//--------- PROTOTIPI ---------//

void insert(void** base, unsigned int i, unsigned int loc, int (*compar)(const void*, const void*));
int search(unsigned int x, void** base, unsigned int i, unsigned int j, int (*compar)(const void*, const void*));
int searchRec(unsigned int x, void** base, unsigned int i, unsigned int j, int (*compar)(const void*, const void*));
void binary_insertion_sort(void** base, unsigned int nitems, int (*compar)(const void*, const void*));
void binary_insertion_sort_2(void** base, unsigned int l, int r, int (*compar)(const void*, const void*));

//------ IMPLEMENTAZIONI ------//

void insert(void** base, unsigned int i, unsigned int loc, int (*compar)(const void*, const void*))
{
    void* temp = base[i];
    for (; i > loc; --i)
        base[i] = base[i - 1];
    base[loc] = temp;
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

// Non usata
int searchRec(unsigned int x, void** base, unsigned int i, unsigned int j, int (*compar)(const void*, const void*))
{
    int l = i, r = j;
    if (l > r)
        return i;

    int m = (l + r) / 2;

    switch ((compar)(base[x], base[m])) {
    case -1:
        return searchRec(x, base, l, m - 1, compar);
    case 1:
        return searchRec(x, base, m + 1, r, compar);
    default:
        return m;
    }
}

// Non più utilizzata
void binary_insertion_sort(void** base, unsigned int nitems, int (*compar)(const void*, const void*))
{
    for (unsigned int loc, i = 1; i < nitems; ++i)
        if ((compar)(base[i], base[i - 1]) == -1)
            if ((loc = search(i, base, 0, i - 1, compar)) < 0)
                ERROR
            else
                insert(base, i, loc, compar);
}

void binary_insertion_sort_2(void** base, unsigned int l, int r, int (*compar)(const void*, const void*))
{
    int loc, j, i;
    void* temp;

    for (i = 1; i < r - l; ++i) {
        loc = search(i, base, 0, i - 1, compar);
        for (temp = base[i--], j = i - 1; j >= loc; --j)
            base[j] = base[j - 1];
        base[loc] = temp;
    }
}
