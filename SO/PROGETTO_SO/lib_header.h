#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <ctype.h>
#include <sys/ipc.h>
#include <sys/msg.h>
#include <sys/shm.h>
#include <sys/sem.h>
#include <sys/wait.h>
#include <sys/types.h>
#include <signal.h>
#include <errno.h>
#include <time.h>
#include <math.h>

#include "Oggetti.h"

#define PERMISSIONS 0744
#define FTOK_FILE "Master.c"

#define ERROR                                                                                                                              \
    if (errno){                                                                                                                            \
    fprintf(stderr, "ERROR - line %d: file \"%s\" (pid %ld) - n %d - (%s)\n", __LINE__, __FILE__, (int)getpid(), errno, strerror(errno));  \
        exit(1);                                                                                                                           \
    }

union semun {
    int val;
    struct semid_ds *buf;
    short *array;
    struct seminfo *__buf;
    void *__pad;
};

int reserveSem(int id_sem, int n_sem) {
    struct sembuf s_ops;

    s_ops.sem_num = n_sem;
    s_ops.sem_op = -1;
    s_ops.sem_flg = 0;

    return semop(id_sem, &s_ops, 1);
}

int releaseSem(int id_sem, int n_sem) {
    struct sembuf s_ops;
    s_ops.sem_num = n_sem;
    s_ops.sem_op = 1;
    s_ops.sem_flg = 0;

    return semop(id_sem, &s_ops, 1);
}

int set_sem(int semID, int semNum, int val) {
    union semun arg;
    arg.val = val;
    return semctl(semID, semNum, SETVAL, arg);
}

pid_t newProcess() {
    releaseSem(semProcessi, 0);
    return fork();
}

void endProcess() {
    reserveSem(semProcessi, 0);
    exit(0);
}

double easynanosleep(double time) {
    sleep(time/1000);
    return (double)0;
}

int stampaIniziale() {
    int i;
        fprintf(out_progetto, "╔══════════════════════╗\n");
        fprintf(out_progetto, "║  RESOCONTO INIZIALE  ║\n");
        fprintf(out_progetto, "╠══════════════════════╝\n");
        fprintf(out_progetto, "║┌─────────────╖\n");
        fprintf(out_progetto, "╠╡ PORTI (%3d) ║\n", var->n_porti);
        fprintf(out_progetto, "║└─────────────╜\n");
        fprintf(out_progetto, "║ ┌────────────────────────────────────────────────────┐\n");
    for (i = 0; i < var->n_porti; ++i)
        fprintf(out_progetto, "║ │ Porto %3d: [%7.2f, %7.2f]  -> n. banchine: %2d  │\n", i + 1, porti[i].pos[X], porti[i].pos[Y], porti[i].banchine);
        fprintf(out_progetto, "║ └────────────────────────────────────────────────────┘\n");
        fprintf(out_progetto, "║\n");
        fprintf(out_progetto, "║┌─────────────╖\n");
        fprintf(out_progetto, "╠╡ MERCI (%3d) ║\n", SO_MERCI);
        fprintf(out_progetto, "║└─────────────╜\n");
        fprintf(out_progetto, "║ ┌─────────────────────────────────────────┐\n");
    for (i = 0; i < SO_MERCI; ++i)
        fprintf(out_progetto, "║ │ Merce %s ->  ton: %3d , t_vita:%3d   │\n", merci[i].t_merce, merci[i].ton, merci[i].t_vita);
        fprintf(out_progetto, "║ └─────────────────────────────────────────┘\n");
        fprintf(out_progetto, "║\n");
        fprintf(out_progetto, "║┌─────────────╖\n");
        fprintf(out_progetto, "╠╡ NAVI (%4d) ║\n", var->n_navi);
        fprintf(out_progetto, "║└─────────────╜\n");
        fprintf(out_progetto, "║ ┌──────────────────────────────┬───────────────────┐\n");
    for (i = 0; i < var->n_navi; ++i)
        fprintf(out_progetto, "║ │ Nave %3d: [%7.2f, %7.2f] │  Max_ton = %5d  │\n", i + 1, navi[i].pos[X], navi[i].pos[Y], navi[i].capacita);
        fprintf(out_progetto, "║ └──────────────────────────────┴───────────────────┘\n");
        fprintf(out_progetto, "╚════\n\n");
    return 0;
}

