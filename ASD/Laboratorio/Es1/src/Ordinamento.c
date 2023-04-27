#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "Interfaccia.h"

#define INPUT_FILE "../records.csv"
#define INPUT_FILE_BIG "ordered_array_sample_file.csv"
#define OUTPUT_FILE "bin/outfile.csv"

void main(int argc, const char* argv[]) {
    int field, n_records;

    if(argc != 3) {
        printf("use default field(0) e n_records(10)\n");
        field = 0;
        n_records = 10;
    } else {
        field = atoi(argv[1]);
        n_records = atoi(argv[2]);
    }

    sort_records(INPUT_FILE, OUTPUT_FILE, field, n_records);
}

