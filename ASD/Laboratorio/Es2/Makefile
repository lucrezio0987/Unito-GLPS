MAIN = src/Main.c
INTERFACCIA = src/Interfaccia.h	
LIBRERIE = src/Lib-Generale.c

ESEGUIBILE = build/main_ex2

FLAG = -w

all: $(ESEGUIBILE)

$(ESEGUIBILE): $(SORGENTI) $(LIBRERIE)
	$(CC) $(FLAG) -o $(ESEGUIBILE) $(MAIN) $(LIBRERIE)

clean: 
	$(RM) -rf build/*
	$(RM) -rf outfile.csv