int stampaGiornaliera() { if(var->setOnStampaGiornaliera == FALSE) return 0;
    int i, j;
    int presente_navi, presente_porti, scaduta_porti, scaduta_navi, merce_consegnata, merce_persa;
    int in_mare_no_cariche=0, in_mare_cariche=0, in_porto=0, rallentate_da_tempesta=0, distrutte=0;

        fprintf(out_progetto, "║┌───────╖\n");
        fprintf(out_progetto, "╠╡ MERCI ║\n");
        fprintf(out_progetto, "║└───────╜\n");
    for (i = 0; i < SO_MERCI; ++i) {
        for (j = 0, presente_navi = 0, scaduta_navi = 0, merce_persa = 0; j < var->n_navi; ++j) { 
                scaduta_navi+=navi[j].scaduta[i];
                if(navi[j].pidNavi != -1) presente_navi+=navi[j].stiva[i];
                else                      merce_persa+=navi[j].stiva[i];
        }
        for (j = 0, presente_porti = 0, scaduta_porti = 0, merce_consegnata = 0; j < var->n_porti; ++j) {
            presente_porti+=porti[j].offerta[i]; 
            scaduta_porti+= porti[j].scaduta[i]; 
            merce_consegnata += porti[j].ricevuta[i];
        }

        fprintf(out_progetto, "║ ┌─────────────┐\n");
        fprintf(out_progetto, "║ │ Merce %s │\n", merci[i].t_merce);
        fprintf(out_progetto, "║ ├─────────────┴──────────────────────────────────────────────────┐\n");
        fprintf(out_progetto, "║ │ IN PORTO --> %8d ton │  SCADUTA IN PORTO --> %8d ton │\n", presente_porti, scaduta_porti);
        fprintf(out_progetto, "║ ├───────────────────────────┼────────────────────────────────────┤\n");
        fprintf(out_progetto, "║ │ IN NAVE  --> %8d ton │  SCADUTA IN NAVE  --> %8d ton │\n", presente_navi, scaduta_navi);
        fprintf(out_progetto, "║ ├───────────────────────────┴────────────────────────────────────┤\n");
        fprintf(out_progetto, "║ │ RIMASTA FERMA IN PORTO  --> %8d ton                       │\n", presente_porti + scaduta_porti);
        fprintf(out_progetto, "║ └────────────────────────────────────────────────────────────────┘\n");
    }
        fprintf(out_progetto, "║┌───────╖\n");
        fprintf(out_progetto, "╠╡ PORTI ║\n");
        fprintf(out_progetto, "║└───────╜\n");
    for (i = 0; i < var->n_porti; ++i) {
        fprintf(out_progetto, "║ ┌───────────────────────────────┐\n");
        fprintf(out_progetto, "║ │ Porto %3d [%7.2f, %7.2f]  │\n", i + 1, porti[i].pos[X], porti[i].pos[Y]);

        fprintf(out_progetto, "║ ├───────────┬─────────────┬─────┴───────┬─────────────┬──────────────┬──────────────────┐\n");
        fprintf(out_progetto, "║ │  T_Merce  │   OFFERTA   │   DOMANDA   │   SPEDITA   │   RICEVUTA   │  T_vita_default  │\n");
        fprintf(out_progetto, "║ ├───────────┼─────────────┼─────────────┼─────────────┼──────────────┼──────────────────┤\n");
        for (j = 0; j < SO_MERCI; ++j)
        fprintf(out_progetto, "║ │   %s   │  %8d   │  %8d   │  %8d   │   %8d   │       %3d        │\n", 
                       merci[j].t_merce,
                       porti[i].offerta[j],
                       porti[i].domanda[j],
                       porti[i].spedita[j],
                       porti[i].ricevuta[j],
                       merci[j].t_vita);
        fprintf(out_progetto, "║ └─┬─────────┴─────────────┴───────────┬─┴─────────────┴──────────────┴──────────────────┘\n");
        fprintf(out_progetto, "║   │  Banchine:                        │\n");
        fprintf(out_progetto, "║   │      - Occupate:   %3d            │\n", porti[i].banchine_occupate);
        fprintf(out_progetto, "║   │      - Libere:     %3d            │\n", porti[i].banchine - porti[i].banchine_occupate);
        fprintf(out_progetto, "║   └───────────────────────────────────┘\n");
    }

    for (i = 0, j = 0; i < var->n_navi; ++i) {
        if(navi[i].pidNavi != -1) {
            if (navi[i].carica == TRUE && navi[i].in_mare == TRUE)      ++in_mare_cariche;
            if (navi[i].carica == FALSE && navi[i].in_mare == TRUE)     ++in_mare_no_cariche;
            if (navi[i].in_mare == FALSE )                              ++in_porto;
            if (navi[i].rallentata_da_tempesta > 0)                     ++rallentate_da_tempesta;
        } else                                                          ++distrutte;
    }
        fprintf(out_progetto, "║┌──────╖\n");
        fprintf(out_progetto, "╠╡ NAVI ║\n");
        fprintf(out_progetto, "║└──────╜\n");
        fprintf(out_progetto, "║ ┌───────────────────────────────────────────────────┬──────┐\n");
        fprintf(out_progetto, "║ │ Numero di navi in mare, con carico a bordo        │ %4d │\n", in_mare_cariche);
        fprintf(out_progetto, "║ ├───────────────────────────────────────────────────┼──────┤\n");
        fprintf(out_progetto, "║ │ Numero di navi in mare, senza carico a bordo      │ %4d │\n", in_mare_no_cariche);
        fprintf(out_progetto, "║ ├───────────────────────────────────────────────────┼──────┤\n");
        fprintf(out_progetto, "║ │ Numero di navi ferme in porto                     │ %4d │\n", in_porto);
        fprintf(out_progetto, "║ ├───────────────────────────────────────────────────┼──────┤\n");
        fprintf(out_progetto, "║ │ Numero di navi rallentate da tempesta             │ %4d │\n", rallentate_da_tempesta);
        fprintf(out_progetto, "║ ├───────────────────────────────────────────────────┼──────┤\n");
        fprintf(out_progetto, "║ │ Numero di navi distrutte                          │ %4d │\n", distrutte);
        fprintf(out_progetto, "║ └───────────────────────────────────────────────────┴──────┘\n");

    return 0;
}

