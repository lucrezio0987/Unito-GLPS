#!/bin/bash

echo -e "\033[1;32m:::::::::::::::::::::::::::"
echo -e "\033[1;32m:: PROGETTO DI ALGORITMI ::"
echo -e "\033[1;32m:::::::::::::::::::::::::::"
echo -e ""
echo -e "\033[1;34m  1.\033[0;32m Compila ed esegui il programma in ex1"
echo -e "\033[1;34m  2.\033[0;32m Compila ed esegui il programma in ex2"
echo -e "\033[1;34m  3.\033[0;32m Compila ed esegui il programma in ex3"
echo -e "\033[1;34m  4.\033[0;32m Compila ed esegui il programma in ex4"
echo -e "\033[1;34m  5.\033[0;32m Esci dallo script"
echo -e ""
echo -e -n "\033[1;32mScegli un'opzione: \033[1;34m"
read scelta
echo -e ""

case $scelta in
    1)
        cd ex1
        echo -e "\033[1;32m::\033[0;91m MAKE:"
        echo -e "\033[0;33m"
        make
        ./build/main_ex1
        echo -e ""
        echo -e "\033[1;32m::\033[0;91m OUTPUT:"
        echo -e "\033[0;37m"
        cat outfile.csv
        echo -e ""
        echo -e "\033[1;32m::\033[0;91m USCITA..."
        cd ..
        ;;
    2)
        cd ex2
        make 
        echo -e "TODO: Stampa risulstato"
        cd ..
        ;;
    3)
        cd ex3
        echo -e "TODO: intero esercizio"
        cd ..
        ;;
    4)  
        cd ex3
        echo -e "TODO: intero esercizio (non so nemmeno dove sia la consegna)"
        cd ..
        ;;
    5)
        exit 0
        ;;
    *)
        echo -e "Scelta non valida"
        ;;
esac