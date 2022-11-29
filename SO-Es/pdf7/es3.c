#include <stdio.h>
#include "lib_sem.h"

int main(int argc, char* argv[]) {
    int N, M;

    if(argc != 3) ERROR;
    N=atoi(argv[1]);
    M=atoi(argv[2]);

    //TODO: SHM
    //TODO: creazione N figli
        //cicla M volte
        //sleep-ran[0..9]
        //monta SM - scrive PID - smonta SM
    //TODO: padre
        //sleep(5)
        //monta SM - stampa PID - smonta SM
    //wait() N figli
    

    exit(0);
}