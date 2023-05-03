#include "Interfaccia.h"

//--------- PROTOTIPI ---------//

void Insert(void** base, unsigned int i, unsigned int loc, int (*compar)(const void *, const void*));
int search(unsigned int x, void** base, unsigned int i, unsigned int j, int (*compar)(const void *, const void*));
void BinaryInsertionSort(void** base,  unsigned int nitems, int (*compar)(const void *, const void*));

//------ IMPLEMENTAZIONI ------//

void Insert(void** base, unsigned int i, unsigned int loc, int (*compar)(const void *, const void*)) {
    void* temp = base[i];
    if (i-loc>1) {
        for(; i>loc ; --i){
            base[i] = base[i-1];
        }
        base[loc] = temp;
    } else {
        base[i] = base[loc];
        base[loc] = temp;
    }
}

//int search2(unsigned int x, void** base, unsigned int i, unsigned int j, int (*compar)(const void *, const void*)) {
//    
//    if (i > j) return i;
//    
//    unsigned int m = (i + j) / 2;
//
//    if ((compar)(base[x], base[m])==0)         return m;
//    else if ((compar)(base[x], base[m])==-1)   return search(x, base, i, m - 1, compar);
//    else                                       return search(x, base, m + 1, j, compar);
//}


int search(unsigned int x, void** base, unsigned int i, unsigned int j, int (*compar)(const void *, const void*)) {
    int left = i, right = j;
    while (left <= right) {
        int mid = left + (right - left) / 2;
        if ((compar)(base[x], base[mid])==0)
            return mid;
        else if ((compar)(base[x], base[mid])==1)
            left = mid + 1;
        else
            right = mid - 1;
    }

    return left;

}

void BinaryInsertionSort(void** base,  unsigned int nitems, int (*compar)(const void *, const void*)){
    unsigned int i,loc;
    for(i=1; i < nitems; ++i) {
        if((compar)(base[i], base[i-1]) <= 0){
            loc = search(i, base, 0, i-1, compar);
            if(loc<0) printf("Errore %d\n",loc);
            else Insert(base,i,loc,compar);
        }
    }
    return;
}


//[i.......m........j..x..............]