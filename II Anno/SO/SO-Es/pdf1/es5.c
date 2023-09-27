#include <stdio.h>

int main() {
    printf("Inserire una stringa in input: ");
    char c;

    while((c = getchar()) != '\n') {
        printf("%c", c-2);
    }
    printf("\n");
}