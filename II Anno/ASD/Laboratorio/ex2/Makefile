#MAIN = src/Main.c
#INTERFACCIA = src/Interfaccia.h	
#LIBRERIE = src/Lib-Generale.c
#
#ESEGUIBILE = build/main_ex2
#
#FLAG = -w
#
#all: $(ESEGUIBILE)
#
#$(ESEGUIBILE): $(MAIN) $(LIBRERIE) $(INTERFACCIA)
#	$(CC) $(FLAG) -o $(ESEGUIBILE) $(MAIN) $(LIBRERIE)
#
#clean: 
#	$(RM) -rf build/*
#	$(RM) -rf outfile.csv

CC = gcc
CFLAGS = -w -Wno-return-type -Wincompatible-pointer-types

MAIN = bin/main_ex2
TEST = bin/test_ex2
INTERFACCIA = src/Interfaccia.h

MAIN_OBJ = build/Main.o
LIB_OBJ = build/Lib-Generale.o
TEST_MAIN_OBJ = build/Test.o
TEST_LIB_OBJ = build/Lib-Test.o build/unity.o

all: $(MAIN) $(TEST)

$(MAIN): $(MAIN_OBJ) $(LIB_OBJ) | bin
	$(CC) $(MAIN_OBJ) $(LIB_OBJ) -o $@

$(TEST): $(TEST_MAIN_OBJ) $(LIB_OBJ) $(TEST_LIB_OBJ) | bin
	$(CC) $(TEST_MAIN_OBJ) $(LIB_OBJ) $(TEST_LIB_OBJ) -o $@

build/%.o: src/%.c $(INTERFACCIA) | build
	$(CC) $(CFLAGS) -c $< -o $@

build bin:
	mkdir -p $@

clean:
	rm -rf build/* bin/* outfile.csv
