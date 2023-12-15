/**
 * @file Interfaccia.h
 * @brief Declarations and definitions for managing a dictionary implemented with a SkipList.
 *
 * This library provides structures and functions to create, manipulate, and search within a dictionary
 * implemented with a SkipList.
 *
 * @author Lucrezio Del Ponte, Simone Bergesio, Mario Corrao
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#ifndef DICTIONARY_H_laokjsdnbudjllvfidkfmqm
#define DICTIONARY_H_laokjsdnbudjllvfidkfmqm

/**
 * @struct Node
 * @brief Structure to represent a node in the SkipList.
 */
typedef struct Node Node;

/**
 * @struct SkipList
 * @brief Structure to represent a SkipList.
 */
typedef struct SkipList SkipList;

/**
 * @brief Initialize a new SkipList.
 *
 * @param list Pointer to a pointer of SkipList.
 * @param max_height Maximum height of the SkipList.
 * @param compar Comparison function for generic type of elements in the SkipList.
 */
void new_skiplist(struct SkipList** list, size_t max_height, int (*compar)(const void*, const void*));

/**
 * @brief Completely clear the SkipList, freeing allocated memory.
 *
 * @param list Pointer to a pointer of SkipList.
 */
void clear_skiplist(struct SkipList** list);

/**
 * @brief Recursive function to free the memory of a SkipList.
 *
 * @param Current Node in the recursion.
 */
void clear_skiplist_ric(Node* Current);

/**
 * @brief Create a new node with the specified element.
 *
 * @param size Size of the element.
 * @param item Pointer to the element to be inserted into the node.
 * @return Pointer to the newly created node.
 */
Node* create_node(size_t size, void* item);

/**
 * @brief Insert an element into the SkipList.
 *
 * @param list Pointer to the SkipList.
 * @param item Pointer to the element to be inserted.
 */
void insert_skiplist(struct SkipList* list, void* item);

/**
 * @brief Load data from a file into the SkipList and print loading information.
 *
 * @param list Pointer to the SkipList.
 * @param file Name of the file from which to load data.
 */
void LoadData(struct SkipList* list, const char* file);

/**
 * @brief Load data from a file into the SkipList without printing loading information.
 *
 * @param list Pointer to the SkipList.
 * @param file Name of the file from which to load data.
 */
void LoadData_no_print(struct SkipList* list, const char* file);

/**
 * @brief Search for an element in the SkipList.
 *
 * @param list Pointer to the SkipList.
 * @param item Pointer to the element to search for.
 * @return Pointer to the element if found, otherwise NULL.
 */
const void* search_skiplist(struct SkipList* list, void* item);

/**
 * @brief Find errors within a dictionary by comparing it with a text.
 *
 * @param dictfile Name of the file containing the dictionary.
 * @param textfile Name of the file containing the text.
 * @param max_height Maximum height of the SkipList.
 */
void find_errors(const char* dictfile, const char* textfile, size_t max_height);

/**
 * @brief Generate a random level for the SkipList.
 *
 * @param max Maximum height of the SkipList.
 * @return Generated level.
 */
size_t random_level(size_t max);

/**
 * @brief Comparison function for strings.
 *
 * @param i First string.
 * @param j Second string.
 * @return Result of the comparison.
 */
int CompareString(char* i, char* j);

/**
 * @brief Check if the SkipList is empty.
 *
 * @param list Pointer to the SkipList.
 * @return 1 if the list is empty, 0 otherwise.
 */
int list_is_empty(struct SkipList* list);

/**
 * @brief Print the SkipList.
 *
 * @param list Pointer to the SkipList.
 */
void print_list(struct SkipList* list);

/**
 * @brief Setup for tests.
 */
void setUp(void);

/**
 * @brief Teardown after tests.
 */
void tearDown(void);

/**
 * @brief Test to verify the creation of an empty SkipList.
 */
void test_create_skiplist_empty();

/**
 * @brief Test to verify the creation of a node.
 */
void test_create_node();

/**
 * @brief Test to verify the insertion of an element into the SkipList.
 */
void test_insert_skiplist();

/**
 * @brief Test to verify the loading of the SkipList from a file.
 */
void test_load_skiplist();

/**
 * @brief Test to verify the search for an element in the SkipList.
 */
void test_search_skiplist();

#endif /* DICTIONARY_H_laokjsdnbudjllvfidkfmqm */
