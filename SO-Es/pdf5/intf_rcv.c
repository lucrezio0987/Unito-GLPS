#include <stdio.h>
#include <errno.h>
#include <sys/msg.h>

#define BUF_SIZE 30
#define ERROR if(errno) { \
    printf("%d: pid %ld (%s)\n", 1, (long)getpid(), errno, strerror(errno)); \
    exit(1); \
}

struct message {
    long mtype;
    char mtext[BUF_SIZE];
};

int main() {
    int id, n;
    
    if ((id = msgget(1234, 0644)) == -1)
        ERROR;
    
    printf("Che tipo di messaggi vuoi stampare (intero): \n");
    scanf("%d", &n);

    struct msqid_ds myqueue; 
    struct message msg;
    
    msgctl(id, IPC_STAT, &myqueue);

    if ((msgrcv(id, &msg, (sizeof(msg) - sizeof(long)), n, MSG_NOERROR | IPC_NOWAIT)) == -1) 
        ERROR;
    
    printf("%d : %s\n", msg.mtype, msg.mtext);

    /*
    if(msgctl(id, IPC_RMID, &myqueue) == -1) {
        ERROR;
    } else 
        printf("Coda deallocata \n");
    */
}