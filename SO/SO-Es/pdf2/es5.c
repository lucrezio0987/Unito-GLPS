#include <stdio.h>
#include <stdlib.h>

int main() {
    int n = 0;
    pid_t pid = getpid(), father = getpid();

    printf("PROCESSO PADRE %d\n", father);
    
    printf("Quanti figli creare? ");
    scanf("%d", &n);

    for (int i = 1; i <= n; i++) {
        if (pid != 0) {
            pid = fork();
            if (pid != 0)
                printf("processo %d creato correttamente\n", pid);
            else 
                sleep(i);
        }
    }

    while ((pid = waitpid(-1, NULL, 0)) > 0)
        printf("Processo %d terminato correttamente\n", pid);

    if (getpid() == father)
        printf("\ntutti i processi sono terminati\n");
    exit(0);
}
    


