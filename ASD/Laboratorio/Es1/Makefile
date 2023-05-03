MAIN = src/Main.c
INTERFACCIA = src/Interfaccia.h	
LIBRERIE = src/Lib-Generale.c src/Lib-MergeSorte.c src/Lib-BinaryInsertionSort.c

ESEGUIBILE = build/main_ex1

FLAG = -w

all: $(ESEGUIBILE)

$(ESEGUIBILE): $(MAIN) $(LIBRERIE)
	$(CC) $(FLAG) -o $(ESEGUIBILE) $(MAIN) $(LIBRERIE)

TEST = src/Test_efficienza.c
ESEGUIBILE_TEST = build/test_ex1

test: $(TEST) $(LIBRERIE) 
	$(CC) $(FLAG) -o $(ESEGUIBILE_TEST) $(TEST) $(LIBRERIE)

clean: 
	$(RM) -rf build/*
	$(RM) -rf outfile.csv
