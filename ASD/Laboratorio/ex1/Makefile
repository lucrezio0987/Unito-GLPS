MAIN = src/Main.c
INTERFACCIA = src/Interfaccia.h	
LIBRERIE = src/Lib-Generale.c src/Lib-MergeSorte.c src/Lib-BinaryInsertionSort.c

ESEGUIBILE = build/main_ex1

FLAG = -w

all: $(ESEGUIBILE)

$(ESEGUIBILE): $(MAIN) $(LIBRERIE)
	gcc $(FLAG) -o $(ESEGUIBILE) $(MAIN) $(LIBRERIE)

TEST = test/Test.c
ESEGUIBILE_TEST = build/test_ex1
TEST_LIBRERIE = test/unity.c src/Lib-test.c

test: $(TEST) $(LIBRERIE) 
$(CC) $(FLAG) -o $(ESEGUIBILE_TEST) $(TEST) $(LIBRERIE) $(TEST_LIBRERIE)

clean: 
	rm -rf build/*
	rm -rf outfile.csv
