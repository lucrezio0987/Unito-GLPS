#include <stdio.h>
#include <errno.h>
#include <sys/msg.h>

#define ERROR if(errno) { \
    printf("%d: pid %ld  - %d - (%s)\n", 1, (long)getpid(), errno, strerror(errno)); \
    exit(1); \
}

struct msg {
    long mtype;
    char mtext[30];
};

int main() {
    int id;

    struct msqid_ds myqueue;
    
    if ((id = msgget(IPC_PRIVATE, IPC_CREAT | 0600)) == -1)
        ERROR;

    switch (fork()) {
    case -1: ERROR
    
    case 0: 
        struct msg message = {getpid(), "saluti da "};
        msgsnd(id, &message, (sizeof(message) - sizeof(long)), IPC_NOWAIT);
        
        exit(0);
    default:
        wait();
        struct msg receive;

        if(msgrcv(id, &receive, sizeof(receive) - sizeof(long), 0, MSG_NOERROR | IPC_NOWAIT) == -1)
            ERROR;
        printf("%s %d \n", receive.mtext, receive.mtype);
        
        if(msgctl(id, IPC_RMID, &myqueue) == -1) {
            ERROR;
        } else 
            printf("Coda deallocata \n");
        exit(0);
    }
}