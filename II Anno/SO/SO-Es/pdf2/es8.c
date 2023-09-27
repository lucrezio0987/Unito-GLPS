#include <stdio.h>
#include <unistd.h>

int main(int argc, char *argv[]) {
    if (argc != 3) {
        fprintf(stderr, "Errore\n");
        exit(1);
    }

    pid_t pid;
    if ((pid = fork()) == 0)
        execl("./es8_2", argv[1], argv[2]);
    
    wait();
    exit(0);
}