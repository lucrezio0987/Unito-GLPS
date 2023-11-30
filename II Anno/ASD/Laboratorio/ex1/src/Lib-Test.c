#include "Interfaccia.h"
#include "unity.h"

#define OUTPUT_FILE "test_outfile.csv"
#define INPUT_FILE "test_records.csv"
// #define INPUT_FILE "../records.csv"
// #define OUTPUT_FILE "bin/outfile.csv"

//--------- PROTOTIPI ---------//

int array_is_empty(Array* array);

void setUp(void);
void tearDown(void);

void test_create_array_empty();
void test_create_array_add_not_empty();
void test_load_array();
void test_load_array_max();

void test_compare_pos();
void test_compare_int();
void test_compare_float();
void test_compare_string();

void test_sort_records_Pos();
void test_sort_records_String();
void test_sort_records_Int();
void test_sort_records_Float();

// void test_merge_sorte();
// void test_binary_insertion_sort();
// void test_merge_binary_insertion_sort();

//--------- STRUTTURE ---------//

struct _Record {
    long int pos;
    long int item_int;
    double item_float;
    char* item_string;
};

struct _Array {
    Records** base;
    unsigned int nitems;
};

//------ VARIABILI GLOBALI ------//

int n_records;

//--- AMBIENTE ---//

void setUp(void)
{
    n_records = 5;

    FILE* fin = fopen(INPUT_FILE, "w");
    if (fin != NULL) {
        fprintf(fin, "1,FruttoE,10,6.5\n");
        fprintf(fin, "2,FruttoD,20,5.8\n");
        fprintf(fin, "3,FruttoC,15,4.2\n");
        fprintf(fin, "4,FruttoA,45,1.5\n");
        fprintf(fin, "5,FruttoB,40,7.0\n");
        fclose(fin);
    }

    FILE* fout = fopen(OUTPUT_FILE, "w");
    if (fout != NULL) {
        fclose(fout);
    }
}

void tearDown(void)
{
    remove(INPUT_FILE);
    remove(OUTPUT_FILE);
    return;
}

//------ IMPLEMENTAZIONI ------//

int array_is_empty(Array* array)
{
    if (array == NULL) {
        fprintf(stderr, "array_is_empty: array parameter cannot be NULL");
        exit(EXIT_FAILURE);
    }
    return array->nitems == 0;
}

void test_create_array_empty()
{
    TEST_ASSERT_NOT_NULL(create_array());
}

void test_create_array_add_not_empty()
{
    Array* A = create_array();
    Records* rec = (Records*)malloc(sizeof(Records));

    rec->item_int = 10;
    rec->item_float = 10.10;
    rec->pos = 0;
    rec->item_string = (char*)malloc(sizeof(char) * 100);
    strcpy(rec->item_string, "Frutto");

    printf("%s", rec->item_string);

    array_add(A, rec);

    TEST_ASSERT_FALSE(array_is_empty(A));

    free(A);
    free(rec);
}

void test_load_array()
{
    Array* A = create_array();
    load_array(A, INPUT_FILE);
    TEST_ASSERT_FALSE(array_is_empty(A));
    free(A);
}

void test_load_array_max()
{
    Array* A = create_array();
    load_array_max(A, INPUT_FILE, n_records);
    TEST_ASSERT_FALSE(array_is_empty(A));
    free(A);
}

void test_compare_pos()
{
    Records* a = (Records*)malloc(sizeof(Records));
    Records* b = (Records*)malloc(sizeof(Records));

    a->pos = 1;
    b->pos = 2;

    TEST_ASSERT_EQUAL(-1, compare_pos(a, b));
    TEST_ASSERT_EQUAL(0, compare_pos(a, a));
    TEST_ASSERT_EQUAL(1, compare_pos(b, a));

    free(a);
    free(b);
}

