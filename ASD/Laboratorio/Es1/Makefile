CC = gcc
CFLAGS = -Wall -Werror -w

all: es1
	@echo "::: Compilazione Terminata: $(shell tput bold)./es1 $(shell tput sgr0) per eseguire"

es1: main.o
	$(CC) $(CFLAGS) main.o -o es1

main.o: main.c libreria.h
	$(CC) $(CFLAGS) -c main.c

clean:
	rm -f *.o es1
