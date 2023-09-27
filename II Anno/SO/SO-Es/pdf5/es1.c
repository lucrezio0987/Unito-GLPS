#include <stdio.h>
#include <sys/msg.h>
#include <sys/stat.h>
#include <time.h>
#include <errno.h>

#define ERROR if(errno) { \
    printf("%d: pid %ld errno: - %d - (%s)\n", 1, (long)getpid(), errno, strerror(errno)); \
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
    struct mymsg message2;

    if ((msgsnd(id, &message, (sizeof(message)-sizeof(long)), IPC_NOWAIT)) == -1) 
        ERROR;

    if(msgctl(id, IPC_STAT, &myqueue) == -1) 
        ERROR;

    printf("Numero di messaggi in coda: %d \n", myqueue.msg_qnum);
    printf("Tempo dell'ultima msg snd(): %d \n", myqueue.msg_stime);
    printf("Tempo in cui è occorsa la msgsnd: %d \n", myqueue.msg_ctime);

    if ((msgrcv(id, &message2, (sizeof(message2)-sizeof(long)), 1, IPC_NOWAIT)) == -1) 
        ERROR;
        
    printf("-----%s\n", message2.mtext);

    if(msgctl(id, IPC_RMID, &myqueue) == -1) {
        ERROR;
    } else 
        printf("Coda deallocata \n");

    exit(0);
}