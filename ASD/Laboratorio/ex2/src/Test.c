#include "unity.h"
#include "Interfaccia.h"

//---[  Tutti i Metodi di Test nel file Lib-Test.C  ]---//

void main() {
  UNITY_BEGIN();

  RUN_TEST(test_create_skiplist_empty);
  RUN_TEST(test_create_node);
  RUN_TEST(test_insert_skiplist);
  RUN_TEST(test_load_skiplist);
  RUN_TEST(test_search_skiplist);
  
  return UNITY_END();
}