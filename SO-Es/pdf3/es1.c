#include <stdio.h>
#include <signal.h>

void header(int sig) {
    printf("sigterm intercettato\n");
}

int main() {
    printf("Processo: %d\n", getpid());
    signal(SIGTERM, header);

    pid_t pid = fork();
    switch(pid) {
        case -1:
            printf("fork error\n");
            exit(1);
        case 0: 
            sleep(2);
            printf("PROCESSO NON KILLATO\n");
            break;
        default:
            kill(pid, SIGTERM);
            kill(pid, SIGKILL);
            wait();
    }  
}


