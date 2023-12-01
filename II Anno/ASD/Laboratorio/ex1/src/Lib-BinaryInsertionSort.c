#include "Interfaccia.h"

//--------- PROTOTIPI ---------//

void insert(void** base, unsigned int i, unsigned int loc, int (*compar)(const void*, const void*));
int search(unsigned int x, void** base, unsigned int i, unsigned int j, int (*compar)(const void*, const void*));
void binary_insertion_sort(void** base, unsigned int nitems, int (*compar)(const void*, const void*));
void binary_insertion_sort_2(void** base, unsigned int l, int r, int (*compar)(const void*, const void*));

//------ IMPLEMENTAZIONI ------//

void insert(void** base, unsigned int i, unsigned int loc, int (*compar)(const void*, const void*))
{
    void* temp = base[i];
    if (i - loc > 1) {
        for (; i > loc; --i)
            base[i] = base[i - 1];
        base[loc] = temp;
    } else {
        base[i] = base[loc];
        base[loc] = temp;
    }
}

void insertEasy(void** base, unsigned int i, unsigned int loc, int (*compar)(const void*, const void*))
{
    void* temp = base[i];
    for (; i > loc; --i)
        base[i] = base[i - 1];
    base[loc] = temp;
}

// int search2(unsigned int x, void** base, unsigned int i, unsigned int j, int (*compar)(const void *, const void*)) {
//
//     if (i > j) return i;
//
//     unsigned int m = (i + j) / 2;
//
//     if ((compar)(base[x], base[m])==0)         return m;
//     else if ((compar)(base[x], base[m])==-1)   return search(x, base, i, m - 1, compar);
//     else                                       return search(x, base, m + 1, j, compar);
// }

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
    for (unsigned int loc, i = l + 1; i <= r; ++i)
        if ((compar)(base[i], base[i - 1]) == -1)
            if ((loc = search(i, base, l, i - 1, compar)) < 0)
                ERROR
            else
                insertEasy(base, i, loc, compar);
}
