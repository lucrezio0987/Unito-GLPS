#include <stdio.h>
#include <errno.h>
#include <sys/shm.h>

#define ERROR if(errno) {                                                                               \     
    printf("ERROR - l %d: pid %ld - n %d - (%s)\n", __LINE__, (long)getpid(), errno, strerror(errno));  \
    exit(1);                                                                                            \
}

typedef struct my_data {
    int counter;
    char stringa[30];
} data;

int main() {
    switch(fork()) {
        case -1: 
            ERROR;
        case 0:
            int id;
            if((id = shmget(ftok("ftok", 'a'), sizeof(data), 0600)) == -1)
                ERROR;

            data* shmp = shmat(id, NULL, 0);
            printf("%d: %s \n", shmp -> counter, shmp -> stringa);

            shmdt(shmp);
            exit(0);
        default:
            break;
    }
    int id;
    if((id = shmget(ftok("ftok", 'a'), sizeof(data), IPC_CREAT | 0600)) == -1)
        ERROR;
    
    data* shmp = shmat(id, NULL, 0);
    
    shmp -> counter = 0;
    sprintf(shmp -> stringa, "memoria condivisa");

    shmdt(shmp);
    wait();

    if(shmctl(id, IPC_RMID, 0) == -1) {
        ERROR;
    } else 
        printf("Memoria deallocata \n");   
}