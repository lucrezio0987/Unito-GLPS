#include <stdio.h>
#include <signal.h>

void (*old_header1) (int);
void (*old_header2) (int);

void header1(int sig) {
    (*old_header2) (SIGINT);
}

void header2(int sig) {
    (*old_header1) (SIGTSTP);
}

int main() {  
    old_header1 = signal(SIGTSTP, header1);
    old_header2 = signal(SIGINT, header2);
        
    if(old_header1 == SIG_ERR)
        printf("error oldheader1\n");
    if(old_header2 == SIG_ERR)
        printf("error oldheader2\n");
    for(;;);
    exit(0);
}
