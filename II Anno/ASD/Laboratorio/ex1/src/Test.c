#include "unity.h"
#include "Interfaccia.h"


//---[  Tutti i Metodi di Test nel file Lib-Test.C  ]---//

void main() {
  UNITY_BEGIN();

  RUN_TEST(test_create_array_empty);
  RUN_TEST(test_create_array_add_not_empty);
  
  RUN_TEST(test_LoadArray);
  RUN_TEST(test_LoadArrayMAX);

  RUN_TEST(test_ComparePos);
  RUN_TEST(test_CompareInt);
  RUN_TEST(test_CompareFloat); 
  RUN_TEST(test_CompareString); 

  RUN_TEST(test_sort_records_Pos);
  RUN_TEST(test_sort_records_String);
  RUN_TEST(test_sort_records_Int);
  RUN_TEST(test_sort_records_Float);

  //TODO: RUN_TEST(test_binary_insertion_sort);
  //TODO: RUN_TEST(test_merge_sorte);
  //TODO: RUN_TEST(test_merge_binary_insertion_sort);
  
  return UNITY_END();
}
