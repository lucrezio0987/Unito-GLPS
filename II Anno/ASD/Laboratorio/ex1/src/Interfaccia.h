/**
 * @file binary_insertion_sort.h
 * @brief Header file for the Binary Insertion Sort library.
 *
 * This library provides functions for sorting and manipulating arrays using
 * the Merge-Binary-Insertion-Sort algorithm. It includes functions for array
 * creation, addition, loading from files, printing to files, and sorting based
 * on various criteria.
 *
 * @author Lucrezio Del Ponte, Simone Bergesio, Mario Corrao
 */

#include <errno.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include <unistd.h>

#define ERROR                                                                                                                                     \
    {                                                                                                                                             \
        if (errno) {                                                                                                                              \
            fprintf(stderr, "ERROR - line %d: file \"%s\" (pid %ld) - n %d - (%s)\n", __LINE__, __FILE__, (int)getpid(), errno, strerror(errno)); \
            exit(1);                                                                                                                              \
        }                                                                                                                                         \
    }

#ifndef BINARY_INSERTION_SORT_H_laokjsdnbudjllvfidkfmqm
#define BINARY_INSERTION_SORT_H_laokjsdnbudjllvfidkfmqm

//--------- STRUCTURES ---------//

typedef struct _Array Array; ///< Structure representing an array.
typedef struct _Record Records; ///< Structure representing a record in the array.

//--------- FUNCTION PROTOTYPES ---------//

/**
 * @brief Comparison function for records based on position.
 * @param i The first record.
 * @param j The second record.
 * @return An integer representing the comparison result.
 */
int compare_pos(Records* i, Records* j);

/**
 * @brief Comparison function for records based on integers.
 * @param i The first record.
 * @param j The second record.
 * @return An integer representing the comparison result.
 */
int compare_int(Records* i, Records* j);

/**
 * @brief Comparison function for records based on floats.
 * @param i The first record.
 * @param j The second record.
 * @return An integer representing the comparison result.
 */
int compare_float(Records* i, Records* j);

/**
 * @brief Comparison function for records based on strings.
 * @param i The first record.
 * @param j The second record.
 * @return An integer representing the comparison result.
 */
int compare_string(Records* i, Records* j);

/**
 * @brief Creates an empty array.
 * @return A pointer to the created array.
 */
Records** create_array();

/**
 * @brief Adds a record to the array.
 * @param A The array.
 * @param rec The record to be added.
 */
void array_add(Array* A, Records* rec);

/**
 * @brief Loads records from an input file into the array.
 * @param A The array.
 * @param infile The input file name.
 */
void load_array(Array* A, const char* infile);

/**
 * @brief Loads a specified number of records from an input file to the array. (used for tests)
 * @param A The array.
 * @param infile The input file name.
 * @param max_records The maximum number of records to load.
 */
void load_array_max(Array* A, const char* infile, unsigned int max_records);

/**
 * @brief Prints the array to an output file.
 * @param outfile The output file name.
 * @param A The array to be printed.
 */
void print_array(const char* outfile, Array* A);

/**
 * @brief Merges two halves of an array.
 * @param base The base array.
 * @param l The left index.
 * @param m The middle index.
 * @param r The right index.
 * @param compar The comparison function.
 */
void merge(void** base, unsigned int l, unsigned int m, unsigned int r, int (*compar)(const void*, const void*));
/**
 * @brief Sorts an array using the Merge Sort algorithm.
 * @param base The base array.
 * @param i The start index.
 * @param j The end index.
 * @param k The threshold value for binary insertion sort.
 * @param compar The comparison function.
 */
void merge_sort(void** base, unsigned int i, unsigned int j, unsigned int k, int (*compar)(const void*, const void*));

/**
 * @brief Sorts an array using the Merge-Binary-Insertion-Sort algorithm.
 * @param base The base array.
 * @param nitems The number of items in the array.
 * @param k The threshold value for binary insertion sort.
 * @param compar The comparison function.
 */
void merge_binary_insertion_sort(void** base, size_t nitems, size_t k, int (*compar)(const void*, const void*));

/**
 * @brief Sorts a portion of an array using the Binary Insertion Sort algorithm.
 * @param base The base array.
 * @param l The left index.
 * @param r The right index.
 * @param compar The comparison function.
 */
void binary_insertion_sort(void** base, unsigned int l, int r, int (*compar)(const void*, const void*));

/**
 * @brief Searches for a specific element in a sorted array.
 * @param x The element to search for.
 * @param base The base array.
 * @param i The start index.
 * @param j The end index.
 * @param compar The comparison function.
 * @return The index of the found element or -1 if not found.
 */
int search(unsigned int x, void** base, unsigned int i, unsigned int j, int (*compar)(const void*, const void*));

/**
 * @brief Sorts records in an input file and writes the result to an output file. (The first function called from main)
 * @param infile The input file name.
 * @param outfile The output file name.
 * @param k The threshold value for binary insertion sort.
 * @param field The field to sort records based on.
 */
void sort_records(const char* infile, const char* outfile, size_t k, size_t field);

/**
 * @brief Sorts a specified number of records in an input file and writes the result to an output file. (used for tests)
 * @param infile The input file name.
 * @param outfile The output file name.
 * @param k The threshold value for binary insertion sort.
 * @param field The field to sort records based on.
 * @param max_records The maximum number of records to sort.
 */
void sort_records_max(const char* infile, const char* outfile, size_t k, size_t field, size_t max_records);

/**
 * @brief Checks if the array is empty.
 * @param array The array to check.
 * @return 1 if the array is empty, 0 otherwise.
 */
int array_is_empty(Array* array);

/**
 * @brief Set up function for testing.
 */
void setUp(void);

/**
 * @brief Tear down function for testing.
 */
void tearDown(void);

/**
 * @brief Test case for creating an empty array.
 */
void test_create_array_empty();

/**
 * @brief Test case for adding a record to a non-empty array.
 */
void test_create_array_add_not_empty();

/**
 * @brief Test case for loading records from a file.
 */
void test_load_array();

/**
 * @brief Test case for loading a specified number of records from a file.
 */
void test_load_array_max();

/**
 * @brief Test case for comparing records based on position.
 */
void test_compare_pos();

/**
 * @brief Test case for comparing records based on integers.
 */
void test_compare_int();

/**
 * @brief Test case for comparing records based on floats.
 */
void test_compare_float();

/**
 * @brief Test case for comparing records based on strings.
 */
void test_compare_string();

/**
 * @brief Test case for sorting records based on position.
 */
void test_sort_records_Pos();

/**
 * @brief Test case for sorting records based on strings.
 */
void test_sort_records_String();

/**
 * @brief Test case for sorting records based on integers.
 */
void test_sort_records_Int();

/**
 * @brief Test case for sorting records based on floats.
 */
void test_sort_records_Float();

#endif /* BINARY_INSERTION_SORT_H_laokjsdnbudjllvfidkfmqm */
