#include <stdio.h>
#include <stdlib.h>
#include <sys/sem.h>
#include <errno.h>

#define ERROR if(errno) {                                                                               \     
    printf("ERROR - l %d: pid %ld - n %d - (%s)\n", __LINE__, (long)getpid(), errno, strerror(errno));  \
    exit(1);                                                                                            \
}

int main() {
    int id;
    if((id = semget(ftok("ftok", 'a'), 1, 0600)) == -1)
        ERROR;

    if(semctl(id, 0, IPC_RMID) == -1) {
        ERROR;
    } else
        printf("Semaforo deallocato \n");
}