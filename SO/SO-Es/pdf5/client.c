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
    char buf[BUF_SIZE];

    //--CONNEZZIONE ALLA CODA DI MESSAGGI--
    if ((id = msgget(ftok("vuoto", 'a'), IPC_CREAT | 0600)) < 0) ERROR;
    
    //--MESSAGGIO AL SERVER--
    printf("Client: Inserisci testo da stampare in rosso: ");
    fgets(buf, BUF_SIZE, stdin);
        messageC.mtype = CTOS;                  // mtype  = INVIO
        sprintf(messageC.mtext, "%s", buf);     // mtext
        messageC.clientPID = (long)getpid();    // clientPID
    if(msgsnd(id, &messageC, (sizeof(messageC) - sizeof(long)), IPC_NOWAIT) == -1) ERROR;

    //--ATTESA e STAMPA RITORNO--
    if(msgrcv(id, &messageS, (sizeof(messageS) - sizeof(long)), (long)getpid(), MSG_NOERROR) == -1) ERROR;
    printf("Client: %s", messageS.mtext);

    //--TERMINZAIONE SERVER--
        messageC.mtype = S_TERM;                // mtype = TERMINZAIONE
        sprintf(messageC.mtext, "%s", buf);     // mtext
        messageC.clientPID = (long)getpid();    // clientPID
    if(msgsnd(id, &messageC, (sizeof(messageC) - sizeof(long)), IPC_NOWAIT) == -1) ERROR;
    
    exit(0);
}