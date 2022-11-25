#include <stdio.h>
#include <sys/msg.h>
#include <sys/stat.h>
#include <errno.h>

#define BUF_SIZE 30
#define ERROR if(errno) { \
    printf("%d: pid %ld errno: - %d - (%s)\n", 1, (long)getpid(), errno, strerror(errno)); \
    exit(1); \
}

struct message {
    long mtype;
    char mtext[BUF_SIZE];
};

int main() {
    int id, n;
    char buf[BUF_SIZE];
    
    struct msqid_ds myqueue;

    if ((id = msgget(1234, IPC_CREAT | 0644)) == -1)
        ERROR;
    if(msgctl(id, IPC_RMID, &myqueue) == -1) {
        ERROR;
    } else 
        printf("Coda deallocata \n");
    if ((id = msgget(1234, IPC_CREAT | 0644)) == -1)
        ERROR;
    
   
    printf("inserire un tipo (intero): ");
    scanf("%d", &n);
    printf("inserire un messaggio (stop per terminare): ");
    scanf("%s", buf);

    while (strcmp(buf, "stop") != 0) { 
        struct message msg;
        msg.mtype = n;
        sprintf(msg.mtext, "%s", buf);

        if(msgsnd(id, &msg, (sizeof(msg) - sizeof(long)), IPC_NOWAIT) == -1)
            ERROR;
        
        printf("inserire un tipo (intero): ");
        scanf("%d", &n);
        printf("inserire un messaggio (stop per terminare): ");
        scanf("%s", buf);
    } 
}