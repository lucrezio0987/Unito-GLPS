#include <stdio.h>
#include <signal.h>
#include <stdlib.h>
#include <unistd.h>


void handler_p(int sig) {
    printf("COMANDO SIGINT INTERCETTATO - PADRE\n");
    fflush(stdout);
    signal(SIGINT, SIG_DFL);
}

void handler_f(int sig) {
    printf("COMANDO SIGINT INTERCETTATO - FIGLIO\n");
    fflush(stdout);
    signal(SIGINT, SIG_DFL);
}
int main() {
    void (*old_handler) (int);
    //old_handler = signal(SIGINT, handler);
    struct sigaction act, old_act;
    act.sa_handler = &handler_p;

    switch (fork()) {
    case -1:
        printf("fork error\n");
        exit(1);
    case 0:
        old_handler = signal(SIGINT, handler_f);
        for(;;);
        exit(0);
    default:
        sigaction(SIGINT, &act, &old_act);
        wait();
    }
}