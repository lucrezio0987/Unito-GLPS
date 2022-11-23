#include <stdio.h>
#include <sys/msg.h>
#include <sys/stat.h>
#include <time.h>
#include <errno.h>

#define ERROR if(errno) { \
    printf("%d: pid %ld (%s)\n", 1, (long)getpid(), errno, strerror(errno)); \
    exit(1); \
}

struct mymsg {
    long mtype;
    char mtext[30];
};

int main() {
    int id;

    if ((id = msgget(IPC_PRIVATE, IPC_CREAT | 0600)) == -1) 
        ERROR;

    struct msqid_ds myqueue;
    if(msgctl(id, IPC_STAT, &myqueue) == -1) 
        ERROR;

    printf("Dimensione della coda: %d \n", myqueue.msg_qbytes);
    printf("Numero di messaggi in coda: %d \n", myqueue.msg_qnum);
    printf("Tempo dell'ultima msg snd(): %d \n", myqueue.msg_stime);

    struct mymsg message = {sizeof(char), "hello world"};

    if ((msgsnd(id, &message, (sizeof(message)-sizeof(long)), IPC_NOWAIT)) == -1) 
        ERROR;

    if(msgctl(id, IPC_STAT, &myqueue) == -1) 
        ERROR;

    printf("Numero di messaggi in coda: %d \n", myqueue.msg_qnum);
    printf("Tempo dell'ultima msg snd(): %d \n", myqueue.msg_stime);
    printf("Tempo in cui è occorsa la msgsnd: %d \n", myqueue.msg_ctime);

    if(msgctl(id, IPC_RMID, &myqueue) == -1) {
        ERROR;
    } else 
        printf("Coda deallocata \n");

    exit(0);
}