void test_compare_int()
{
    Records* a = (Records*)malloc(sizeof(Records));
    Records* b = (Records*)malloc(sizeof(Records));

    a->item_int = 1;
    b->item_int = 2;

    TEST_ASSERT_EQUAL(-1, compare_int(a, b));
    TEST_ASSERT_EQUAL(0, compare_int(a, a));
    TEST_ASSERT_EQUAL(1, compare_int(b, a));

    free(a);
    free(b);
}

void test_compare_float()
{
    Records* a = (Records*)malloc(sizeof(Records));
    Records* b = (Records*)malloc(sizeof(Records));

    a->item_float = 1.5;
    b->item_float = 2.5;

    TEST_ASSERT_EQUAL(-1, compare_float(a, b));
    TEST_ASSERT_EQUAL(0, compare_float(a, a));
    TEST_ASSERT_EQUAL(1, compare_float(b, a));

    free(a);
    free(b);
}

void test_compare_string()
{
    Records* a = (Records*)malloc(sizeof(Records));
    Records* b = (Records*)malloc(sizeof(Records));

    a->item_string = strdup("fruttoA");
    b->item_string = strdup("fruttoB");

    TEST_ASSERT_EQUAL(-1, compare_string(a, b));
    TEST_ASSERT_EQUAL(0, compare_string(a, a));
    TEST_ASSERT_EQUAL(1, compare_string(b, a));

    free(a->item_string);
    free(b->item_string);

    free(a);
    free(b);
}

