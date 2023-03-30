#include <stdio.h>
#include <stdlib.h>
#include <signal.h>

int main() {
    pid_t pid;
    int sigkill = 1;
    printf("Processo padre: %d\n", getpid());
    
    if((pid = fork()) != 0)
        printf("processo figlio: %d\n", pid);
    
    if (pid == 0) {
        printf("Processo figlio %d terminato\n", getpid());
        exit(1);
    } else {
        sleep(10);
        if (kill(pid, sigkill) == 0)
            printf("Kill avvenuta con successo\n");
        else 
            printf("kill avvenuta senza successo, errno\n");
    }

    


}