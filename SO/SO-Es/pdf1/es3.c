#include <stdio.h>
#include <ctype.h>
#include <string.h>

void recur_to_up(char* in_str) {
    if(in_str[0] != '\0') {
        in_str[0] = toupper(in_str[0]);
        return recur_to_up(++in_str);
    } else 
        return;
}

int main() {
    char stringa[50];
    printf("Inserire una stringa: ");
    fgets(stringa, 50, stdin);

    recur_to_up(stringa);
    printf("%s", stringa);
}