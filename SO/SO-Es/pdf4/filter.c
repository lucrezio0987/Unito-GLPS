#include <stdio.h>
#include <string.h>
#include <ctype.h>

int main() {
    char c;
    while ((c = getchar()) != EOF) {
        c = toupper(c);
        if (c != '\n')
            putchar(c);
    } 
}