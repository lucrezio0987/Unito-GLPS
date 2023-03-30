#include <stdio.h>
#include <stdlib.h>
#include <unistd.h> 
#include <string.h>

#define BUF_SIZE 100

void exitError(char *str_err){
  printf("%s\n"str_err);
  exit(1);
}

int main(int argc, char *argv[]) {
    int pfd1[2], pfd2[2];
    char buf1[BUF_SIZE], buf2[BUF_SIZE];
    ssize_t numRead;

    if((argc != 2 && argc != 3) || strcmp(argv[1], "--help") == 0)
      exitError("Formato on riconosciuto")

    if(pipe(pfd1) == -1)
        exitError("PIPE error");

    switch (fork()) {
    case -1: // Errore
        exitError("fork error");
        break;
    case 0: // figlio
        if(close(pfd1[1]) == -1)
            exitError("close write(1) figlio error");
         {
            numRead = read(pfd1[0], buf1, BUF_SIZE);  //da dove prende a leggere, dove leggere, grandezza di lettura
            if (numRead == -1)
                exitError("Read error\n");
            if (numRead == 0)
                break;
            if (write(STDOUT_FILENO, buf1, numRead) != numRead) // di default (intero), stringa letta e da scrivere, lunghezza stringa 
                exitError("Write error figlio");
        }
        write(STDOUT_FILENO, "\n", 1);
        if(close(pfd1[0]) == -1)
            exitError("close read(0) figlio error");

        if(argc == 3) {
            if(pipe(pfd2) == -1)
                exitError("PIPE error");
            switch(fork()) {
                case -1: // Errore
                    exitError("fork error");
                    break;
                case 0:
                    if(close(pfd2[1]) == -1)
                        exitError("close write(1) figlio error");
                    {
                        numRead = read(pfd2[0], buf2, BUF_SIZE);
                        if(numRead == -1)
                            exitError("Read error");
                        if(numRead == 0)
                            break;
                        if (write(STDOUT_FILENO, buf2, numRead) != numRead)
                            exitError("write error");
                        write(STDOUT_FILENO, "\n", 1);

                        if(close(pfd2[0]) == -1)
                            exitError("close error");
                    }
                    exit(0);
                default:
                    if(close(pfd2[0]) == -1)
                        exitError("close read(0) padre error");
                    if(write(pfd2[1], argv[2], strlen(argv[2])) != strlen(argv[2]))
                        exitError("write error");
                    if(close(pfd2[1]) == -1)
                        exitError("close error");
                    wait();
            }
        }
        exit(0);
    default: // padre
        if(close(pfd1[0]) == -1)
            exitError("close read(0) padre error ");
        if (write(pfd1[1], argv[1], strlen(argv[1])) != strlen(argv[1]))
            exitError("Write error, unmatching string size");
        if(close(pfd1[1]) == -1)
            exitError("close write(1) padre error");
        wait();
    }
    exit(0);
}
