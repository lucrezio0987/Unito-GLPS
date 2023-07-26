#include "unity.h"
#include "../src/Interfaccia.h"


//---[  Tutti i Metodi di Test nel file Lib-Test.C  ]---//

void main() {
  UNITY_BEGIN();

  RUN_TEST(test_create_array_empty);
  RUN_TEST(test_create_array_add_not_empty);
  
  //TODO: RUN_TEST(test_LoadArray);

  //TODO: RUN_TEST(test_merge_sorte);
  //TODO: RUN_TEST(test_binary_insertion_sort);

  //TODO: RUN_TEST(test_merge_binary_insertion_sort);

  //TODO: RUN_TEST(test_ComparePos);
  //TODO: RUN_TEST(test_CompareInt);
  //TODO: RUN_TEST(test_CompareFloat); 
  
  return UNITY_END();
}