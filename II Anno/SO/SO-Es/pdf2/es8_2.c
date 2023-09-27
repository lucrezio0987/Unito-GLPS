#include <stdio.h>

int main(int argc, char *argv[]) {
    FILE *f1, *f2;
    char stringa[50];

    f1 = fopen(argv[0], "r");
    f2 = fopen(argv[1], "w");

    while (fgets(stringa, 50, f1) != NULL)
        fprintf(f2, "%s", stringa);
    
    exit(0);
}