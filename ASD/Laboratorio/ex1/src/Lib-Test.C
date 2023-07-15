#include "Interfaccia.h"

struct _Record {
  long int pos; 
  long int item_int;
  double item_float;
  char *item_string;
};

struct _Array {
    Records **base;
    unsigned int nitems;
};

int Array_is_empty(Array *array) {
  if (array == NULL) {
    fprintf(stderr, "Array_is_empty: array parameter cannot be NULL");
    exit(EXIT_FAILURE);
  }
  return array->nitems == 0;
}
