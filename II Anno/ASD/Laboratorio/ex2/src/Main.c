#include "Interfaccia.h"

#define DICTIONARY "dictionary.txt"
#define TEXT_FILE "correctme.txt"
#define MAX_HEIGHT 10

void main(int argc, const char* argv[])
{

    if (argc == 3)
        find_errors(argv[1], argv[2], MAX_HEIGHT);
    else if (argc == 2)
        find_errors(DICTIONARY, TEXT_FILE, atoi(argv[1]));
    else
        find_errors(DICTIONARY, TEXT_FILE, MAX_HEIGHT);
}

// .build/main_ex2 [DICTIONARY] [TEXT_FILE]
