#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>

int main() {
    int pfd[2];

    if(pipe(pfd) == -1)
        printf("pipe error \n");

    for (int i = 0; i < 3; i++)
        switch(fork()) {
            case -1: 
                printf("fork error \n");
                exit(1);
            case 0:
                if (close(pfd[0]) == -1) {
                    printf("figlio [%d], close read error \n", getpid());
                    exit(1);
                }
                sleep(2);
                if (close(pfd[1]) == -1) {
                    printf("figlio [%d], close write error \n", getpid());
                    exit(1);
                }
                printf("figlio [%d], stato: terminato\n", getpid());
                exit(EXIT_SUCCESS);
            default: 
                break;
        }
    wait();
    printf("Processo padre [%d], stato: terminato\n", getpid());
    exit(EXIT_SUCCESS);
}