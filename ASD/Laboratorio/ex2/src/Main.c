#include <stdio.h>
#include <stdlib.h>

#include "Interfaccia.h"

#define DICTIONARY "/home/lucrezio0987/Documenti/Unito-GLPS/ASD/Laboratorio/ex2/dictionary.txt"//"../dictionary.txt"
#define TEXT_FILE "/home/lucrezio0987/Documenti/Unito-GLPS/ASD/Laboratorio/ex2/text.txt"//"../correctme.txt"
#define MAX_HEIGHT 10


void main(int argc, const char* argv[]) {

    if(argc != 3)
        find_errors(DICTIONARY, TEXT_FILE, MAX_HEIGHT);
    else 
        find_errors(argv[1], argv[2], MAX_HEIGHT);
}

// .build/main_ex2 [DICTIONARY] [TEXT_FILE]