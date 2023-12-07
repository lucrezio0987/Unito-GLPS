#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct _kTree {
    char key;
    struct _kTree* child;
    struct _kTree* sibling;
} kTree;

typedef struct _List {
    char key;
    struct _List* next;
} List;

// Definizione del nodo della pila
typedef struct StackNode {
    kTree value;
    struct StackNode* next;
} StackNode;

// Definizione della struttura pila
typedef struct {
    StackNode* top;
} Stack;

typedef struct Node {
    kTree value; // Cambia il tipo di dato da int a kTree
    struct Node* next;
} Node;

// Struttura per rappresentare la coda
typedef struct Queue {
    Node* front; // Puntatore al fronte della coda
    Node* rear; // Puntatore alla fine della coda
} Queue;

// Funzione per creare una nuova coda
Queue* createQueue()
{
    Queue* queue = (Queue*)malloc(sizeof(Queue));
    if (queue == NULL) {
        fprintf(stderr, "Errore nell'allocazione di memoria per la coda\n");
        exit(EXIT_FAILURE);
    }
    queue->front = queue->rear = NULL;
    return queue;
}

// Funzione per verificare se la coda è vuota
int isQueueEmpty(Queue* queue)
{
    return (queue->front == NULL);
}
// Funzione per inserire un elemento di tipo kTree in coda
void enqueue(Queue* queue, kTree value)
{
    // Crea un nuovo nodo
    Node* newNode = (Node*)malloc(sizeof(Node));
    if (newNode == NULL) {
        fprintf(stderr, "Errore nell'allocazione di memoria per il nuovo nodo\n");
        exit(EXIT_FAILURE);
    }
    newNode->value = value;
    newNode->next = NULL;

    // Se la coda è vuota, il nuovo nodo diventa sia il fronte che la fine della coda
    if (isQueueEmpty(queue)) {
        queue->front = queue->rear = newNode;
    } else {
        // Altrimenti, collega il nuovo nodo alla fine della coda e aggiorna il puntatore della fine
        queue->rear->next = newNode;
        queue->rear = newNode;
    }
}
// Funzione per rimuovere un elemento dalla testa della coda
kTree dequeue(Queue* queue)
{
    // Controlla se la coda è vuota
    if (isQueueEmpty(queue)) {
        fprintf(stderr, "La coda è vuota, impossibile eseguire dequeue\n");
        exit(EXIT_FAILURE);
    }

    // Ottieni il dato dal nodo nel fronte della coda
    kTree data = queue->front->value;

    // Salva il puntatore al fronte attuale
    Node* temp = queue->front;

    // Sposta il puntatore del fronte al prossimo nodo
    queue->front = queue->front->next;

    // Libera la memoria del vecchio fronte
    free(temp);

    // Restituisci il dato rimosso
    return data;
}

// Funzione per stampare la coda contenente elementi di tipo kTree
void printQueue(Queue* queue)
{
    Node* current = queue->front;
    printf("Coda: ");
    while (current != NULL) {
        printf("%c ", current->value.key); // Modifica la stampa per il tipo kTree
        current = current->next;
    }
    printf("\n");
}

int max(int a, int b)
{
    if (a > b)
        return a;
    return b;
}

// Method to create a new node
List* new(char key)
{
    List* newNode = (List*)malloc(sizeof(List));

    newNode->key = key;
    newNode->next = NULL;

    return newNode;
}

// Method to add a node at the beginning of the list
List* add(List* head, char key)
{
    List* newNode = new (key);

    // Set the next of the new node to the current head
    newNode->next = head;

    // Update the head to the new node
    head = newNode;

    return head;
}

// Funzione di utilità per aggiungere un carattere a una stringa
char* appendCharToString(char* str, char c)
{
    size_t len = strlen(str);
    char* newStr = (char*)malloc(len + 2); // +2 per il nuovo carattere e il terminatore null
    if (newStr == NULL) {
        fprintf(stderr, "Memory allocation failed\n");
        exit(EXIT_FAILURE);
    }

    strcpy(newStr, str);
    newStr[len] = c;
    newStr[len + 1] = '\0';

    return newStr;
}

