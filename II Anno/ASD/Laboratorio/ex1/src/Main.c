#include "Interfaccia.h"

#define INPUT_FILE "../records.csv"
#define OUTPUT_FILE "outfile.csv"

#define K 25 // 30

enum enum_field { FIELD_POS,
    FIELD_STRING,
    FIELD_INT,
    FIELD_FLOAT };

void main(int argc, const char* argv[])
{

    if (argc == 3)
        sort_records(argv[1], argv[2], K, FIELD_POS);
    else if (argc == 2)
        // sort_records_max(INPUT_FILE, OUTPUT_FILE, K, FIELD_STRING, atoi(argv[1]));
        sort_records(INPUT_FILE, OUTPUT_FILE, atoi(argv[1]), FIELD_STRING);
    else if (argc == 4)
        sort_records_max(argv[1], argv[2], K, FIELD_STRING, atoi(argv[3]));
    else if (argc == 5)
        sort_records_max(argv[1], argv[2], K, atoi(argv[4]), atoi(argv[3]));
    else
        sort_records(INPUT_FILE, OUTPUT_FILE, K, FIELD_STRING);
}
