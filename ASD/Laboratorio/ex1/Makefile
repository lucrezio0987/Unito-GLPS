CC = gcc
CFLAGS = -w

MAIN = bin/main_ex1 
TEST = bin/test_ex1
INTERFACCIA = src/Interfaccia.h

MAIN_OBJ = build/Main.o
LIB_OBJ = build/Lib-Generale.o build/Lib-MergeSorte.o build/Lib-BinaryInsertionSort.o
TEST_MAIN_OBJ = build/Test.o
TEST_LIB_OBJ = build/Lib-Test.o build/unity.o

all: $(MAIN) $(TEST)

bin/main_ex1: $(MAIN_OBJ) $(LIB_OBJ) | bin
	$(CC) $(MAIN_OBJ) $(LIB_OBJ) -o $@

bin/test_ex1: $(TEST_MAIN_OBJ) $(LIB_OBJ) $(TEST_LIB_OBJ) | bin
	$(CC) $(TEST_MAIN_OBJ) $(LIB_OBJ) $(TEST_LIB_OBJ) -o $@

build/%.o: src/%.c $(INTERFACCIA) | build
	$(CC) $(CFLAGS) -c $< -o $@

build bin:
	mkdir -p $@

clean:
	rm -rf build/* bin/* outfile.csv



