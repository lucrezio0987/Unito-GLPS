
/**
 * @file Main.c
 * @author Lucrezio Del Ponte, Simone Bergesio, Mario Corrao
 */

#include "Interfaccia.h"

#define INPUT_FILE "../records.csv"
#define OUTPUT_FILE "outfile.csv"

#define K 30

enum enum_field { FIELD_POS,
    FIELD_STRING,
    FIELD_INT,
    FIELD_FLOAT };

void printHelper()
{
    printf("\033[0;34m _______________________________________\n");
    printf("\033[0;34m| \n");
    printf("\033[0;34m|\033[0;32m Usage: ./bin/main_ex1 [OPTIONS]\n");
    printf("\033[0;34m|\033[0;32m Options:\n");
    printf("\033[0;34m|\033[0;32m   -i <inputFile>   Set the input file (default: %s)\n", INPUT_FILE);
    printf("\033[0;34m|\033[0;32m   -o <outputFile>  Set the output file (default: %s)\n", OUTPUT_FILE);
    printf("\033[0;34m|\033[0;32m   -k <value>       Set the value of k (default: %d)\n", K);
    printf("\033[0;34m|\033[0;32m   -m <value>       Set the value of max (default: all records)\n");
    printf("\033[0;34m|\033[0;32m   -f <field>       Set the field (default: FIELD_STRING)\n");
    printf("\033[0;34m|\033[0;32m                    Available fields: FIELD_POS, FIELD_STRING, FIELD_INT, FIELD_FLOAT\n");
    printf("\033[0;34m|\033[0;32m   -h               Display this help message\n");
    printf("\033[0;34m|_______________________________________\n");
}
void main(int argc, const char* argv[])
{
    char inputFile[20] = INPUT_FILE;
    char outputFile[20] = OUTPUT_FILE;
    int k = K;
    int field = FIELD_STRING;
    int max = 0;

    for (int i = 1; i < argc; i++) {
        if (strcmp(argv[i], "-i") == 0 && i + 1 < argc)
            strcpy(inputFile, argv[++i]);
        else if (strcmp(argv[i], "-o") == 0 && i + 1 < argc)
            strcpy(outputFile, argv[++i]);
        else if (strcmp(argv[i], "-k") == 0 && i + 1 < argc)
            k = atoi(argv[++i]);
        else if (strcmp(argv[i], "-m") == 0 && i + 1 < argc)
            max = atoi(argv[++i]);
        else if (strcmp(argv[i], "-f") == 0 && i + 1 < argc)
            field = atoi(argv[++i]);
        else if (strcmp(argv[i], "-h") == 0) {
            printHelper();
            exit(0);
        }
    }

    if (field > 3)
        field = FIELD_STRING;
    printHelper();
    printf("\033[0;34m| \n");
    printf("\033[0;34m|\033[0;33m  inputFile(-i):  \033[0;30m %s\n", inputFile);
    printf("\033[0;34m|\033[0;33m outputFile(-o):  \033[0;30m %s\n", outputFile);
    printf("\033[0;34m|\033[0;33m          k(-k):  \033[0;30m %d\n", k);
    printf("\033[0;34m|\033[0;33m      field(-f):  \033[0;30m %d\n", field);
    printf("\033[0;34m|\033[0;33m        max(-m):  \033[0;30m %d\n", max);
    printf("\033[0;34m|_____________________________\n");
    printf("\033[0;34m| \n");

    if (max == 0)
        sort_records(inputFile, outputFile, k, field);
    else
        sort_records_max(inputFile, outputFile, k, field, max);
    printf("\033[0;34m|_____________________________\n");
}
