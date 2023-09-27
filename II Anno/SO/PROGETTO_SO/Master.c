#include "lib_header.h"
#include "lib_master.h"

int main() {
    pid_t pidMovimento, pidGiornaliera, pidMaelstrom;
    int i;

    setbuf(stdout, NULL);
    srand(time(NULL));

    printf(":::::::::::::::::::::::::::::::::\n");
    printf(":::  PROCESSO Master - Start  :::\n");
    printf(":::::::::::::::::::::::::::::::::\n::\n");
    
    createIPCs("Progetto.conf");

    if(set_sem(semShm, 0, 1) == -1) ERROR;
    printf(":::::::::::::::::::::::::::\n");
    printf(":::  CREAZIONE MONDO    :::\n");
    printf(":::::::::::::::::::::::::::\n");

    attShm(); {
        if (creazione_porti() == -1) ERROR;     printf(":: >>> Porti creati (%d)\n", var->n_porti);
        if (creazione_navi () == -1) ERROR;     printf(":: >>> Navi  create (%d)\n", var->n_navi);
        if (creazione_merci() == -1) ERROR;     printf(":: >>> Merci create (%d)\n", SO_MERCI);

        if (stampa(INIZIALE)  == -1) ERROR;
    } detShm();

    printf("::::::::::::::::::::::::::::::::::::\n");
    printf(":::  PROCESSO Movimento - Start  :::\n");
    printf("::::::::::::::::::::::::::::::::::::\n::\n");
    
    switch ((pidMovimento = fork())) {
        case -1:    ERROR;
        case  0:    execl("./Movimento", "./Movimento", NULL);
                    printf("Movimento non avviato correttamente\n");                         
                    ERROR;
        default:    break;
    }

    printf("::::::::::::::::::::::::::::::::::::\n");
    printf(":::  PROCESSO Maelstrom - Start  :::\n");
    printf("::::::::::::::::::::::::::::::::::::\n::\n");

    switch ((pidMaelstrom = fork())) {
        case -1:    ERROR;
        case  0:    if(var->setOnMaelstrom == FALSE) exit(0);
                    execl("./Maelstrom", "./Maelstrom", NULL); 
                    printf("Maelstrom non avviato correttamente\n");                         
                    ERROR;
                    exit(0);
        default:    break;
    }
    
    printf("::::::::::::::::::::::::::::::::::::::\n");
    printf(":::  PROCESSO Giornaliera - Start  :::\n");
    printf("::::::::::::::::::::::::::::::::::::::\n::\n");

    switch ((pidGiornaliera = fork())) {
        case -1:    ERROR;
        case  0:    execl("./Giornaliera", "./Giornaliera", NULL); 
                    printf("Giornaliera non avviato correttamente\n");
                    kill(pidMovimento, SIGTERM);
                    ERROR;
        default:    waitpid(pidGiornaliera, NULL, 0);
    } 

    kill(pidMovimento, SIGTERM);
    kill(pidMaelstrom, SIGTERM);
    ++var->flagTerminazione; 

    for(i = 0;semctl(semProcessi, 0, GETVAL) != 0; ++i){
        sleep(1);
        printf(":: PROCESSI - ancora attivi: %d\n", semctl(semProcessi, 0, GETVAL));
        if(i>5){
            printf(":: ERRORE - TERMINAZIONE FORZATA\n");
            killpg(getpid(), SIGKILL);
        }
    }
    
    --var->flagTerminazione;
    attShm();{
        if (stampa(FINALE) == -1) ERROR;
    }detShm();

    deallocIPCs();

    printf("::\n:::::::::::::::::::::::::::::::::::::\n");
    printf(":::  PROCESSO Master - Terminato  :::\n");
    printf(":::::::::::::::::::::::::::::::::::::\n");
        
    exit(0);
}