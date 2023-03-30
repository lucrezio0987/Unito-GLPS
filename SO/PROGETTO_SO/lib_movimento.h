int pop() {
    int nave = -1;
    reserveSem(semPila, 0);
    reserveSem(semPila, 1);

    if(var->flagTerminazione == 0)
    if((pila  = shmat(shmPila,  NULL, 0)) != ( int*   ) -1){
        nave = pila[pila[var->n_navi] - 1];
        --pila[var->n_navi];
        if((shmdt(pila)) == (void *) -1) ERROR;
    } else endProcess();
    releaseSem(semPila, 1);
    return nave;
}

void push(int nave) {
    reserveSem(semPila, 1);
    if((pila  = shmat(shmPila,  NULL, 0)) == ( int*   ) -1) ERROR;

    ++pila[var -> n_navi];
    pila[pila[var -> n_navi] -1] = nave;
    
    if((shmdt(pila )) == (void *) -1) ERROR;
    releaseSem(semPila, 1);
    releaseSem(semPila, 0);
}

int aggiornaPidNave(int nave, pid_t pid) {
    reserveSem(semPidNavi, 0);

    if(var -> flagTerminazione == 0)
    if((navi  = shmat(shmNavi,  NULL, 0)) != ( Nave*  ) -1){
        if(navi[nave].pidNavi!= -1) navi[nave].pidNavi=pid;

        if(pid>0) ++var->fork_navi;
        else      --var->fork_navi;

        if((shmdt(navi )) == (void *) -1) ERROR;
    } else endProcess();

    releaseSem(semPidNavi, 0);
    return pid;
}

double distanza(int nave, int p_rand) {
    double n =   (navi[nave].pos[X] - porti[p_rand].pos[X])*(navi[nave].pos[X] - porti[p_rand].pos[X]) 
               + (navi[nave].pos[Y] - porti[p_rand].pos[Y])*(navi[nave].pos[Y] - porti[p_rand].pos[Y]);
    double d = n/2;
    double t = 0;

    while(d != t) { t = d; d = ( n/t + t) / 2; } /* algoritmo babilonese */
    return d;
}

double viaggio (int nave, int p_rand) {
    double d = distanza(nave, p_rand);
    return d;   
}

int scarico(int nave, int p_rand) {
    int scarico_u, scaricata;
    int t_merce, g;

    for (t_merce = 0, scarico_u = 0, scaricata = 0; t_merce < SO_MERCI; ++t_merce, scarico_u = 0) {
        if (porti[p_rand].domanda[t_merce] > 0 && navi[nave].stiva[t_merce] > 0) {
            if (navi[nave].stiva[t_merce]  > porti[p_rand].domanda[t_merce]) 
                    scarico_u = porti[p_rand].domanda[t_merce];                         
            else    scarico_u = navi[nave].stiva[t_merce];

            if(scarico_u < 0) {
                fprintf(stderr, " _/scarico_u = %d, Merce = %d\n", scarico_u, t_merce);
                fprintf(stderr, "|_ porti[p_rand].domanda[t_merce] = %d\n", porti[p_rand].domanda[t_merce]);
                fprintf(stderr, "  \\navi[nave].stiva[t_merce] = %d \t navi[nave].scaduta[t_merce] = %d\n", navi[nave].stiva[t_merce], navi[nave].scaduta[t_merce]);
            }
            
            porti[p_rand].ricevuta[t_merce]       += scarico_u;
            porti[p_rand].domanda[t_merce]        -= scarico_u;
            porti[p_rand].merce_ricevuta          += scarico_u;
            navi[nave].stiva[t_merce]             -= scarico_u;
            navi[nave].occupato                   -= scarico_u;
            }
        scaricata += scarico_u;

        for(g = var->giorno; scarico_u > 0; ++g){
                if (navi[nave].scadenza[g][t_merce] >= scarico_u) {
                    navi[nave].scadenza[g][t_merce] -= scarico_u;
                    scarico_u = 0;
                } else {
                    scarico_u -= navi[nave].scadenza[g][t_merce];
                    navi[nave].scadenza[g][t_merce] = 0;
                }       
            }  
    }
    if(navi[nave].occupato == 0) navi[nave].carica = FALSE;
    return scaricata;
}

