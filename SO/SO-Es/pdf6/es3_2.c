#include <stdio.h>
#include <stdlib.h>
#include <sys/sem.h>
#include <errno.h>

#define ERROR if(errno) {                                                                               \     
    printf("ERROR - l %d: pid %ld - n %d - (%s)\n", __LINE__, (long)getpid(), errno, strerror(errno));  \
    exit(1);                                                                                            \
}

int release(int id, int num) {
    struct sembuf op;

    op.sem_num = num;
    op.sem_op = 1;
    op.sem_flg = 0;

    return semop(id, &op, 1);
}

int main() {
    int id;
    if ((id = semget(ftok("ftok", 'a'), 1, IPC_CREAT | 0600)) == -1) 
        ERROR;
    
    release(id, 0);
    exit(0);
}