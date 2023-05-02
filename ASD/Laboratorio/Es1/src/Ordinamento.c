#include <stdio.h>
#include <stdlib.h>

#include "Interfaccia.h"

#define INPUT_FILE "../records.csv"
#define OUTPUT_FILE "bin/outfile.csv"

#define K 100

enum enum_field {FIELD_POS, FIELD_STRING, FIELD_INT, FIELD_FLOAT};

void main(int argc, const char* argv[]) {
        
    if(argc == 3)
        sort_records(argv[1], argv[2], K, FIELD_POS);
    else 
        sort_records(INPUT_FILE, OUTPUT_FILE, K, FIELD_INT);
}

// Si prevede che la funzione venga chiamata con (altrimenti verranno usati i valori di default): 
//    ./bin/Ordinamento [INPUT_FILE] [OUTPUT_FILE]
