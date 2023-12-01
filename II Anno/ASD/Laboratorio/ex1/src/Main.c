
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

    printf("Usage: ./bin/main_ex1 [OPTIONS]\n");
    printf("Options:\n");
    printf("  -i <inputFile>   Set the input file (default: %s)\n", INPUT_FILE);
    printf("  -o <outputFile>  Set the output file (default: %s)\n", OUTPUT_FILE);
    printf("  -k <value>       Set the value of k (default: %d)\n", K);
    printf("  -m <value>       Set the value of max (default: all records)\n");
    printf("  -f <field>       Set the field (default: FIELD_STRING)\n");
    printf("                   Available fields: FIELD_POS, FIELD_STRING, FIELD_INT, FIELD_FLOAT\n");
    printf("  -h               Display this help message\n");
    exit(0);
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
        else if (strcmp(argv[i], "-h") == 0)
            printHelper();
        else
            ERROR
    }

    if (field > 3)
        field = FIELD_STRING;

    printf(" _____________________________\n");
    printf("|  inputFile(-i):   %s\n", inputFile);
    printf("| outputFile(-o):   %s\n", outputFile);
    printf("|          k(-k):   %d\n", k);
    printf("|      field(-f):   %d\n", field);
    printf("|        max(-m):   %d\n", max);
    printf("|_____________________________\n");

    if (max == 0)
        sort_records(inputFile, outputFile, k, field);
    else
        sort_records_max(inputFile, outputFile, k, field, max);
    printf("|_____________________________\n");
}
