#!/bin/bash

Menu_2() {
    case $1 in
        1) 
            cd ex1
            echo -e "\033[1;32m::\033[0;91m TODO: \033[0;37mTEST da implementare"
            cd ..
            ;;
        2) 
            cd ex2
            echo -e "\033[1;32m::\033[0;91m TODO: \033[0;37mTEST da implementare"
            cd ..
            ;;
        3) 
            cd ex3
            echo -e "\033[1;32m::\033[0;91m TODO: \033[0;37mTEST da implementare"
            cd ..
            ;;
        4) 
            cd ex4
            echo -e "\033[1;32m::\033[0;91m TODO: \033[0;37mTEST da implementare"
            cd ..
            ;;
        5) 
            ;;
        *) 
            exit 0
            ;;
    esac
}

Menu_1() {

    case $1 in
        1)
            cd ex1
            echo -e "\033[1;32m::\033[0;91m MAKE:"
            echo -e "\033[0;33m"
            make
            echo -e ""
            echo -e "\033[1;32m::\033[0;91m ESECUZIONE:"
            echo -e "\033[0;37m...Eseguito"
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
            echo -e "\033[1;32m::\033[0;91m MAKE:"
            echo -e "\033[0;33m"
            make
            echo -e ""
            echo -e "\033[1;32m::\033[0;91m ESECUZIONE:"
            echo -e "\033[0;37m"
            ./build/main_ex2
            echo -e ""
            echo -e "\033[1;32m::\033[0;91m USCITA..."
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
            scelta=1
            while [ $scelta -ne 5 ]; do
                echo -e "\033[1;32m::::::::::"
                echo -e "\033[1;32m:: TEST ::"
                echo -e "\033[1;32m::::::::::"
                echo -e ""
                echo -e "\033[1;34m  1.\033[0;37m Test \033[1;37mex1"
                echo -e "\033[1;34m  2.\033[0;37m Test \033[1;37mex2"
                echo -e "\033[1;34m  3.\033[0;37m Test \033[1;37mex3"
                echo -e "\033[1;34m  4.\033[0;37m Test \033[1;37mex4"
                echo -e "\033[1;34m  5.\033[0;32m Torna indietro"
                echo -e "\033[1;34m  *.\033[0;32m Esci dallo script"
                echo -e ""
                echo -e -n "\033[1;32mScegli un'opzione: \033[1;34m"
                read scelta
                echo -e ""

                Menu_2 "$scelta"
                
            done    
            ;;
        *)
            exit 0
            ;;
    esac
}

while true; do

    echo -e "\033[1;32m:::::::::::::::::::::::::::"
    echo -e "\033[1;32m:: PROGETTO DI ALGORITMI ::"
    echo -e "\033[1;32m:::::::::::::::::::::::::::"
    echo -e ""
    echo -e "\033[1;34m  1.\033[0;37m Compila ed esegui \033[1;37mex1"
    echo -e "\033[1;34m  2.\033[0;37m Compila ed esegui \033[1;37mex2"
    echo -e "\033[1;34m  3.\033[0;37m Compila ed esegui \033[1;37mex3"
    echo -e "\033[1;34m  4.\033[0;37m Compila ed esegui \033[1;37mex4"
    echo -e "\033[1;34m  5.\033[0;32m Test"
    echo -e "\033[1;34m  *.\033[0;32m Esci dallo script"
    echo -e ""
    echo -e -n "\033[1;32mScegli un'opzione: \033[1;34m"
    read scelta
    echo -e ""

    Menu_1 "$scelta"

    echo -e ""
done