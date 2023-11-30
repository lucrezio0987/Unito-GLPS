CC = gcc
CFLAGS = -w -Wno-return-type

MAIN = bin/main_ex1 
TEST = bin/test_ex1
TEST_EFFICIENZA = bin/test_efficienza
TEST_TEMPO = bin/test_tempo
INTERFACCIA = src/Interfaccia.h
EXEC_PARAM = ../records.csv outfile.csv 100


MAIN_OBJ = build/Main.o
LIB_OBJ = build/Lib-Generale.o build/Lib-MergeSorte.o build/Lib-BinaryInsertionSort.o build/Lib-MergeSorteConInsertion.o
TEST_MAIN_OBJ = build/Test.o
TEST_LIB_OBJ = build/Lib-Test.o build/unity.o
TEST_EFFICIENZA_OBJ = build/Test_efficienza.o
TEST_TEMPO_OBJ = build/Test_tempo.o

all: $(MAIN) $(TEST) $(TEST_EFFICIENZA) $(TEST_TEMPO)

$(MAIN): $(MAIN_OBJ) $(LIB_OBJ) | bin
	$(CC) $(MAIN_OBJ) $(LIB_OBJ) -o $@

$(TEST): $(TEST_MAIN_OBJ) $(LIB_OBJ) $(TEST_LIB_OBJ) | bin
	$(CC) $(TEST_MAIN_OBJ) $(LIB_OBJ) $(TEST_LIB_OBJ) -o $@

$(TEST_EFFICIENZA): $(TEST_EFFICIENZA_OBJ) $(LIB_OBJ) | bin
	$(CC) $(TEST_EFFICIENZA_OBJ) $(LIB_OBJ) -o $@

$(TEST_TEMPO): $(TEST_TEMPO_OBJ) $(LIB_OBJ) | bin
	$(CC) $(TEST_TEMPO_OBJ) $(LIB_OBJ) -o $@


build/%.o: src/%.c $(INTERFACCIA) | build
	$(CC) $(CFLAGS) -c $< -o $@

build bin:
	mkdir -p $@


clean:
	rm -rf build/* bin/* outfile.csv



