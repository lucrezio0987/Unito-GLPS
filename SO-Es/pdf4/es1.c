#include <stdio.h>
#include <stdlib.h>
#include <unistd.h> 
#include <string.h>

#define BUF_SIZE 100
int main(int argc, char *argv[]) {
    int pfd1[2], pfd2[2];
    char buf1[BUF_SIZE], buf2[BUF_SIZE];
    ssize_t numRead;

    if((argc != 2 && argc != 3) || strcmp(argv[1], "--help") == 0) {
        printf("Il formato è sbagliato, inserire la stringa da caricare..\n");
        exit(1);
    }

    if(pipe(pfd1) == -1) {
        printf("PIPE error\n");
        exit(1);
    }

    switch (fork()) {
    case -1: // Errore
        printf("fork error\n");
        exit(1);
        break;
    case 0: // figlio
        if(close(pfd1[1]) == -1) {
            printf("close write(1) figlio error\n");
            exit(1);
        } 
         {
            numRead = read(pfd1[0], buf1, BUF_SIZE);  //da dove prende a leggere, dove leggere, grandezza di lettura
            if (numRead == -1) {
                printf("Read error\n");
                exit(1);
            }
            if (numRead == 0)
                break;
            if (write(STDOUT_FILENO, buf1, numRead) != numRead) { // di default (intero), stringa letta e da scrivere, lunghezza stringa 
                printf("Write error figlio"); 
                exit(1);
            }
        }
        write(STDOUT_FILENO, "\n", 1);
        if(close(pfd1[0]) == -1) {
            printf("close read(0) figlio error\n");
            exit(1);
        }

        if(argc == 3) {
            if(pipe(pfd2) == -1) {
                printf("PIPE error\n");
                exit(1);
            }
            switch(fork()) {
                case -1: // Errore
                    printf("fork error\n");
                    exit(1);
                    break;
                case 0:
                    if(close(pfd2[1]) == -1) {
                        printf("close write(1) figlio error\n");
                        exit(1);
                    }
                    {
                        numRead = read(pfd2[0], buf2, BUF_SIZE);
                        if(numRead == -1) {
                            printf("Read error\n");
                            exit(1);
                        }
                        if(numRead == 0)
                            break;
                        if (write(STDOUT_FILENO, buf2, numRead) != numRead) {
                            printf("write error\n");
                            exit(1);
                        }
                        write(STDOUT_FILENO, "\n", 1);

                        if(close(pfd2[0]) == -1) {
                            printf("close error");
                            exit(1);
                        }
                    }
                    exit(0);
                default:
                    if(close(pfd2[0]) == -1) {
                        printf("close read(0) padre error\n");
                        exit(1);
                    }
                    if(write(pfd2[1], argv[2], strlen(argv[2])) != strlen(argv[2])) {
                        printf("write error\n");
                        exit(1);
                    }
                    if(close(pfd2[1]) == -1) {
                        printf("close error\n");
                        exit(1);
                    }
                    wait();
            }
        }
        exit(0);
    default: // padre
        if(close(pfd1[0]) == -1) {
            printf("close read(0) padre error \n");
            exit(1);
        }
        if (write(pfd1[1], argv[1], strlen(argv[1])) != strlen(argv[1])) {
            printf("Write error, unmatching string size\n");
            exit(1);           
        }
        if(close(pfd1[1]) == -1)  {
            printf("close write(1) padre error\n");
            exit(1);
        }
        wait();
    }
    exit(0);
}