int stampaFinale() {
    int i, j, max;
    int presente_navi, presente_porti, scaduta_porti, scaduta_navi, merce_consegnata, merce_persa;
    int in_mare_no_cariche=0, in_mare_cariche=0, in_porto=0, rallentate_da_tempesta=0, distrutte=0;

        fprintf(out_progetto, "╔════════════════════╗\n");
        fprintf(out_progetto, "║  RESOCONTO FINALE  ║\n");
        fprintf(out_progetto, "╠════════════════════╝\n");
        fprintf(out_progetto, "║┌───────╖\n");
        fprintf(out_progetto, "╠╡ PORTI ║\n");
        fprintf(out_progetto, "║└───────╜\n");
        fprintf(out_progetto, "║ ┌─────────────────────────────────────────────────────────────────────┐\n");
    for (i = 0, max = 0; i < var->n_porti; ++i) if (porti[i].merce_offerta > max) max = porti[i].merce_offerta;
    for (i = 0; i < var->n_porti; ++i) if (porti[i].merce_offerta == max)
        fprintf(out_progetto, "║ │ Porto %3d ha offerto la quantità maggiore di merce (%8d ton)   │\n", i + 1, max);
        fprintf(out_progetto, "║ ├─────────────────────────────────────────────────────────────────────┤\n");
    for (i = 0, max = 0; i < var->n_porti; ++i) if (porti[i].merce_ricevuta > max) max = porti[i].merce_ricevuta;
    for (i = 0; i < var->n_porti; ++i) if (porti[i].merce_ricevuta == max)
        fprintf(out_progetto, "║ │ Porto %3d ha ricevuta la quantità maggiore di merce (%8d ton)  │\n", i + 1, max);
        fprintf(out_progetto, "║ ├──────────────────────────────────────────────────────────────┬──────┤\n");
    for (i = 0, j=0; i < var->n_porti; ++i) j += porti[i].colpito_da_mareggiata;
        fprintf(out_progetto, "║ │ Porti colpiti da mareggiata                                  │ %4d │\n", j);
        fprintf(out_progetto, "║ └──────────────────────────────────────────────────────────────┴──────┘\n");
        fprintf(out_progetto, "║\n");

    for (i = 0; i < var->n_porti; ++i) {
        fprintf(out_progetto, "║ ┌───────────────────────────────┐\n");
        fprintf(out_progetto, "║ │ Porto %3d [%7.2f, %7.2f]  │\n", i + 1, porti[i].pos[X], porti[i].pos[Y]);

        fprintf(out_progetto, "║ ├───────────┬─────────────┬─────┴───────┬─────────────┬──────────────┬──────────────────┐\n");
        fprintf(out_progetto, "║ │  T_Merce  │   OFFERTA   │   DOMANDA   │   SPEDITA   │   RICEVUTA   │  T_vita_default  │\n");
        fprintf(out_progetto, "║ ├───────────┼─────────────┼─────────────┼─────────────┼──────────────┼──────────────────┤\n");
        for (j = 0; j < SO_MERCI; ++j)
        fprintf(out_progetto, "║ │   %s   │  %8d   │  %8d   │  %8d   │   %8d   │       %3d        │\n", 
                       merci[j].t_merce,
                       porti[i].offerta[j],
                       porti[i].domanda[j],
                       porti[i].spedita[j],
                       porti[i].ricevuta[j],
                       merci[j].t_vita);
        fprintf(out_progetto, "║ └─┬─────────┴─────────────┴───────────┬─┴─────────────┴──────────────┴──────────────────┘\n");
        fprintf(out_progetto, "║   │  Banchine:                        │\n");
        fprintf(out_progetto, "║   │      - Occupate:   %3d            │\n", porti[i].banchine_occupate);
        fprintf(out_progetto, "║   │      - Libere:     %3d            │\n", porti[i].banchine - porti[i].banchine_occupate);
        fprintf(out_progetto, "║   └───────────────────────────────────┘\n");
    }

    for (i = 0, j = 0; i < var->n_navi; ++i) {
        if(navi[i].pidNavi != -1) {
            if (navi[i].carica == TRUE && navi[i].in_mare == TRUE)      ++in_mare_cariche;
            if (navi[i].carica == FALSE && navi[i].in_mare == TRUE)     ++in_mare_no_cariche;
            if (navi[i].in_mare == FALSE )                              ++in_porto;
            if (navi[i].rallentata_da_tempesta > 0)                     ++rallentate_da_tempesta;
        } else                                                          ++distrutte;
    }
        fprintf(out_progetto, "║┌──────╖\n");
        fprintf(out_progetto, "╠╡ NAVI ║\n");
        fprintf(out_progetto, "║└──────╜\n");
        fprintf(out_progetto, "║ ┌───────────────────────────────────────────────────┬──────┐\n");
        fprintf(out_progetto, "║ │ Numero di navi in mare, con carico a bordo        │ %4d │\n", in_mare_cariche);
        fprintf(out_progetto, "║ ├───────────────────────────────────────────────────┼──────┤\n");
        fprintf(out_progetto, "║ │ Numero di navi in mare, senza carico a bordo      │ %4d │\n", in_mare_no_cariche);
        fprintf(out_progetto, "║ ├───────────────────────────────────────────────────┼──────┤\n");
        fprintf(out_progetto, "║ │ Numero di navi ferme in porto                     │ %4d │\n", in_porto);
        fprintf(out_progetto, "║ ├───────────────────────────────────────────────────┼──────┤\n");
        fprintf(out_progetto, "║ │ Numero di navi rallentate da tempesta             │ %4d │\n", rallentate_da_tempesta);
        fprintf(out_progetto, "║ ├───────────────────────────────────────────────────┼──────┤\n");
        fprintf(out_progetto, "║ │ Numero di navi distrutte                          │ %4d │\n", distrutte);
        fprintf(out_progetto, "║ └───────────────────────────────────────────────────┴──────┘\n");

        fprintf(out_progetto, "║┌───────╖\n");
        fprintf(out_progetto, "╠╡ MERCI ║\n");
        fprintf(out_progetto, "║└───────╜\n");
    for (i = 0; i < SO_MERCI; ++i) {       
            for (j = 0, presente_navi = 0, scaduta_navi = 0, merce_persa = 0; j < var->n_navi; ++j) { 
                scaduta_navi+=navi[j].scaduta[i];
                if(navi[j].pidNavi != -1) presente_navi+=navi[j].stiva[i];
                else                      merce_persa+=navi[j].stiva[i];
            }
            for (j = 0, presente_porti = 0, scaduta_porti = 0, merce_consegnata = 0; j < var->n_porti; ++j) {
                presente_porti+=porti[j].offerta[i]; 
                scaduta_porti+= porti[j].scaduta[i]; 
                merce_consegnata += porti[j].ricevuta[i];
            }
        
        fprintf(out_progetto, "║ ┌─────────────┐\n");
        fprintf(out_progetto, "║ │ Merce %s │\n", merci[i].t_merce);
        fprintf(out_progetto, "║ ├─────────────┴──────────────────────────────────────────────────┐\n");
        fprintf(out_progetto, "║ │ MERCE TOTALE GENERATA   --> %8d ton                       │\n", presente_navi + presente_porti+ scaduta_navi + scaduta_porti + merce_consegnata + merce_persa);
        fprintf(out_progetto, "║ ├───────────────────────────┬────────────────────────────────────┤\n");
        fprintf(out_progetto, "║ │ IN PORTO --> %8d ton │  SCADUTA IN PORTO --> %8d ton │\n", presente_porti, scaduta_porti);
        fprintf(out_progetto, "║ ├───────────────────────────┼────────────────────────────────────┤\n");
        fprintf(out_progetto, "║ │ IN NAVE  --> %8d ton │  SCADUTA IN NAVE  --> %8d ton │\n", presente_navi, scaduta_navi);
        fprintf(out_progetto, "║ ├───────────────────────────┴────────────────────────────────────┤\n");
        fprintf(out_progetto, "║ │ RIMASTA FERMA IN PORTO  --> %8d ton                       │\n", presente_porti + scaduta_porti);
        fprintf(out_progetto, "║ ├────────────────────────────────────────────────────────────────┤\n");
        fprintf(out_progetto, "║ │ CARICATA DA UNA NAVE    --> %8d ton                       │\n", merce_consegnata + presente_navi + scaduta_navi + merce_persa);
        fprintf(out_progetto, "║ └────────────────────────────────────────────────────────────────┘\n");
    }
        fprintf(out_progetto, "╚════\n\n");
    return 0;
}

