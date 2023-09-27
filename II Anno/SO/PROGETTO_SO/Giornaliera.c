#include "lib_header.h"
#include "lib_giornaliera.h"

int main(int argc, char* argv[]) {
    setbuf(stdout, NULL);
    int ret;
    
    loadIPCs();
    
    for(giorno = 0, var->giorno = 0; giorno < SO_DAYS; giorno = var->giorno) {
        sleep(1);
        fprintf(out_progetto, "╔═════════════════════════════╗\n");
        fprintf(out_progetto, "║  RESOCONTO GIORNALIERO %3d  ║\n", giorno + 1);
        fprintf(out_progetto, "╠═════════════════════════════╝\n");
        printf("::::::::::: GIORNO %d\n", giorno + 1);
        attShm(); {                                     printf(":: >>> (ATT)\n");
            if (decremento()         != 0) ERROR;       printf(":: >>> decremento\n");
            if ((ret=creazione_off())      <= 0) ERROR; printf(":: >>> creazione_off    :%5d (di %d)\n",  ret, var->SO_fill/SO_DAYS);
            if ((ret=creazione_dom())      <= 0) ERROR; printf(":: >>> creazione_dom    :%5d (di %d)\n",  ret, var->SO_fill/SO_DAYS);
            if ((ret=tempesta())           <= 0) ERROR; printf(":: >>> nave tempesta    :%5d (0 = nessuna)\n",  ret);
            if ((ret=mareggiata())         <= 0) ERROR; printf(":: >>> porto mareggiata :%5d (0 = nessuno)\n",  ret);
            if (stampa(GIORNALIERA)  != 0) ERROR; 
            ++var->giorno;
        } detShm();                                     printf(":: >>> (DET)\n");
        fprintf(out_progetto, "╚════\n\n");
    }

    unloadIPCs();
    
    printf("::::::::::::::::::::::::::::::::::::::::::\n");
    printf(":::  PROCESSO Giornaliera - Terminato  :::\n");
    printf("::::::::::::::::::::::::::::::::::::::::::\n::\n");
    exit(0);
}