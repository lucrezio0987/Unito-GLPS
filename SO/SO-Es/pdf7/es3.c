#include <stdio.h>
#include "lib_sem.h"

int main(int argc, char *argv[]) {
    int N, M;
    int i,j;
    data* shmp;
    int semID, shmID;

   if(argc != 3) ERROR;
   N=atoi(argv[1]);
   M=atoi(argv[2]);


    //TODO: SHM
    //TODO: creazione N figli
        //cicla M volte
        //sleep-ran[0..9] sleep(rand()%9)
        //monta SM - scrive PID - smonta SM
    //TODO: padre
        //sleep(5)
        //monta SM - stampa PID - smonta SM
    //TODO: wait() N figli

   if((semID = semget(ftok("ftok", 'a'), 2, IPC_CREAT | 0644)) == -1) ERROR;
   if((shmID = shmget(ftok("ftok", 'a'), sizeof(data), IPC_CREAT | 0644)) == -1) ERROR;

   if(initSemInUse(semID, 0) == -1) ERROR;       // LETTURA = 0
   if(initSemAvailable(semID, 1) == -1) ERROR;    // SCRITTURA = 1

    for(i=0; i<N; ++i)
        switch(fork()){
            case -1: ERROR;
            case 0: //--FIGLIO
               for(j=0; j<M; ++j){
                   sleep(rand()%10);
                   if(reserveSem(semID,1) == -1) ERROR;                      // prende la scrittura
                   if((shmp = shmat(shmID, NULL, 0)) == (void *)-1) ERROR;
                   shmp->val=getpid();                                       // scrittura su shm
                   if(shmdt(shmp) == -1) ERROR;
                   if(releaseSem(semID,1) == -1) ERROR;                      // fine scrittura
               }
               if(releaseSem(semID,0) == -1) ERROR;                          // Permette 1 lettura al padre
                exit(0);
        }

    //--PADRE
   for(j=0; j<M; ++j){
       sleep(5);
       if(reserveSem(semID,0) == -1) ERROR;                          // Padre attende la lettura
       if((shmp = shmat(shmID, NULL, 0)) == (void *)-1) ERROR;
       printf("%d\n",shmp->val);                                     // lettura da shm
       if(shmdt(shmp) == -1) ERROR;
   }
   
    //RIMOZIONE: Sem1, Sem2 ; shm
   if(semctl(semID, IPC_RMID, 0) == -1) { ERROR; } 
   else printf("Semafori deallocati \n");
   if(shmctl(shmID, IPC_RMID, 0) == -1) { ERROR; } 
   else printf("Memoria deallocata \n");

    exit(0);
}