#include <stdio.h>
#include <stdlib.h>
#include <string.h>

char ** leggi_lista(FILE* file_in, int* nof_elements) {
    char **lista = malloc(sizeof(char*)*(*nof_elements));
    char temp[30];

    for (int i = 0; i < (*nof_elements); ++i) {
        fgets(temp, 30, file_in);
        if (temp[0] != '\r') {
            lista[i] =  malloc(sizeof(char)*(strlen(temp)));
            strcpy(lista[i], temp);
        } else --i;
    }
    return lista;
}

void stampa_lista(char** mio_ar, int n_elems) {
    for (int i = 0; i < n_elems; i++)
        printf("%s", mio_ar[i]);
    return;
}

int conta(FILE *file) {
    int i = 0;
    char temp[30];
    while (fgets(temp, 30, file) != NULL) {
        if (temp[0] != '\r') 
            i++;
    }
    return i;
}

int main() {
    FILE *f1;
    int n_elementi;
    char **lista;

    f1 = fopen("file1.txt", "r");
    n_elementi = conta(f1);

    lista = leggi_lista(fopen("file1.txt", "r"), &n_elementi);
    stampa_lista(lista, n_elementi);
    exit(1);
}