#include <stdio.h>
#include <unistd.h>

#define max 30
int main() {
    FILE *f1;
    f1 = fopen("file1.txt", "r+");
    char stringa[max];

    pid_t procID;
    switch (procID = fork()) {
        case -1:
            error("Errore fork");
        case 0: 
            fprintf(f1, "hello world");
            exit(0);
        default: 
            wait();
            rewind(f1);
            while (fgets(stringa, max, f1) != NULL) 
                printf("%s\n", stringa);
    }

/*
    pid_t process = fork();
    if(!process) {
        fprintf(f1, "hello world");
        exit(0);
    }
    else {
        wait();
        rewind(f1);
        while (fgets(stringa, max, f1) != NULL) {
            printf("%s\n", stringa);
        }
    }
*/
}