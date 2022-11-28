#include <stdio.h>
#include <sys/shm.h>
#include <sys/sem.h>
#include <errno.h>

#define ERROR if(errno) {                                                                               \     
    printf("ERROR - l %d: pid %ld - n %d - (%s)\n", __LINE__, (long)getpid(), errno, strerror(errno));  \
    exit(1);                                                                                            \
}

typedef struct my_data {
    char buf[30];
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
    int semID, shmID;
    data* shmp;

    if((semID = semget(ftok("ftok", 'a'), 2, 0600)) == -1) ERROR;
    
    if(reserveSem(semID,0) == -1 ) ERROR;

    if((shmID = shmget(ftok("ftok", 'a'), sizeof(data), 0600)) == -1) ERROR;

    if((shmp = shmat(shmID, NULL, 0)) == (void *)-1) ERROR;
    printf("%s\n", shmp->buf);

    if(shmdt(shmp) == -1) ERROR;
    if(releaseSem(semID,1) == -1) ERROR;

    exit(0);
}