// Funzione ricorsiva per stampare la lista al contrario
char* printListReverseToString(List* head, char* str)
{
    if (head == NULL) {
        return str;
    } else {
        str = printListReverseToString(head->next, str);
        str = appendCharToString(str, head->key);
        str = appendCharToString(str, ',');
        str = appendCharToString(str, ' ');

        return str;
    }
}

// Funzione per stampare la lista
char* printListToString(List* head)
{
    char* result = printListReverseToString(head, "");
    size_t len = strlen(result);

    if (len >= 2) {
        // Rimuovi l'ultima virgola e lo spazio
        result[len - 2] = '\0';
    }

    return result;
}

// Funzione per inizializzare una pila vuota
Stack* newStack()
{
    Stack* stack = (Stack*)malloc(sizeof(Stack));
    if (stack == NULL) {
        fprintf(stderr, "Errore nell'allocazione di memoria per la pila\n");
        exit(EXIT_FAILURE);
    }
    stack->top = NULL;
    return stack;
}

// Funzione per verificare se la pila è vuota
int isStackEmpty(Stack* stack)
{
    return (stack->top == NULL);
}

// Funzione per inserire un elemento in cima alla pila (push)
void push(Stack* stack, kTree value)
{
    // Crea un nuovo nodo
    StackNode* newNode = (StackNode*)malloc(sizeof(StackNode));
    if (newNode == NULL) {
        fprintf(stderr, "Errore nell'allocazione di memoria per il nuovo nodo\n");
        exit(EXIT_FAILURE);
    }

    // Imposta il valore del nodo
    newNode->value = value;

    // Collega il nuovo nodo al top della pila
    newNode->next = stack->top;
    stack->top = newNode;
}

// Funzione per rimuovere un elemento dalla cima della pila (pop)
kTree pop(Stack* stack)
{
    // Controlla se la pila è vuota
    if (isStackEmpty(stack)) {
        fprintf(stderr, "La pila è vuota, impossibile eseguire pop\n");
        exit(EXIT_FAILURE);
    }

    // Salva il valore del nodo al top della pila
    kTree poppedValue = stack->top->value;

    // Salva il puntatore al top attuale
    StackNode* temp = stack->top;

    // Sposta il puntatore al top al prossimo nodo
    stack->top = stack->top->next;

    // Libera la memoria del vecchio top
    free(temp);

    // Restituisci il valore rimosso
    return poppedValue;
}

void initKTree(kTree* A, kTree* B, kTree* C, kTree* D, kTree* E, kTree* F, kTree* G)
{
    A->key = 'A';
    A->child = B;
    A->sibling = NULL;
    B->key = 'B';
    B->child = E;
    B->sibling = C;
    C->key = 'C';
    C->child = NULL;
    C->sibling = D;
    D->key = 'D';
    D->child = G;
    D->sibling = NULL;
    E->key = 'E';
    E->child = NULL;
    E->sibling = F;
    F->key = 'F';
    F->child = NULL;
    F->sibling = NULL;
    G->key = 'G';
    G->child = NULL;
    G->sibling = NULL;
}

typedef struct _serBinTree {
    struct _serBinTree* parent;
    int key;
    struct _serBinTree* left;
    struct _serBinTree* right;
} serBinTree;

void initSerBinTree(serBinTree* A, serBinTree* B, serBinTree* C, serBinTree* D, serBinTree* E, serBinTree* F, serBinTree* G, serBinTree* H)
{
    A->parent = NULL;
    A->key = 12;
    A->left = B;
    A->right = C;

    B->parent = A;
    B->key = 7;
    B->left = D;
    B->right = E;

    C->parent = A;
    C->key = 15;
    C->left = NULL;
    C->right = F;

    D->parent = B;
    D->key = 3;
    D->left = NULL;
    D->right = NULL;

    E->parent = B;
    E->key = 10;
    E->left = G;
    E->right = NULL;

    F->parent = C;
    F->key = 18;
    F->left = H;
    F->right = NULL;

    G->parent = E;
    G->key = 9;
    G->left = NULL;
    G->right = NULL;

    H->parent = F;
    H->key = 16;
    H->left = NULL;
    H->right = NULL;
}
