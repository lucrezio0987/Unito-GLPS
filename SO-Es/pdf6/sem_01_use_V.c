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
    if ((id = semget(ftok("ftok", 'a'), 1, 0600)) == -1)
        ERROR;

    struct sembuf op_sem[1];
    op_sem[0].sem_num = 0;
    op_sem[0].sem_op = 10;
    op_sem[0].sem_flg = 0;   

    if(semop(id, op_sem, 1) == -1) 
        ERROR;

    int val = semctl(id, 0, GETVAL);
    printf("%d \n", val);
}