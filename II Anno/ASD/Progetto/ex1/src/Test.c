/**
 * @file Test.C
 * @author Lucrezio Del Ponte, Simone Bergesio, Mario Corrao
 */

#include "Interfaccia.h"
#include "unity.h"

//---[  Tutti i Metodi di Test nel file Lib-Test.C  ]---//

void main()
{
    UNITY_BEGIN();

    RUN_TEST(test_create_array_empty);
    RUN_TEST(test_create_array_add_not_empty);

    RUN_TEST(test_load_array);
    RUN_TEST(test_load_array_max);

    RUN_TEST(test_compare_pos);
    RUN_TEST(test_compare_int);
    RUN_TEST(test_compare_float);
    RUN_TEST(test_compare_string);

    RUN_TEST(test_sort_records_Pos);
    RUN_TEST(test_sort_records_String);
    RUN_TEST(test_sort_records_Int);
    RUN_TEST(test_sort_records_Float);

    return UNITY_END();
}
