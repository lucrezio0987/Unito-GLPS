#include <stdio.h>
#include <errno.h>
#include <sys/msg.h>
#include <string.h>

#define BUF_SIZE 30
#define ERROR if(errno) { \
    printf("%d: pid %ld errno: - %d - (%s)\n", 1, (long)getpid(), errno, strerror(errno)); \
    exit(1); \
}

struct message {
    long mtype;
    char mtext[BUF_SIZE];
} message;

int main() {
    int id, n;
    
    if ((id = msgget(ftok("vuoto", 'a'), 0644)) < 0)
        ERROR;
    
    printf("Che tipo di messaggi vuoi stampare (intero): \n");
    scanf("%d", &n);

    struct msqid_ds myqueue; 
    struct message msg;
    
    msgctl(id, IPC_STAT, &myqueue);

    int i = 0;
    while(myqueue.msg_qnum != 0) {
        if (msgrcv(id, &msg, (sizeof(msg) - sizeof(long)), n, MSG_NOERROR | IPC_NOWAIT) == -1) 
            ERROR;
        printf("%d : %s\n", msg.mtype, msg.mtext);
        i++;

        msgctl(id, IPC_STAT, &myqueue);
    }
    
    
    if(msgctl(id, IPC_RMID, &myqueue) == -1) {
        ERROR;
    } else 
        printf("Coda deallocata \n");
}