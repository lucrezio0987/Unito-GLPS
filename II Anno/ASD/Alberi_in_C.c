#include "lib_alberi_in_c.h"

int kTreeCard(kTree T)
{
    if (T.child == NULL && T.sibling == NULL) // T == NUL -> return 0;
        return 1;

    int card = 1;
    kTree* tmp = T.child;

    while (tmp != NULL) {
        card += kTreeCard(*tmp);
        tmp = tmp->sibling;
    }
    return card;
}

int kTreeHight(kTree T)
{
    if (T.child == NULL && T.sibling == NULL) // T == NUL -> return 0;
        return 1;

    int hight = 0;
    kTree* tmp = T.child;

    while (tmp != NULL) {
        hight = max(hight, kTreeHight(*tmp));
        tmp = tmp->sibling;
    }
    return hight + 1;
}

List* DFS_SX(List* l, kTree T) // DA CORREGGERE
{
    Stack* myStack = newStack();
    push(myStack, T);

    while (!isStackEmpty(myStack)) {
        kTree tmp = pop(myStack);
        l = add(l, tmp.key);

        kTree* tmp2 = tmp.child;
        while (tmp2 != NULL) {
            push(myStack, *tmp2);
            tmp2 = tmp2->child; // <--
        }
    }

    return l;
}
List* DFS_DX(List* l, kTree T)
{
    Stack* myStack = newStack();
    push(myStack, T);

    while (!isStackEmpty(myStack)) {
        kTree tmp = pop(myStack);
        l = add(l, tmp.key);

        kTree* tmp2 = tmp.child;
        while (tmp2 != NULL) {
            push(myStack, *tmp2);
            tmp2 = tmp2->sibling; // <--
        }
    }

    return l;
}

List* BFS_DX(List* l, kTree T) // DA CORREGGERE
{
    Queue* myQueue = createQueue();
    enqueue(myQueue, T);

    while (!isQueueEmpty(myQueue)) {
        kTree tmp = dequeue(myQueue);
        l = l = add(l, tmp.key);

        kTree* tmp2 = tmp.child;
        while (tmp2 != NULL) {
            enqueue(myQueue, *tmp2);
            tmp2 = tmp2->child; // <--
        }
    }

    return l;
}

List* BFS_SX(List* l, kTree T)
{
    Queue* myQueue = createQueue();
    enqueue(myQueue, T);

    while (!isQueueEmpty(myQueue)) {
        kTree tmp = dequeue(myQueue);
        l = l = add(l, tmp.key);

        kTree* tmp2 = tmp.child;
        while (tmp2 != NULL) {
            enqueue(myQueue, *tmp2);
            tmp2 = tmp2->sibling; // <--
        }
    }

    return l;
}

void main()
{
    kTree A, B, C, D, E, F, G;
    initKTree(&A, &B, &C, &D, &E, &F, &G);

    printf("\n");
    printf("        A           |       A           \n");
    printf("      / | \\         |       |           \n");
    printf("     B  C  D        |       B - C - D   \n");
    printf("    / \\    |        |       |       |   \n");
    printf("    E  F   G        |       E - F   G   \n");
    printf("\n");
    printf(" Card:\t%d\n", kTreeCard(A));
    printf(" Hight:\t%d\n", kTreeHight(A));
    printf("\n");
    printf(" DFS_DX: %s\n", printListToString(DFS_DX(NULL, A)));
    // printf(" DFS_SX: %s - [Non funziona]\n", printListToString(DFS_SX(NULL, A)));
    // printf(" BFS_DX: %s - [Non funziona]\n", printListToString(BFS_DX(NULL, A)));
    printf(" BFS_SX: %s\n", printListToString(BFS_SX(NULL, A)));
    printf("\n");
}