int carico(int nave, int p_rand) {
    int q_ton = 0, max_qta = 0, caricata = 0, da_caricare = 0;
    int stop, g, loop_cont, t_merce = 0;

    for(t_merce=0; t_merce<SO_MERCI; ++t_merce)                 da_caricare += porti[p_rand].offerta[t_merce];
    if (da_caricare > navi[nave].capacita - navi[nave].occupato) da_caricare = navi[nave].capacita - navi[nave].occupato;

    for (caricata = 0; caricata<da_caricare;) {
        /* CONTROLLO MERCE */
        for(t_merce = 0; porti[p_rand].offerta[t_merce] == 0 || merci[t_merce].size > navi[nave].capacita - navi[nave].occupato;++t_merce)
            if(t_merce == SO_MERCI) return caricata;

        /* SCELTA MAX_QTA */
        if (navi[nave].capacita - navi[nave].occupato < porti[p_rand].offerta[t_merce]) 
            max_qta = navi[nave].capacita - navi[nave].occupato;
        else 
            max_qta = porti[p_rand].offerta[t_merce];

        for(loop_cont = 0, stop=0, q_ton=merci[t_merce].size; 
            q_ton < max_qta-(max_qta%merci[t_merce].size) && stop == 0; 
            ++loop_cont, stop=rand()%2) {
                q_ton+=merci[t_merce].size;
                if(++loop_cont == max_qta*10) { printf("LOOP - q_ton, no size minore di %d\n", max_qta); 
                                                printf("    LOOP >>>> Merce: %d  ===== Porto: %d (contiene: %d) --- Nave: %d (contiene: %d) [%d/%d]\n", t_merce+1, p_rand+1, porti[p_rand].offerta[t_merce], nave+1, navi[nave].stiva[t_merce], navi[nave].occupato, navi[nave].capacita);
                                                printf("              Merce: %d --> q_ton/max_qta: [%d/%d] \n", t_merce+1, q_ton, max_qta);
                return 0; }
        }

        porti[p_rand].offerta[t_merce] -= q_ton;
        porti[p_rand].spedita[t_merce] += q_ton;
        porti[p_rand].merce_offerta    += q_ton;
        navi[nave].stiva[t_merce]      += q_ton;
        navi[nave].occupato            += q_ton;

        caricata += q_ton;

        for(g = var->giorno; q_ton > 0; ++g){
            if (porti[p_rand].scadenza[g][t_merce] >= q_ton) {
                porti[p_rand].scadenza[g][t_merce] -= q_ton;
                navi[nave].scadenza[g][t_merce]        += q_ton;
                q_ton = 0;
            } else {
                navi[nave].scadenza[g][t_merce] += porti[p_rand].scadenza[g][t_merce];
                q_ton -= porti[p_rand].scadenza[g][t_merce];
                porti[p_rand].scadenza[g][t_merce] = 0;
            }          
        }
    }

    if(navi[nave].occupato !=0 ) navi[nave].carica = TRUE;
    return caricata;
}

void h_meteo(int sig) {
    switch(sig) {
        case SIG_TEMPESTA:   signal(SIG_TEMPESTA, h_meteo); /* TEMPESTA */
            flagTempesta = 1;
            break;
        case SIG_MAELSTROM:  signal(SIG_MAELSTROM, h_meteo); /* MAELSTROM */
            flagMaelstrom = 1;
            break;
        case SIGTERM:        signal(SIGTERM, h_meteo);
            flagTerminazione = 1;
            printf("::::::::::::::::::::::::::::::::::::::::\n");
            printf(":::  PROCESSO Movimento - Terminato  :::\n");
            printf("::::::::::::::::::::::::::::::::::::::::\n::\n");
            endProcess();
        default: 
            ERROR;
    }
}

int controlloSegnali(int flag){
    int flagMar;
    if (flag & C_TEMPESTA && flagTempesta--     == 1) {
        sleep(1/24 * var->SO_storm_duration);
    }   
    if (flag & C_MAREGGIATA) {
        for(flagMar=0;semctl(semMareggiata, p_rand, GETVAL) == 0;)
            if(flagMar==0) ++flagMar;
    }
    if (flagTerminazione==1 || var -> flagTerminazione == 1) endProcess();
    if (flag & C_MAELSTROM && flagMaelstrom--    == 1) {
        int i;
        attShm();{
            releaseSem(banchine, p_rand);              /* Lascia banchina */
            --porti[p_rand].banchine_occupate;
            for(i=0; i<SO_MERCI; ++i){
                merci[i].ton -= navi[nave].stiva[i];
            }
        }detShm(); 
        message.m_type = 2;
        if ((msgsnd(msgPila, &message, (sizeof(message)-sizeof(long)), IPC_NOWAIT)) == -1) ERROR;
        endProcess();
    }
}