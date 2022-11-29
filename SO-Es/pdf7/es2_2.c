#include <stdio.h>
#include "lib_sem.h"


int main() {
    int semID, shmID;
    data* shmp;

    // CREAZIONE: Sem1, Sem2 ; shm
    if((semID = semget(ftok("ftok", 'a'), 2, 0644)) == -1) ERROR;
    if((shmID = shmget(ftok("ftok", 'a'), sizeof(data), 0644)) == -1) ERROR;

    if(reserveSem(semID,0) == -1 ) ERROR;  // aspetta la scrittura

    //SHELL MEMORY: mount - lettura - unmount
    if((shmp = shmat(shmID, NULL, 0)) == (void *)-1) ERROR;
    printf("%s \n", shmp->buf);            // legge da shm
    if(shmdt(shmp) == -1) ERROR;

    if(releaseSem(semID,1) == -1) ERROR;   // fa ripartire la scrittura

    exit(0);
}