#include <stdio.h>
#include <unistd.h>
#include <sys/wait.h>
#include <sys/types.h>
#include <stdlib.h>
#include <string.h>

int main() {
    int n;
    pid_t pid;

    printf("Quanti figli creare? ");
    scanf("%d", &n);

    for (int i = 0; i < n; i++) {
        if((pid=fork()) == 0) 
            exit(10+i); // per evitare di occupare exit(0) e exit(1)
        if (getpid() == pid)
            sleep(pid % 10);
        printf("sleep: %d %d\n", pid, pid%10);        
    }

    if(getpid())
        printf("Il processo %d ha creato correttamente %d figli\n", getpid(), n);
        
    while ((pid=waitpid(-1, NULL, 0)) > 0) 
        printf("Processo %d terminato\n", pid);
    exit(0);
}