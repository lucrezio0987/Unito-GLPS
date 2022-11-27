#include <stdio.h>
#include <stdlib.h>
#include <errno.h>
#include <sys/msg.h>

#define ERROR if(errno) {                                                                               \     
    printf("ERROR - l %d: pid %ld - n %d - (%d)\n", __LINE__, (long)getpid(), errno, strerror(errno));  \
    exit(1);                                                                                            \
}

struct msg {
    long mtype;
    char mtext[30];
    long clientPID;
};

int main() {

    int id;
    struct msqid_ds myqueue;
    struct msg messageC;
    struct msg messageS;

    if ((id = msgget(ftok("vuoto", 'a'), IPC_CREAT | 0600)) < 0)
        ERROR;
    
    messageC.clientPID = (long)getpid();
    messageC.mtype = 1;
    sprintf(messageC.mtext, "ciao");
                    
    if(msgsnd(id, &messageC, (sizeof(messageC) - sizeof(long)), IPC_NOWAIT) == -1)
        ERROR;
    
    if(msgrcv(id, &messageS, (sizeof(messageS) - sizeof(long)), (long)getpid(), MSG_NOERROR) == -1)
        ERROR;
    printf("%s \n", messageS.mtype);
    exit()
}