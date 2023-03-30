int decremento() {
    int i,j;
    for(i=0; i < SO_MERCI; ++i) {
        for(j=0; j< var->n_porti; ++j){
            if(porti[j].scadenza[giorno][i] != 0) {
                if(porti[j].offerta[i] - porti[j].scadenza[giorno][i] < 0) printf(":: --- NEGATIVA - (%d - %d) merce[%d](t_vita = %d) in porto[%d] (offerta)\n", porti[j].offerta[i], porti[j].scadenza[giorno][i],i+1, merci[i].t_vita , j+1);
                porti[j].scaduta[i]     += porti[j].scadenza[giorno][i];
                porti[j].offerta[i]     -= porti[j].scadenza[giorno][i];
                porti[j].scadenza[giorno][i] = 0;
            }
        }
        for(j=0; j< var->n_navi; ++j)
            if(navi[j].scadenza[giorno][i] != 0 && navi[nave].pidNavi != -1) {
                if(navi[j].stiva[i] - navi[j].scadenza[giorno][i] < 0) {printf(":: --- NEGATIVA - (%d - %d) merce[%d](t_vita = %d) in nave[%d]\n", navi[j].stiva[i], navi[j].scadenza[giorno][i],i+1, merci[i].t_vita, j+1);
                                                                        printf("::                [stiva:  %d] - [occupato: %d]\n", navi[j].stiva[i], navi[j].occupato);}
                navi[j].scaduta[i]      += navi[j].scadenza[giorno][i];
                navi[j].stiva[i]        -= navi[j].scadenza[giorno][i];
                navi[j].scadenza[giorno][i] = 0;
            }
    }
    return 0;
}

int TonSize(int size, int so_fill) {
    return size + rand() % (1 + so_fill - size) / size * size;
}

int creazione_dom() {
    int i, loop;
    int p_rand, q_ton, t_merce;

    for (i = 0; i < var->SO_fill / SO_DAYS ;) {
        /* RANDOM MERCE */
        for(loop = 0, t_merce = rand() % (SO_MERCI); merci[t_merce].size > (var->SO_fill / SO_DAYS - i); ++loop) {
            if(loop == SO_MERCI * 100) { 
                /*fprintf(stderr,"LOOP - t_merce, no size minore di %d\n", (var->SO_fill / SO_DAYS - i)); ERROR;*/
                t_merce = 0;
            }
            t_merce = rand() % (SO_MERCI);
        }

        /* RANDOM PORTO */
        p_rand =  (var->n_porti) / 2 + rand() % ((var->n_porti) / 2);

        /* RANDOM QUANTITA' */
        if(( q_ton = TonSize(merci[t_merce].size, var->SO_fill / SO_DAYS - i)
           ) > (var->SO_fill / SO_DAYS - i) ) return -1;

        if(porti[p_rand].offerta[t_merce] == 0) {                   
            porti[p_rand].domanda[t_merce] += q_ton;                
            i += q_ton;                                            
        }
        
    }
    return i;
}

int creazione_off() {
    int i, p_rand, t_merce, loop;
    int q_ton = 0;

    for (i = 0; i < var->SO_fill / SO_DAYS;) {
        /* RANDOM MERCE */
        for(loop = 0, t_merce = rand() % (SO_MERCI); merci[t_merce].size > ((var->SO_fill / SO_DAYS) - i); ++loop) {
            if(loop == SO_MERCI * 100) 
                t_merce = 0;
            t_merce = rand() % (SO_MERCI);
        }

        /* RANDOM PORTO */
        p_rand = rand() % ((var->n_porti) / 2);
        /* RANDOM QUANTITA' */
        if((q_ton = TonSize(merci[t_merce].size, var->SO_fill / SO_DAYS - i)
           ) > (var->SO_fill / SO_DAYS - i)) return -1;

        if( porti[p_rand].domanda[t_merce] == 0) {                       /*  |-------------| */
            porti[p_rand].offerta[t_merce] += q_ton;
            i += q_ton;

            if(giorno + merci[t_merce].t_vita < SO_DAYS) 
                porti[p_rand].scadenza[giorno + merci[t_merce].t_vita][t_merce] +=q_ton;
        }
    }
    return i;
}

int tempesta() { if(var->setOnTempesta == FALSE) return 0;
   int nave, flagOP = 0;
    for (nave = rand() % var->n_navi; navi[nave].pidNavi<=0 || navi[nave].in_mare != TRUE; ++nave)
        if(nave == var->n_navi) { 
            if (flagOP == 0) { nave = 0; ++flagOP; } 
            else 
                return 0; 
        }
    kill(navi[nave].pidNavi, SIG_TEMPESTA);
    ++navi[nave].rallentata_da_tempesta;

    return nave + 1;
}

int mareggiata() { if(var->setOnMaraggiata == FALSE) return 0;
    int p_rand, flagOP = 0;
    int pidMareggiata;

    for (p_rand = rand() % var->n_porti; porti[p_rand].flagMareggiata == TRUE; ++p_rand)
        if(p_rand == var->n_porti) { 
            if (flagOP == 0) { p_rand = 0; ++flagOP; } 
            else             return 0; 
        }
    
    porti[p_rand].flagMareggiata = TRUE;
    ++porti[p_rand].colpito_da_mareggiata;

    if(semctl(semMareggiata, p_rand, GETVAL) == 0) return 1;
    reserveSem(semMareggiata, p_rand);

    switch((pidMareggiata = newProcess())){
        case -1: ERROR;
        case 0:
            sleep((1/24) * var->SO_swell_duration);
            releaseSem(semMareggiata, p_rand);
            attShm();{
            porti[p_rand].flagMareggiata = FALSE;
            }detShm();
            endProcess();
        default:
            break;
    }
    return p_rand + 1;
}