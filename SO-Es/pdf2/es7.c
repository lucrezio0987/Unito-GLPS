#include <stdio.h>
#include <stdlib.h>

int main() {
    pid_t pid = fork();

    if (pid == 0) {
        execl("./saluta_pesone", "./saluta_persone", "mario", "ada", NULL);
        printf("execl non riuscita\n");
        exit(1);
    }

    wait();
    printf("Programma terminato\n");
    exit(0);
}