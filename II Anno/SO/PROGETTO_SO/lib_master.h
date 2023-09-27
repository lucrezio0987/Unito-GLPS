double RandomPosition() {
    double i = (double) (rand() % (var->SO_lato + 1));
    if (i < var->SO_lato) i += (double)(rand() % 100) / 100;
    return i;
}

int creazione_porti() {
    int i, j, k;

    for(i = 0; i < var->n_porti; ++i) {
        if(i == 0) {
            porti[i].pos[X] = 0;
            porti[i].pos[Y] = 0;
        } else if(i == 1) {
            porti[i].pos[X] = 0;
            porti[i].pos[Y] = var->SO_lato;
        } else if(i == 2) {
            porti[i].pos[X] = var->SO_lato;
            porti[i].pos[Y] = 0;
        } else if(i == 3) {
            porti[i].pos[X] = var->SO_lato;
            porti[i].pos[Y] = var->SO_lato;
        } else {
            porti[i].pos[X] = RandomPosition();
            porti[i].pos[Y] = RandomPosition();
        }

        porti[i].banchine           = 1 + rand() % (var->SO_banchine);
        porti[i].banchine_occupate  = 0;
        porti[i].merce_offerta      = 0;
        porti[i].merce_ricevuta     = 0;

        set_sem(banchine, i, porti[i].banchine);
        set_sem(semMareggiata, i, 1);
        
        for(j = 0; j < SO_MERCI; ++j) {
            porti[i].offerta[j]     = 0;
            porti[i].domanda[j]     = 0;
            porti[i].spedita[j]     = 0;
            porti[i].ricevuta[j]    = 0;
            porti[i].scaduta[j]     = 0;

            for(k=0; k<SO_DAYS; ++k) porti[i].scadenza[k][j] = 0; 
        }
    }
    return 0;
}

int creazione_merci() {
    int i;
    for(i = 0; i < SO_MERCI; ++i) {
        switch(i) {
            case 0:  sprintf(merci[i].t_merce, "Carne"); break;
            case 1:  sprintf(merci[i].t_merce, "Pere "); break;
            case 2:  sprintf(merci[i].t_merce, "Uova "); break;
            case 3:  sprintf(merci[i].t_merce, "Pesci"); break;
            case 4:  sprintf(merci[i].t_merce, "Miso "); break;
            case 5:  sprintf(merci[i].t_merce, "Seta "); break;
            case 6:  sprintf(merci[i].t_merce, "Vetro"); break;
            case 7:  sprintf(merci[i].t_merce, "Ferro"); break;
            case 8:  sprintf(merci[i].t_merce, "Legno"); break;
            case 9:  sprintf(merci[i].t_merce, "Oro  "); break;
            default: sprintf(merci[i].t_merce, "%5d", i+1);
        }
        if(i == 0) merci[i].size    = 1; /* per evitare il loop in eventuali creazioni di merci */
        else       merci[i].size    = 1 + rand() % var->SO_size;
        merci[i].ton                = 0;
        merci[i].t_vita             = var->SO_min_vita + rand() % (var->SO_max_vita - var->SO_min_vita + 1);
    }
    return 0;
}

int creazione_navi() {
    int i, j, k;
    
    if((pila  = shmat(shmPila,  NULL, 0)) == ( int*   ) -1) ERROR;

    for(i = 0; i < var->n_navi; ++i) {
        navi[i].pos[X]  = RandomPosition();
        navi[i].pos[Y]  = RandomPosition();
        navi[i].capacita = 1 + rand() % (var->SO_capacity);
        navi[i].carica  = FALSE;
        navi[i].in_mare = TRUE;
        navi[i].pidNavi = 0;
        pila[i]         = i;

        for(j = 0; j < SO_MERCI; ++j) {
            navi[i].stiva[j]   = 0;
            navi[i].scaduta[j] = 0;
            
            for(k=0; k<SO_DAYS; ++k) navi[i].scadenza[k][j] = 0;
        }
    }
    pila[var->n_navi] = var->n_navi;

    if((shmdt(pila )) == -1) ERROR;
    return 0;
}