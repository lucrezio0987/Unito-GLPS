#include "lib_header.h"
#include "lib_movimento.h"

int main(int argc, char* argv[]) {
    setbuf(stdout, NULL);
    loadIPCs();

    /* --- Variabili locali ---  */
        int caricata, scaricata;
        double d; /* durata viaggio */
        int i,j;
        int exec = 0;
    /* --- Apertura IPC --- */

    if (set_sem(semPila    , 0, var->n_navi) == -1) ERROR;
    if (set_sem(semPila    , 1, 1          ) == -1) ERROR;
    if (set_sem(semPidNavi , 0, 1          ) == -1) ERROR;
    
    message.m_type = 1;
    signal(SIGTERM, h_meteo);

    switch ((var -> pidMovimento = newProcess())) {
        case -1: ERROR;
        case 0:
            signal(SIG_TEMPESTA, h_meteo);
            signal(SIG_MAELSTROM, h_meteo);
            for (;;++exec) {
                if(var -> flagTerminazione != 0) endProcess();
                p_rand = rand() % (var -> n_porti);
                nave = pop();
                message.n = nave;
                switch ((pidNave = newProcess())){
                    case -1: printf("Errore pidMovimento (exec = %d)\n", exec); break;
                    case 0:
                        attShm();{
                            d = viaggio(nave, p_rand);             /*VIAGGIO*/
                        } detShm();
                        controlloSegnali(C_MAELSTROM);
                        
                        easynanosleep(d);

                        controlloSegnali(C_TEMPESTA | C_MAELSTROM);
                        attShm();{
                                navi[nave].pos[X] = porti[p_rand].pos[X];   
                                navi[nave].pos[Y] = porti[p_rand].pos[Y];
                        }detShm(); 
                        controlloSegnali(C_MAELSTROM);

                        if(semctl(banchine, p_rand, GETVAL) == 0) {   /*Se non i sono banchine libere non fare nulla*/ 
                            if ((msgsnd(msgPila, &message, (sizeof(message)-sizeof(long)), IPC_NOWAIT)) == -1) ERROR;
                            endProcess();
                        }

                        reserveSem(banchine, p_rand);                   /*Attesa banchina libera*/
                        controlloSegnali(C_MAREGGIATA | C_MAELSTROM);
                        
                        attShm();{
                            ++porti[p_rand].banchine_occupate;
                            navi[nave].in_mare = FALSE;
                            if((scaricata = scarico(nave, p_rand)) < 0) printf("Errore Scaricata = %d\n", scaricata); /**/
                            if((caricata  = carico (nave, p_rand)) < 0) printf("Errore Caricata = %d\n", caricata); /**/
                        } detShm();      

                        controlloSegnali(C_MAREGGIATA | C_MAELSTROM);
                        easynanosleep((scaricata + caricata) / var->SO_loadspeed);
                        controlloSegnali(C_MAREGGIATA | C_MAELSTROM);
                        
                        attShm();{
                            releaseSem(banchine, p_rand);              /*Lascia banchina*/
                            --porti[p_rand].banchine_occupate;
                            navi[nave].in_mare = TRUE;
                        }detShm();  
                                                                        
                        controlloSegnali(C_MAELSTROM);
                        if ((msgsnd(msgPila, &message, (sizeof(message)-sizeof(long)), IPC_NOWAIT)) == -1) ERROR;
            
                        controlloSegnali(C_MAELSTROM);                       
                        endProcess();
                    default: if(flagTempesta==1) --flagTempesta;
                            aggiornaPidNave(nave, pidNave);
                }
            } ERROR;
        default: break;
    }

    for (;var -> flagTerminazione == 0 || var->fork_navi > 0;) {
        msgrcv(msgPila, &message, (sizeof(message)-sizeof(long)), 0, 0);
            if(message.m_type == 1){
                    push(message.n);
                    aggiornaPidNave(message.n, 0);
            } else if(message.m_type == 2){
                    aggiornaPidNave(nave, -1); 
            }
    }
    ERROR;
}