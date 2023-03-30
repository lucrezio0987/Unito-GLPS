#include <stdio.h>
#include <stdlib.h>
#include <errno.h>
#include <sys/msg.h>

#define CTOS 1
#define S_TERM 2
#define ERROR if(errno) {                                                                               \     
    printf("ERROR - l %d: pid %ld - n %d - (%s)\n", __LINE__, (long)getpid(), errno, strerror(errno));  \
    exit(1);                                                                                            \
}

#define BLU "\033[0;36m"
#define RED "\033[0;31m"
#define WHITE "\033[0;37m"

#define BUF_SIZE 30

struct msg {
    long mtype;
    char mtext[BUF_SIZE];
    long clientPID;
};

int main() { 

    int id;
    struct msqid_ds myqueue;
    struct msg messageC;
    struct msg messageS; 
    
    //--CREAZIONE DELLA CODA DI MESSAGGI--
    if ((id = msgget(ftok("vuoto", 'a'), IPC_CREAT | 0600)) < 0) ERROR;
    
    //--CICLO DI ASCOLTO--
    for(;;) {
        msgctl(id, IPC_STAT, &myqueue);
        if(myqueue.msg_qnum != 0) {
            //--ATTESA MESSAGGIO--
            if(msgrcv(id, &messageC, (sizeof(messageC) - sizeof(long)), CTOS, IPC_NOWAIT) == -1) ERROR;

            //--GESIONE MESSAGGIO NOMALE--
            switch (fork()) {
                case -1: ERROR;
                case 0:
                //--ELABORAZIONE MESASGGIO--
                    printf("--> Server: %sMessaggio ricevuto dal server%s\n", BLU, WHITE);
                    messageS.mtype = messageC.clientPID;
                    messageS.clientPID=messageC.clientPID;
                    sprintf(messageS.mtext, "%s%s%s", RED, messageC.mtext, WHITE);
                //--INVIO RIPOSTA--
                    if(msgsnd(id, &messageS, (sizeof(messageS) - sizeof(long)), IPC_NOWAIT) == -1) ERROR;
                    printf("<-- Server: %sMessaggio inviato dal server%s\n", BLU, WHITE);
                    exit(0);
                default: wait(); break;
            }

            //--GESTIONE MESSAGGIO DI USCITA--
            if(msgrcv(id, &messageC, (sizeof(messageC) - sizeof(long)), S_TERM, IPC_NOWAIT) == -1) ERROR;
            if(messageC.mtype==S_TERM) break;
        }
    }

    //-- DEALLOCAZIONE CODA + TERMINAZIONE SERVER--
    if(msgctl(id, IPC_RMID, &myqueue) == -1) { ERROR; } 
    else  printf("Server: Coda deallocata \n");
    exit(0);
}