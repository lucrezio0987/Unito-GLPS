#include <stdio.h>
#include <stdlib.h>
#include <sys/sem.h>
#include <errno.h>

#define ERROR if(errno) {                                                                               \     
    printf("ERROR - l %d: pid %ld - n %d - (%s)\n", __LINE__, (long)getpid(), errno, strerror(errno));  \
    exit(1);                                                                                            \
}

union semun {
    int i;
    char c;
};

int main() {
    int id;

    union semun arg;
    
    if ((id = semget(ftok("ftok", 'a'), 1, IPC_CREAT | 0600)) == -1) 
        ERROR;
    
    arg.i = 0;
    if (semctl(id, 0, SETVAL, arg) == -1) 
        ERROR;
    
    struct semid_ds mysem;
    semctl(id, 0, IPC_STAT, &mysem);
    printf("%d \n", mysem.sem_nsems);
}