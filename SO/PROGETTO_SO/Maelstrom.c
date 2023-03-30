#include "lib_header.h"

int main() {
    setbuf(stdout, NULL);
    int i, j;    
    int nave, flagOP = 0;
    float ore = 1/24 * var->SO_maelstrom; 
    loadIPCs();
    
    for(;;) {
        sleep(ore);
        attShm(); {
            for (nave = rand() % var->n_navi; navi[nave].pidNavi<0 ; ++nave)
                if(nave == var->n_navi) { 
                    if (flagOP != 0) { nave = 0; ++flagOP; } 
                    else             { 
                        printf("Maelstrom - Sono finite le navi......\n"); 
                        var -> setOnTempesta = FALSE;
                        detShm();
                        return 0; 
                    } 
                }
            if(navi[nave].pidNavi == 0 ) navi[nave].pidNavi = -1;
            else kill(navi[nave].pidNavi, SIG_MAELSTROM); 
        } detShm();
    }

    unloadIPCs();
    exit(0);
}