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
    
   // for(;;)
     {
        msgctl(id, IPC_STAT, "myqueue");


        if(myqueue.msg_qnum != 0) {   
            
            if(msgrcv(id, &messageC, (sizeof(messageC) - sizeof(long)), 1, MSG_NOERROR) == -1)
                ERROR;

            switch (fork()) {
                case -1:
                    ERROR;
                
                case 0:
                    messageS.mtype = messageC.clientPID;
                    sprintf(messageS.mtext, "\e033[31m %s", messageC.mtext);
                    
                    if(msgsnd(id, &messageS, (sizeof(messageS) - sizeof(long)), IPC_NOWAIT) == -1)
                        ERROR;
                    exit(0);

                default:
                    wait();
                    break;
            }
        
        if(msgctl(id, IPC_RMID, &myqueue) == -1) {
            ERROR;
        } else 
            printf("Coda deallocata \n");
        exit(0);
            
        }
    }
}