#include <stdio.h>

int main() {
    //setbuf(stdout, NULL);
    FILE *f;
    char stringa[30];
    f = popen("./filter", "r");
    
    while(fgets(stringa, 30, f) != NULL)
        printf("%s-main\n", stringa);
}