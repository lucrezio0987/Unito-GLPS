#include <stdio.h>
#include <stdlib.h>
#include <errno.h>
#include <sys/msg.h>

#define CTOS 1
#define ERROR if(errno) {                                                                               \     
    printf("ERROR - l %d: pid %ld - n %d - (%d)\n", __LINE__, (long)getpid(), errno, strerror(errno));  \
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

    if ((id = msgget(ftok("vuoto", 'a'), IPC_CREAT | 0600)) < 0)
        ERROR;
    
    printf("Client: Inserisci testo da stamoare in rosso: ");
    fgets(buf, BUF_SIZE, stdin);
    messageC.clientPID = (long)getpid();
    messageC.mtype = CTOS;
    sprintf(messageC.mtext, "%s", buf);
                    
    if(msgsnd(id, &messageC, (sizeof(messageC) - sizeof(long)), IPC_NOWAIT) == -1)
        ERROR;
    
    if(msgrcv(id, &messageS, (sizeof(messageS) - sizeof(long)), (long)getpid(), MSG_NOERROR) == -1)
        ERROR;
    printf("Client: %s", messageS.mtext);
    exit(0);
}