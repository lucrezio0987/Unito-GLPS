#include <stdio.h>
#include <stdlib.h>

#include "Interfaccia.h"

#define DICTIONARY "dictionary.txt"
#define TEXT_FILE  "text.txt"
#define MAX_HEIGHT 10


void main(int argc, const char* argv[]) {

    if(argc != 3)
        find_errors(DICTIONARY, TEXT_FILE, MAX_HEIGHT);
    else 
        find_errors(argv[1], argv[2], MAX_HEIGHT);
}

// .build/main_ex2 [DICTIONARY] [TEXT_FILE]