int stampa(int flagStampa) {
    switch (flagStampa) {
    case INIZIALE:    return stampaIniziale();
    case GIORNALIERA: return stampaGiornaliera();
    case FINALE:      return stampaFinale();
    default:          return -1;
    }
}

void attShm() {
    reserveSem(semShm, 0);
    if(var -> flagTerminazione == 0) {
        if((merci = shmat(shmMerci, NULL, 0)) == ( Merce* ) -1) ERROR;
        if((porti = shmat(shmPorti, NULL, 0)) == ( Porto* ) -1) ERROR;
        if((navi  = shmat(shmNavi,  NULL, 0)) == ( Nave*  ) -1) ERROR;
    } else endProcess();
    return;
}

void detShm() {
    if(var -> flagTerminazione == 0) {
        if((shmdt(merci)) == -1) ERROR;
        if((shmdt(porti)) == -1) ERROR;
        if((shmdt(navi )) == -1) ERROR;
    }
    releaseSem(semShm, 0);
    return;
}

void createIPCs(char* file) {
    char temp[20], boolean[6];
    int SO_navi, SO_porti, i;

    if ((shmVar  = shmget(ftok(FTOK_FILE, 'v'), sizeof(Var), IPC_CREAT | IPC_EXCL | PERMISSIONS)) == -1) ERROR;
    if ((var     = shmat(shmVar, NULL, 0)) == (void *) -1) ERROR;
    
    out_progetto = fopen("Progetto.out", "w");
    in_progetto  = fopen(file, "r");
        setbuf(out_progetto, NULL);
        fprintf(out_progetto, "******************************************************************************\n");
        fprintf(out_progetto, "*   START: Inizio esecuzione progetto di Sistemi Operativi (2022/2023)       *\n");
        fprintf(out_progetto, "*          di                                                                *\n");
        fprintf(out_progetto, "*          Bergesio Simone, Del Ponte Lucrezio, Grillo Giovanni              *\n");
        fprintf(out_progetto, "******************************************************************************\n");
                                                                        fprintf(out_progetto, "*  %s %d\n","SO_MERCI:", SO_MERCI);
                                                                        fprintf(out_progetto, "*  %s %d\n","SO_DAYS:", SO_DAYS);
        fscanf(in_progetto, "%s %d\n",temp, &var->SO_lato);             fprintf(out_progetto, "*  %s %d\n",temp, var->SO_lato);
        fscanf(in_progetto, "%s %d\n",temp, &SO_navi);                  fprintf(out_progetto, "*  %s %d\n",temp, SO_navi);
        fscanf(in_progetto, "%s %d\n",temp, &SO_porti);                 fprintf(out_progetto, "*  %s %d\n",temp, SO_porti);
        fscanf(in_progetto, "%s %d\n",temp, &var->SO_size);             fprintf(out_progetto, "*  %s %d\n",temp, var->SO_size);
        fscanf(in_progetto, "%s %d\n",temp, &var->SO_fill);             fprintf(out_progetto, "*  %s %d\n",temp, var->SO_fill);
        fscanf(in_progetto, "%s %d\n",temp, &var->SO_min_vita);         fprintf(out_progetto, "*  %s %d\n",temp, var->SO_min_vita);
        fscanf(in_progetto, "%s %d\n",temp, &var->SO_max_vita);         fprintf(out_progetto, "*  %s %d\n",temp, var->SO_max_vita);
        fscanf(in_progetto, "%s %d\n",temp, &var->SO_banchine);         fprintf(out_progetto, "*  %s %d\n",temp, var->SO_banchine);
        fscanf(in_progetto, "%s %d\n",temp, &var->SO_capacity);         fprintf(out_progetto, "*  %s %d\n",temp, var->SO_capacity);
        fscanf(in_progetto, "%s %d\n",temp, &var->SO_speed);            fprintf(out_progetto, "*  %s %d\n",temp, var->SO_speed);
        fscanf(in_progetto, "%s %d\n",temp, &var->SO_loadspeed);        fprintf(out_progetto, "*  %s %d\n",temp, var->SO_loadspeed);
        fscanf(in_progetto, "%s %d\n",temp, &var->SO_storm_duration);   fprintf(out_progetto, "*  %s %d\n",temp, var->SO_storm_duration);
        fscanf(in_progetto, "%s %d\n",temp, &var->SO_swell_duration);   fprintf(out_progetto, "*  %s %d\n",temp, var->SO_swell_duration);
        fscanf(in_progetto, "%s %d\n",temp, &var->SO_maelstrom);        fprintf(out_progetto, "*  %s %d\n",temp, var->SO_maelstrom);
        fscanf(in_progetto, "\n");                                      fprintf(out_progetto, "*  \n");
        fscanf(in_progetto, "%s = %s\n",temp, boolean);                 if(strcmp(boolean,"TRUE") == 0) i=TRUE; else i=FALSE; var->setOnMaraggiata = i;
                                                                        fprintf(out_progetto, "*  %s = %s(%d)\n",temp, boolean, var->setOnMaraggiata);
        fscanf(in_progetto, "%s = %s\n",temp, boolean);                 if(strcmp(boolean,"TRUE") == 0) i=TRUE; else i=FALSE; var->setOnTempesta = i;
                                                                        fprintf(out_progetto, "*  %s = %s(%d)\n",temp, boolean, var->setOnTempesta);
        fscanf(in_progetto, "%s = %s\n",temp, boolean);                 if(strcmp(boolean,"TRUE") == 0) i=TRUE; else i=FALSE; var->setOnStampaGiornaliera = i;
                                                                        fprintf(out_progetto, "*  %s = %s(%d)\n",temp, boolean, var->setOnStampaGiornaliera);
        fscanf(in_progetto, "%s = %s\n",temp, boolean);                 if(strcmp(boolean,"TRUE") == 0) i=TRUE; else i=FALSE; var->setOnMaelstrom = i;
                                                                        fprintf(out_progetto, "*  %s = %s(%d)\n",temp, boolean, var->setOnMaelstrom);
        fprintf(out_progetto, "******************************************************************************\n\n");
    
    fclose(in_progetto);
    fclose(out_progetto);
    var->n_navi = 1 + rand() % (SO_navi);
    var->n_porti= 4 + rand() % (SO_porti - 4 + 1);

    if ((shmPorti      = shmget(ftok(FTOK_FILE, 'p'), sizeof(Porto) * var->n_porti      , IPC_CREAT | IPC_EXCL | PERMISSIONS)) == -1) ERROR;
    if ((shmNavi       = shmget(ftok(FTOK_FILE, 'n'), sizeof(Nave)  * var->n_navi       , IPC_CREAT | IPC_EXCL | PERMISSIONS)) == -1) ERROR;
    if ((shmMerci      = shmget(ftok(FTOK_FILE, 'm'), sizeof(Merce) * SO_MERCI          , IPC_CREAT | IPC_EXCL | PERMISSIONS)) == -1) ERROR;
    if ((shmPila       = shmget(ftok(FTOK_FILE, 'i'), sizeof(int)   * (var->n_navi + 1) , IPC_CREAT | IPC_EXCL | PERMISSIONS)) == -1) ERROR;
    if ((semShm        = semget(ftok(FTOK_FILE, 'a'), 1                                 , IPC_CREAT | IPC_EXCL | PERMISSIONS)) == -1) ERROR;
    if ((semPidNavi    = semget(ftok(FTOK_FILE, 'f'), 1                                 , IPC_CREAT | IPC_EXCL | PERMISSIONS)) == -1) ERROR;
    if ((semPila       = semget(ftok(FTOK_FILE, 's'), 2                                 , IPC_CREAT | IPC_EXCL | PERMISSIONS)) == -1) ERROR;
    if ((msgPila       = msgget(ftok(FTOK_FILE, 'g'),                                     IPC_CREAT | IPC_EXCL | PERMISSIONS)) == -1) ERROR;
    if ((semMareggiata = semget(ftok(FTOK_FILE, 'x'), var->n_porti                      , IPC_CREAT | IPC_EXCL | PERMISSIONS)) == -1) ERROR;
    if ((banchine      = semget(ftok(FTOK_FILE, 'z'), var->n_porti                      , IPC_CREAT | IPC_EXCL | PERMISSIONS)) == -1) ERROR;
    if ((semProcessi   = semget(ftok(FTOK_FILE, 't'), 1                                 , IPC_CREAT | IPC_EXCL | PERMISSIONS)) == -1) ERROR;

    out_progetto = fopen("Progetto.out", "a");
    setbuf(out_progetto, NULL);

    return;
}

