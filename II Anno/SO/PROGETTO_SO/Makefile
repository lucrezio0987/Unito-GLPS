all: Progetto_SO
Progetto_SO: Giornaliera_make Movimento_make Maelstrom_make Master_make
Master_make: Master
	gcc -std=c89 -Wpedantic -w  Master.c       -o Master
Giornaliera_make: Giornaliera 
	gcc -std=c89 -Wpedantic -w  Giornaliera.c  -o Giornaliera
Movimento_make: Movimento
	gcc -std=c89 -Wpedantic -w  Movimento.c    -o Movimento
Maelstrom_make : Maelstrom
	gcc -std=c89 -Wpedantic -w  Maelstrom.c    -o Maelstrom
clean:
	rm -f Master Giornaliera Movimento Maelstrom