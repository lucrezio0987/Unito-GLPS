all: build/Ordinamento.o bin/Ordinamento #build/Test.o build/unity.o bin/Test

build/Ordinamento.o: src/Libreria.c src/Interfaccia.h
	$(CC) $(CFLAGS) -c src/Libreria.c -o build/Libreria.o

bin/Ordinamento: src/Interfaccia.h build/Libreria.o src/Ordinamento.c
	$(CC) $(CFLAGS) -o bin/Ordinamento build/Libreria.o src/Ordinamento.c


# build/Test.o: src/Libreria.c src/Interfaccia.h src/unity.c src/unity.h src/unity_internals.h
# 	$(CC) $(CFLAGS) -c src/Libreria.c -o build/Libreria.o 
# 
# build/unity.o: src/unity.c src/unity.h src/unity_internals.h
# 	$(CC) $(CFLAGS) -c src/unity.c -o build/unity.o
# 
# bin/Test: src/Interfaccia.h build/Libreria.o src/Test.c	 build/unity.o src/unity.c src/unity.h src/unity_internals.h
# 	$(CC) $(CFLAGS) -o bin/Test build/Libreria.o src/Test.c build/unity.o

clean: 
	$(RM) -rf bin/*
	$(RM) -rf build/*
