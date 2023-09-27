#include <stdio.h>
#include <string.h>

int main() {
    FILE *f1;
    f1 = fopen("file6.txt", "r");

    char str_riga[100];
    printf("Lettura del file:\n");
    while (fgets(str_riga, 100, f1) != NULL) 
        printf("\t%s", str_riga);

    fseek(f1, 0, 0); //fseek(variabile, offset, partenza)
    printf("\n\nLettura ponderata del file:\n");
    int i = 0;

    while (fgets(str_riga, 100, f1) != NULL) {
        if(strstr(str_riga, "computational") != NULL) {
            printf("\t%s", str_riga);
            i++;
        }
        
    }
    printf("\nNumero di righe stampate: %d\n", i);
}