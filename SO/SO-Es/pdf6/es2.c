#include <stdio.h>
#include <stdlib.h>
#include <sys/sem.h>
#include <errno.h>

#define ERROR if(errno) {                                                                               \     
    printf("ERROR - l %d: pid %ld - n %d - (%s)\n", __LINE__, (long)getpid(), errno, strerror(errno));  \
    exit(1);                                                                                            \
}

int reserveSem(int id_sem, int n_sem) {
    struct sembuf s_ops;

    s_ops.sem_num = n_sem;
    s_ops.sem_op = -1;
    s_ops.sem_flg = 0;

    return semop(id_sem, &s_ops, 1);
}

int releaseSem(int id_sem, int n_sem) {
    struct sembuf s_ops;

    s_ops.sem_num = n_sem;
    s_ops.sem_op = 1;
    s_ops.sem_flg = 0;

    return semop(id_sem, &s_ops, 1);
}

int main() {
    int id;
    if((id = semget(ftok("ftok", 'a'), 1, IPC_CREAT | 0600))== -1)
        ERROR;

    switch (fork()) {
    case -1:
        ERROR;
    
    case 0:
        sleep(5);
        releaseSem(id, 0);
        exit(0);

    default:
        reserveSem(id, 0);
        if(semctl(id, 1, IPC_RMID) == -1) {
            ERROR;
        } else 
            printf("Semaforo deallocato \n");
    }
}