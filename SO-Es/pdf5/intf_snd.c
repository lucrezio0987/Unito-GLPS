#include <stdio.h>
#include <sys/msg.h>
#include <sys/stat.h>
#include <errno.h>

#define BUF_SIZE 30
#define ERROR if(errno) { \
    printf("%d: pid %ld (%s)\n", 1, (long)getpid(), errno, strerror(errno)); \
    exit(1); \
}

struct message {
    long mtype;
    char mtext[BUF_SIZE];
    int 
}

int main() {
    int id, n;
    char buf[BUF_SIZE];

    if ((id = msgget(IPC_PRIVATE, IPC_CREAT | 0600)) == -1)
        ERROR;

    printf("inserire un tipo (intero): \n");
    scanf("%d", &n);
    printf("inserire un messaggio (stop per terminare): \n");
    scanf("%s", buf);

    while (strcmp(buf, "stop") != 0) { 
        struct message msg = {n, buf};
    } 
}