#include <stdio.h>
#include <string.h>
#include <ctype.h>

void stampa(char *stringa, int length) {
    if(length==0) {
        printf("\n");
        return;
    } else {
        printf("%c", toupper(stringa[length - 1]));
        return stampa(stringa, length - 1);
    }
}

int main() {
    char stringa[100];
    printf("Inserire una stringa: ");
    fgets(stringa, 100, stdin);

    stampa(stringa, strlen(stringa) - 1);
}