#include <stdio.h>
#include <sys/shm.h>
#include <sys/sem.h>
#include <errno.h>

#define ERROR if(errno) {                                                                               \     
    printf("ERROR - l %d: pid %ld - n %d - (%s)\n", __LINE__, (long)getpid(), errno, strerror(errno));  \
    exit(1);                                                                                            \
}

typedef struct my_data {
    char stringa[30];
} data;


int reserveSem(int id_sem, int n_sem) {
    struct sembuf s_ops[2];

    s_ops[n_sem].sem_num = n_sem;
    s_ops[n_sem].sem_op = -1;
    s_ops[n_sem].sem_flg = 0;

    return semop(id_sem, s_ops, 2);
}

int releaseSem(int id_sem, int n_sem) {
    struct sembuf s_ops[2];

    s_ops[n_sem].sem_num = n_sem;
    s_ops[n_sem].sem_op = 1;
    s_ops[n_sem].sem_flg = 0;

    return semop(id_sem, s_ops, 2);
}

int main() {
    int sem, id;
    if((id = shmget(ftok("ftok", 'b'), sizeof(data), IPC_CREAT | 0600)) == -1)
        ERROR;

    if((sem = semget(ftok("ftok", 'b'), 2, IPC_CREAT | 0600)) == -1)
        ERROR;

    data* shmp = shmat(id, NULL, 0);
    scanf("%s",shmp -> stringa);

    releaseSem(sem, 0);
    reserveSem(sem,1);

    shmdt(shmp);

    if(semctl(sem, IPC_RMID, 2) == -1) {
        ERROR;
    } else 
        printf("Semafori deallocati \n");

    if(shmctl(id, IPC_RMID, 0) == -1) {
        ERROR;
    } else 
        printf("Memoria deallocata \n");
}