#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>

char* parse_nome(char* stringa_completa) {
    char *nome = malloc(sizeof(char) * 30);
    int i = 0;
    while(isalpha(stringa_completa[i])) {
        nome[i] = stringa_completa[i];
        i++;
    }
    return nome;
}

int main() {
    char stringa[30];
    char *nome;

    fgets(stringa, 30, stdin); //oppure usando gets(stringa)
    while(stringa[0] != '\n') {
        nome = parse_nome(stringa);
        printf("%s\n", nome);
        fgets(stringa, 30, stdin); //gets(stringa)
    } 
}
