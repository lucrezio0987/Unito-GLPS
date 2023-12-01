#include <stdio.h>
#include <time.h>

#include "Interfaccia.h"

#define OUTPUT_FILE "tempi.csv"

#define MAX_REC 500000
#define N_CONTR 5
#define Q_VAL 1000

int time_exec(int max_records);

void main(int argc, const char* argv[])
{
    int i, j, res;
    FILE* output = fopen(OUTPUT_FILE, "w+");
    for (i = 0; i < MAX_REC; i += MAX_REC / Q_VAL) {
        for (j = 0, res = 0; j < N_CONTR; ++j)
            res += time_exec(i);
        fprintf(output, "%d %d\n", i, res / N_CONTR);
    }
    fclose(output);
}

int time_exec(int max_records)
{
    pid_t pid;
    int start;
    char str[10];

    switch ((pid = fork())) {
    case -1:
        ERROR;
    case 0:
        sprintf(str, "%d", max_records);
        execl("./bin/main_ex1", "./bin/main_ex1", str, NULL);
        printf("main_ex1 non avviato correttamente\n");
        ERROR;
    default:
        start = clock();
        waitpid(pid, NULL, NULL);
        return clock() - start;
    }
}
