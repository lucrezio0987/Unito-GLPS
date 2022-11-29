#include <stdio.h>
#include "lib_sem.h"

int main() {
    int semID, shmID;
    data* shmp;
    // read = 0; write = 1
    if((semID = semget(ftok("ftok", 'b'), 2, IPC_CREAT | 0644)) == -1)
        ERROR;
    
    if((shmID = shmget(ftok("ftok", 'b'), sizeof(struct my_data), IPC_CREAT | 0644)) == -1)
        ERROR;

    // CREAZIONE: Sem1, Sem2 ; shm
    if((semID = semget(ftok("ftok", 'a'), 2, IPC_CREAT | 0644)) == -1) ERROR;
    if((shmID = shmget(ftok("ftok", 'a'), sizeof(data), IPC_CREAT | 0644)) == -1) ERROR;

    // INIZIALIZZAIONE: Sem1, Sem2 = 0
    initSemInUse(semID, 0);
    initSemInUse(semID, 1);

    //SHELL MEMORY: mount - caricamento - unmount
    if((shmp = shmat(shmID, NULL, 0)) == (void *)-1) ERROR;
    scanf("%s", shmp->buf);                // carinca nella shm
    if(shmdt(shmp) == -1) ERROR;
    
    if(releaseSem(semID,0) == -1) ERROR;  // fa partire la lettura
 
    if(reserveSem(semID,1) == -1) { ERROR; }  // aspetta la terminazione della lettura
    else printf("Reader terminato\n");

    //RIMOZIONE: Sem1, Sem2 ; shm
    if(semctl(semID, IPC_RMID, 0) == -1) { ERROR; } 
    else printf("Semafori deallocati \n");
    if(shmctl(shmID, IPC_RMID, 0) == -1) { ERROR; } 
    else printf("Memoria deallocata \n");
    
    exit(0);
}