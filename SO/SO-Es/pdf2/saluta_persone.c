#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>

int main(int argc, char *argv[]) {
    if (argc < 2) {
        fprintf(stderr, "Errore: argomenti non sufficienti\n");
        exit(1);
    }

    for (int i = 1; i < argc; ++i) {
        printf("Ciao %s\n", argv[i]);
    }
}