#include <stdio.h>
#include <stdlib.h>

enum { FALSE,
    TRUE };

void Swap(int* A, int i, int j)
{
    if (i != j) {
        int temp = A[i];
        A[i] = A[j];
        A[j] = temp;
    }
}

/** INSERTION SORT
 *
 * @caso_migliore:  O(n)
 * @caso_medio:     O(n^2)
 * @caso_peggiore:  O(n^2)
 */
void Insertion_Sort(int* A, int n)
{
    for (int i = 1; i < n; ++i)
        for (int j = i; A[j - 1] > A[j] && j > 0; --j)
            Swap(A, j - 1, j);
}

/** SELECTION SORT
 *
 * @caso_migliore:  O(n^2)
 * @caso_medio:     O(n^2)
 * @caso_peggiore:  O(n^2)
 */
void Selection_Sort(int* A, int n)
{
    for (int i = 0; i < n - 1; ++i) {
        int min = i;
        for (int j = i + 1; j < n; ++j)
            if (A[min] > A[j])
                min = j;
        Swap(A, i, min);
    }
}

/** QUICK SORT
 *
 * @caso_migliore:  θ(n log n)
 * @caso_medio:     θ(n log n)
 * @caso_peggiore:  θ(n^2)
 */
int Partition(int* A, int piv, int j)
{
    int i = piv + 1;
    while (i <= j) {
        while (A[i] < A[piv])
            ++i;
        while (A[j] > A[piv])
            --j;

        if (i <= j)
            Swap(A, i++, j--);
    }

    Swap(A, piv, j);
    return j;
}

void Quick_Sort(int* A, int i, int j)
{
    if (i < j) {
        int piv = Partition(A, i, j);
        Quick_Sort(A, i, piv - 1);
        Quick_Sort(A, piv + 1, j);
    }
}

/** MERGE SORT
 *
 * @caso_migliore:  O(n log n)
 * @caso_medio:     O(n log n)
 * @caso_peggiore:  O(n log n)
 */
void Merge(int* A, int l, int m, int r)
{
    int i = l, j = m + 1, k = 0;
    int* temp = (int*)malloc(sizeof(int) * (r - l + 1));

    while (i <= m && j <= r)
        if (A[i] < A[j])
            temp[k++] = A[i++];
        else
            temp[k++] = A[j++];

    while (i <= m)
        temp[k++] = A[i++];
    while (j <= r)
        temp[k++] = A[j++];

    while (k > 0)
        A[r--] = temp[--k];
}
void Merge_Sort(int* A, int l, int r)
{
    if (l < r) {
        int m = (r + l) / 2;
        Merge_Sort(A, l, m);
        Merge_Sort(A, m + 1, r);
        Merge(A, l, m, r);
    }
}

/** BUBBLE SORT
 *
 * @caso_migliore:  O(n)
 * @caso_medio:     O(n^2)
 * @caso_peggiore:  O(n^2)
 */
void Bubble_Sort(int* A, int n)
{
    for (int i = n - 1, count = FALSE; i > 0 && !count; --i, count = FALSE) {
        for (int j = 0; j < i; ++j)
            if (A[j] > A[j + 1]) {
                Swap(A, j, j + 1);
                count = TRUE;
            }
    }
}

/** BINARY SERCH
 *
 * @caso_migliore:  O(1)
 * @caso_medio:     O(log n)
 * @caso_peggiore:  O(log n)
 */

int Binary_Search(int x, int* A, int l, int r)
{
    int m = (r + l) / 2;

    if (m > 0){
        if (x == A[m])
            return m;
        else if (x < A[m])
            return Binary_Serch(x, A, l, m - 1);
        else
            return Binary_Serch(x, A, m + 1, r);
    }
    return -1;
}

#define N 10

int main()
{
    int A[N] = { 4, 2, 3, 9, 1, 5, 7, 6, 0, 8 };

    Insertion_Sort(A, N);
    // Selection_Sort(A, N);
    // Quick_Sort(A, 0, N - 1);
    // Merge_Sort(A, 0, N - 1);
    // Bouble_Sort(A, N);

    int check = FALSE;
    for (int i = 0; i < 10; ++i) {
        if (A[i] != i)
            check = TRUE;
        printf("%d ", A[i]);
    }
    if (check)
        printf("\t[FAIL]\n");
    else {
        printf("\t[OK]\n");

        printf("Search: ");
        if (Binary_Search(8, A, 0, N - 1) == -1)
            printf("\t[FAIL]\n");
        else
            printf("\t[OK]\n");
    }
    return 0;
}
