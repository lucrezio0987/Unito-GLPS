#include <stdio.h>
#include <errno.h>
#include <sys/msg.h>
#include <stdlib.h>

#define ERROR if(errno) { \
    printf("ERROR - %d: pid %ld  - %d - (%d)\n", __LINE__, (long)getpid(), errno, strerror(errno)); \
    exit(1); \
}

struct msg {
    long mtype;
    char mtext[30];
};

int main() {
    int id;

    struct msqid_ds myqueue;
    struct msg message;
    if ((id = msgget(IPC_PRIVATE, IPC_CREAT | 0600)) == -1)
        ERROR;

    switch (fork()) {
    case -1: ERROR
    
    case 0: 
        message.mtype=(long)getpid();
        sprintf(message.mtext, "saluti da ");
        msgsnd(id, &message, (sizeof(message) - sizeof(long)), IPC_NOWAIT);
        
        exit(0);
    default:
        wait();

        if(msgrcv(id, &message, sizeof(message) - sizeof(long), 0, MSG_NOERROR | IPC_NOWAIT) == -1)
            ERROR;
        printf("%s %ld \n", message.mtext, message.mtype);
        
        if(msgctl(id, IPC_RMID, &myqueue) == -1) {
            ERROR;
        } else 
            printf("Coda deallocata \n");
        exit(0);
    }
}