void deallocIPCs() {
    int i;
    reserveSem(semShm, 0);
                                                                printf("::::::::::::::::::::::::::::::::::::::\n");
    if (shmctl(shmVar        , IPC_RMID, 0) == -1) { ERROR; } else printf(":::  shmVar        :   deallocati  :::\n");
    if (shmctl(shmPorti      , IPC_RMID, 0) == -1) { ERROR; } else printf(":::  shmPorti      :   deallocati  :::\n");
    if (shmctl(shmNavi       , IPC_RMID, 0) == -1) { ERROR; } else printf(":::  shmNavi       :   deallocati  :::\n");
    if (shmctl(shmMerci      , IPC_RMID, 0) == -1) { ERROR; } else printf(":::  shmMerci      :   deallocati  :::\n");
    if (shmctl(shmPila       , IPC_RMID, 0) == -1) { ERROR; } else printf(":::  shmPila       :   deallocati  :::\n");
    if (semctl(semShm        , IPC_RMID, 0) == -1) { ERROR; } else printf(":::  semShm        :   deallocati  :::\n");
    if (semctl(semPidNavi    , IPC_RMID, 0) == -1) { ERROR; } else printf(":::  semPidNavi    :   deallocati  :::\n");
    if (semctl(semPila       , IPC_RMID, 0) == -1) { ERROR; } else printf(":::  semPila       :   deallocati  :::\n");
    if (semctl(banchine      , IPC_RMID, 0) == -1) { ERROR; } else printf(":::  banchine      :   deallocati  :::\n");
    if (msgctl(msgPila       , IPC_RMID, 0) == -1) { ERROR; } else printf(":::  msgPila       :   deallocati  :::\n");
    if (semctl(semMareggiata , IPC_RMID, 0) == -1) { ERROR; } else printf(":::  semMareggiata :   deallocati  :::\n");
    if (semctl(semProcessi   , IPC_RMID, 0) == -1) { ERROR; } else printf(":::  semProcessi   :   deallocati  :::\n");
                                                                printf("::::::::::::::::::::::::::::::::::::::\n");
    return;
}