void test_sort_records_Pos()
{
    Array* A = create_array();

    sort_records(INPUT_FILE, OUTPUT_FILE, 0, 0);
    load_array(A, OUTPUT_FILE);
    TEST_ASSERT_TRUE(compare_pos(A->base[0], A->base[1]) <= 0);
    TEST_ASSERT_TRUE(compare_pos(A->base[1], A->base[2]) <= 0);
    TEST_ASSERT_TRUE(compare_pos(A->base[2], A->base[3]) <= 0);
    TEST_ASSERT_TRUE(compare_pos(A->base[3], A->base[4]) <= 0);

    sort_records(INPUT_FILE, OUTPUT_FILE, 2, 0);
    load_array(A, OUTPUT_FILE);
    TEST_ASSERT_TRUE(compare_pos(A->base[0], A->base[1]) <= 0);
    TEST_ASSERT_TRUE(compare_pos(A->base[1], A->base[2]) <= 0);
    TEST_ASSERT_TRUE(compare_pos(A->base[2], A->base[3]) <= 0);
    TEST_ASSERT_TRUE(compare_pos(A->base[3], A->base[4]) <= 0);

    sort_records(INPUT_FILE, OUTPUT_FILE, 10, 0);
    load_array(A, OUTPUT_FILE);
    TEST_ASSERT_TRUE(compare_pos(A->base[0], A->base[1]) <= 0);
    TEST_ASSERT_TRUE(compare_pos(A->base[1], A->base[2]) <= 0);
    TEST_ASSERT_TRUE(compare_pos(A->base[2], A->base[3]) <= 0);
    TEST_ASSERT_TRUE(compare_pos(A->base[3], A->base[4]) <= 0);

    free(A);
}
void test_sort_records_String()
{
    Array* A = create_array();

    sort_records(INPUT_FILE, OUTPUT_FILE, 0, 1);
    load_array(A, OUTPUT_FILE);
    TEST_ASSERT_TRUE(compare_string(A->base[0], A->base[1]) <= 0);
    TEST_ASSERT_TRUE(compare_string(A->base[1], A->base[2]) <= 0);
    TEST_ASSERT_TRUE(compare_string(A->base[2], A->base[3]) <= 0);
    TEST_ASSERT_TRUE(compare_string(A->base[3], A->base[4]) <= 0);

    sort_records(INPUT_FILE, OUTPUT_FILE, 2, 1);
    load_array(A, OUTPUT_FILE);
    TEST_ASSERT_TRUE(compare_string(A->base[0], A->base[1]) <= 0);
    TEST_ASSERT_TRUE(compare_string(A->base[1], A->base[2]) <= 0);
    TEST_ASSERT_TRUE(compare_string(A->base[2], A->base[3]) <= 0);
    TEST_ASSERT_TRUE(compare_string(A->base[3], A->base[4]) <= 0);

    sort_records(INPUT_FILE, OUTPUT_FILE, 10, 1);
    load_array(A, OUTPUT_FILE);
    TEST_ASSERT_TRUE(compare_string(A->base[0], A->base[1]) <= 0);
    TEST_ASSERT_TRUE(compare_string(A->base[1], A->base[2]) <= 0);
    TEST_ASSERT_TRUE(compare_string(A->base[2], A->base[3]) <= 0);
    TEST_ASSERT_TRUE(compare_string(A->base[3], A->base[4]) <= 0);

    free(A);
}
void test_sort_records_Int()
{
    Array* A = create_array();

    sort_records(INPUT_FILE, OUTPUT_FILE, 0, 2);
    load_array(A, OUTPUT_FILE);
    TEST_ASSERT_TRUE(compare_int(A->base[0], A->base[1]) <= 0);
    TEST_ASSERT_TRUE(compare_int(A->base[1], A->base[2]) <= 0);
    TEST_ASSERT_TRUE(compare_int(A->base[2], A->base[3]) <= 0);
    TEST_ASSERT_TRUE(compare_int(A->base[3], A->base[4]) <= 0);

    sort_records(INPUT_FILE, OUTPUT_FILE, 2, 2);
    load_array(A, OUTPUT_FILE);
    TEST_ASSERT_TRUE(compare_int(A->base[0], A->base[1]) <= 0);
    TEST_ASSERT_TRUE(compare_int(A->base[1], A->base[2]) <= 0);
    TEST_ASSERT_TRUE(compare_int(A->base[2], A->base[3]) <= 0);
    TEST_ASSERT_TRUE(compare_int(A->base[3], A->base[4]) <= 0);

    sort_records(INPUT_FILE, OUTPUT_FILE, 10, 2);
    load_array(A, OUTPUT_FILE);
    TEST_ASSERT_TRUE(compare_int(A->base[0], A->base[1]) <= 0);
    TEST_ASSERT_TRUE(compare_int(A->base[1], A->base[2]) <= 0);
    TEST_ASSERT_TRUE(compare_int(A->base[2], A->base[3]) <= 0);
    TEST_ASSERT_TRUE(compare_int(A->base[3], A->base[4]) <= 0);

    free(A);
}
void test_sort_records_Float()
{
    Array* A = create_array();

    sort_records(INPUT_FILE, OUTPUT_FILE, 0, 3);
    load_array(A, OUTPUT_FILE);
    TEST_ASSERT_TRUE(compare_float(A->base[0], A->base[1]) <= 0);
    TEST_ASSERT_TRUE(compare_float(A->base[1], A->base[2]) <= 0);
    TEST_ASSERT_TRUE(compare_float(A->base[2], A->base[3]) <= 0);
    TEST_ASSERT_TRUE(compare_float(A->base[3], A->base[4]) <= 0);

    sort_records(INPUT_FILE, OUTPUT_FILE, 2, 3);
    load_array(A, OUTPUT_FILE);
    TEST_ASSERT_TRUE(compare_float(A->base[0], A->base[1]) <= 0);
    TEST_ASSERT_TRUE(compare_float(A->base[1], A->base[2]) <= 0);
    TEST_ASSERT_TRUE(compare_float(A->base[2], A->base[3]) <= 0);
    TEST_ASSERT_TRUE(compare_float(A->base[3], A->base[4]) <= 0);

    sort_records(INPUT_FILE, OUTPUT_FILE, 10, 3);
    load_array(A, OUTPUT_FILE);
    TEST_ASSERT_TRUE(compare_float(A->base[0], A->base[1]) <= 0);
    TEST_ASSERT_TRUE(compare_float(A->base[1], A->base[2]) <= 0);
    TEST_ASSERT_TRUE(compare_float(A->base[2], A->base[3]) <= 0);
    TEST_ASSERT_TRUE(compare_float(A->base[3], A->base[4]) <= 0);
    free(A);
}
