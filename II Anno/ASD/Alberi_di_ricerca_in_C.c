#include "lib_alberi_in_c.h"

char* ricSearch(int x, serBinTree* T)
{
    if (T == NULL)
        return "FALSE";
    else {
        if (x == T->key)
            return "TRUE";
        else if (x < T->key)
            return ricSearch(x, T->left);
        else
            return ricSearch(x, T->right);
    }
}

char* itSearch(int x, serBinTree* T)
{
    while (T != NULL && x != T->key) {
        if (x < T->key)
            T = T->left;
        else
            T = T->right;
    }

    if (T == NULL)
        return "FALSE";
    return "TRUE";
}

void printInOrder(serBinTree* T)
{
    if (T == NULL)
        return;
    printInOrder(T->left);
    printf(" %d", T->key);
    printInOrder(T->right);
}

serBinTree* minBinTree(serBinTree* T)
{
    if (T->left == NULL)
        return T;
    minBinTree(T->left);
}

serBinTree* maxBinTree(serBinTree* T)
{
    if (T->right == NULL)
        return T;
    maxBinTree(T->right);
}

serBinTree* succBinTree(serBinTree* T) // NON FUNZIONA ANCORA
{
    if (T->right != NULL)
        return minBinTree(T->right);
    else {
        serBinTree* P = T->parent;
        while (P != NULL && T == P->right) {
            T = P;
            P = T->right;
        }
        return P;
    }
}

int main()
{
    serBinTree A, B, C, D, E, F, G, H;
    initSerBinTree(&A, &B, &C, &D, &E, &F, &G, &H);

    printf("\n");
    printf("      12       |        A      \n");
    printf("     / \\       |       / \\     \n");
    printf("    7   15     |      B   C    \n");
    printf("   / \\    \\    |     / \\   \\   \n");
    printf("  3  10   18   |    D   E   F  \n");
    printf("    /    /     |      /    /   \n");
    printf("   9    16     |     G    H    \n");
    printf("\n");
    printf(" ricSearch(%d): %s\t", 10, ricSearch(10, &A));
    printf(" ricSearch(%d): %s\n", 20, ricSearch(20, &A));
    printf(" itSearch(%d):  %s\t", 10, itSearch(10, &A));
    printf(" itSearch(%d):  %s\n", 20, itSearch(20, &A));
    printf("\n");
    printf(" InOrder: [");
    printInOrder(&A);
    printf(" ]\n");
    printf("\n");
    printf(" Min: %d\n", (minBinTree(&A))->key);
    printf(" Max: %d\n", (maxBinTree(&A))->key);
    printf("\n");
    printf(" Succ(%d): %d [NON FUNZIONA ANCORA]\n", E.key, (succBinTree(&E))->key);
}
