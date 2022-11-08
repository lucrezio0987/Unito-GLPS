#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define BUFSIZE 128
char ** leggi_lista(FILE *file_in, int *nof_elements) {
    char buf[BUFSIZE];
    int nof_rows = 0, i = 0;
    char ** new_array = NULL;
    //conto quanti elementi sono presenti in lista nome
   
    while(fgets(buf, BUFSIZE, file_in)) 
        nof_rows++;
    //al termine del ciclo nof_rows contiene il numero di righe lette
    *nof_elements = nof_rows;
    //riposiziono il ptr a inizio file
    rewind(file_in);

    if((new_array = malloc(sizeof(char*)*nof_rows)) == NULL) {
        fprintf(stderr, "malloc error; terminating\n");
        exit(1);
    }

    while(fgets(buf, BUFSIZE, file_in)) 
        new_array[i++] = strdup(buf);
    
    return new_array;
}

void stampa_lista(char ** mio_ar, int n_elems) {
    int i;
    for (i = 0; i < n_elems; ++i) 
        printf("mio_ar[%2d]: %s", i, mio_ar[i]);
    printf("\n");
}

int main() {
    FILE* file_in = NULL;
    int nof_elements = 0;
    char ** string_arr = NULL;

    if ((file_in = fopen("file1.txt", "r")) == NULL) {
        fprintf(stderr, "fopen error; exiting\n");
        exit(1);
    }

    string_arr = leggi_lista(file_in, &nof_elements);
    stampa_lista(string_arr, nof_elements);
    fclose(file_in);
}