void loadIPCs() {
    if ((shmVar        = shmget(ftok(FTOK_FILE, 'v'), sizeof(Var)                         , PERMISSIONS)) == -1) ERROR;
    if ((var = shmat(shmVar  , NULL, 0)) == (void*) -1) ERROR;
    if ((shmPorti      = shmget(ftok(FTOK_FILE, 'p'), sizeof(Porto) * var -> n_porti      , PERMISSIONS)) == -1) ERROR;
    if ((shmNavi       = shmget(ftok(FTOK_FILE, 'n'), sizeof(Nave)  * var -> n_navi       , PERMISSIONS)) == -1) ERROR;
    if ((shmMerci      = shmget(ftok(FTOK_FILE, 'm'), sizeof(Merce) * SO_MERCI            , PERMISSIONS)) == -1) ERROR;
    if ((shmPila       = shmget(ftok(FTOK_FILE, 'i'), sizeof(int)   * (var -> n_navi + 1) , PERMISSIONS)) == -1) ERROR;
    if ((msgPila       = msgget(ftok(FTOK_FILE, 'g')                                      , PERMISSIONS)) == -1) ERROR;
    if ((semPila       = semget(ftok(FTOK_FILE, 's'), 2                                   , PERMISSIONS)) == -1) ERROR;
    if ((semShm        = semget(ftok(FTOK_FILE, 'a'), 1                                   , PERMISSIONS)) == -1) ERROR;
    if ((semPidNavi    = semget(ftok(FTOK_FILE, 'f'), 1                                   , PERMISSIONS)) == -1) ERROR;
    if ((semMareggiata = semget(ftok(FTOK_FILE, 'x'), var->n_porti                        , PERMISSIONS)) == -1) ERROR;
    if ((banchine      = semget(ftok(FTOK_FILE, 'z'), var->n_porti                        , PERMISSIONS)) == -1) ERROR;
    if ((semProcessi   = semget(ftok(FTOK_FILE, 't'), 1                                   , PERMISSIONS)) == -1) ERROR;

    out_progetto = fopen("Progetto.out", "a");
    setbuf(out_progetto, NULL);
    set_sem(semProcessi, 0, 0);
    
    return;
}

void unloadIPCs() {
    if((shmdt(var)) == -1) ERROR;
    return;
}