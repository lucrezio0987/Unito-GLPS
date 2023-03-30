#include <stdio.h>
#include <unistd.h>
#include <signal.h>
#include <errno.h>

int main() {
    int p;
    
    printf("Inserire un intero: ");
    scanf("%d", &p);

    kill(p, 0);
    if(errno == ESRCH)
        printf("processo non esistente\n");
    else if (errno == EPERM)
        printf("accesso non consentito\n");
    else 
        printf("errno error\n");
}