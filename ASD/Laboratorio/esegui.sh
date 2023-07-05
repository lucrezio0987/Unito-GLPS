#!/bin/bash

# Ottiene le dimensioni del terminale
rows=$(tput lines)
cols=$(tput cols)
tput civis      # Nasconde il cursore del terminale
tput rmcup      # Disabilita lo scrolling del terminale

Menu_1() {
    clear
    echo -e "\033[1;32m:::::::::::::::::::::::::::"
    echo -e "\033[1;32m:: PROGETTO DI ALGORITMI ::"
    echo -e "\033[1;32m:::::::::::::::::::::::::::"
    echo -e ""
    echo -e "\033[1;34m  -.\033[1;37m Eserczio 1: ex1 \033[0;33m[\033[0;35m-\033[0;33m]"
    echo -e "\033[1;34m     1.\033[0;37m Esegui\033[0;33m[\033[0;35mX\033[0;33m]"
    echo -e "\033[1;34m     2.\033[0;37m Unit test\033[0;33m[\033[0;35m-\033[0;33m]"
    echo -e "\033[1;34m  -.\033[0;37m Eserczio 2: ex2 \033[0;33m[\033[0;35m-\033[0;33m]"
    echo -e "\033[1;34m  -.\033[0;37m Eserczio 3: ex3 \033[0;33m[\033[0;35m \033[0;33m]"
    echo -e "\033[1;34m  -.\033[0;37m Eserczio 4: ex4 \033[0;33m[\033[0;35m \033[0;33m]"
    echo -e "\033[1;34m  *.\033[0;32m << Torna Indietro"
    echo -e "\n"
    echo -e "\033[0;91m NOTE:"
    echo -e "\033[0;36m"
    cat note.txt
    echo -e "\n"
    echo -e ""
    echo -e -n "\033[1;32mScegli un'opzione: \033[1;34m"
    read scelta
    echo -e "\033[1;37m"

    clear
    case $scelta in
        1)  # ESEGUI
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
            ;;
        2)  # TEST
            echo -e "\033[1;32m::\033[0;91m TODO: \033[0;37mTEST da implementare"
            ;;
        *)
            return 1
            ;;
    esac

    echo -e "\n\n"
    echo -e "\033[0;32m << Torna al Menu..."
    echo -e "\033[1;37m"
    read -n1 -s -r
}

Menu_2() {
    clear
    echo -e "\033[1;32m:::::::::::::::::::::::::::"
    echo -e "\033[1;32m:: PROGETTO DI ALGORITMI ::"
    echo -e "\033[1;32m:::::::::::::::::::::::::::"
    echo -e ""
    echo -e "\033[1;34m  -.\033[0;37m Eserczio 1: ex1 \033[0;33m[\033[0;35m-\033[0;33m]"
    echo -e "\033[1;34m  -.\033[1;37m Eserczio 2: ex2 \033[0;33m[\033[0;35m-\033[0;33m]"
    echo -e "\033[1;34m     1.\033[0;37m Esegui\033[0;33m[\033[0;35m-\033[0;33m]"
    echo -e "\033[1;34m     2.\033[0;37m Unit test\033[0;33m[\033[0;35m \033[0;33m]"
    echo -e "\033[1;34m  -.\033[0;37m Eserczio 3: ex3 \033[0;33m[\033[0;35m \033[0;33m]"
    echo -e "\033[1;34m  -.\033[0;37m Eserczio 4: ex4 \033[0;33m[\033[0;35m \033[0;33m]"
    echo -e "\033[1;34m  *.\033[0;32m << Torna Indietro"
    echo -e "\n"
    echo -e "\033[0;91m NOTE:"
    echo -e "\033[0;36m"
    cat note.txt
    echo -e "\n"
    echo -e ""
    echo -e -n "\033[1;32mScegli un'opzione: \033[1;34m"
    read scelta
    echo -e "\033[1;37m"

    clear
    case $scelta in
        1)  # ESEGUI
            echo -e "\033[1;32m::\033[0;91m MAKE:"
            echo -e "\033[0;33m"
            make
            echo -e ""
            echo -e "\033[1;32m::\033[0;91m ESECUZIONE:"
            echo -e "\033[0;36m"
            ./build/main_ex2
            echo -e ""
            echo -e "\033[1;32m::\033[0;91m USCITA..."
            ;;
        2)  # TEST
            echo -e "\033[1;32m::\033[0;91m TODO: \033[0;37mTEST da implementare"
            ;;
        *)
            return 1
            ;;
    esac

    echo -e "\n\n"
    echo -e "\033[0;32m << Torna al Menu..."
    echo -e "\033[1;37m"
    read -n1 -s -r
}

Menu_3() {
    clear
    echo -e "\033[1;32m:::::::::::::::::::::::::::"
    echo -e "\033[1;32m:: PROGETTO DI ALGORITMI ::"
    echo -e "\033[1;32m:::::::::::::::::::::::::::"
    echo -e ""
    echo -e "\033[1;34m  -.\033[0;37m Eserczio 1: ex1 \033[0;33m[\033[0;35m-\033[0;33m]"
    echo -e "\033[1;34m  -.\033[0;37m Eserczio 2: ex2 \033[0;33m[\033[0;35m-\033[0;33m]"
    echo -e "\033[1;34m  -.\033[1;37m Eserczio 3: ex3 \033[0;33m[\033[0;35m \033[0;33m]"
    echo -e "\033[1;34m  -.\033[0;37m Eserczio 4: ex4 \033[0;33m[\033[0;35m \033[0;33m]"
    echo -e "\033[1;34m  *.\033[0;32m << Torna Indietro"
    echo -e "\n"
    echo -e "\033[0;91m NOTE:"
    echo -e "\033[0;36m"
    cat note.txt
    echo -e "\n"
    echo -e ""
    echo -e -n "\033[1;32mScegli un'opzione: \033[1;34m"
    read scelta
#    echo -e "\033[1;37m"
#   
#    clear
#    case $scelta in
#        1)  # ESEGUI
#            echo -e "\033[1;32m::\033[0;91m MAKE:"
#            echo -e "\033[0;33m"
#            make
#            echo -e ""
#            echo -e "\033[1;32m::\033[0;91m ESECUZIONE:"
#            echo -e "\033[0;36m"
#            ./build/main_ex2
#            echo -e ""
#            echo -e "\033[1;32m::\033[0;91m USCITA..."
#            ;;
#        2)  # TEST
#            echo -e "\033[1;32m::\033[0;91m TODO: \033[0;37mTEST da implementare"
#            ;;
#        *)
#            return 1
#            ;;
#    esac
#
#    echo -e "\n\n"
#    echo -e "\033[0;32m << Torna al Menu..."
#    echo -e "\033[1;37m"
#    read -n1 -s -r
}

Menu_4() {
    clear
    echo -e "\033[1;32m:::::::::::::::::::::::::::"
    echo -e "\033[1;32m:: PROGETTO DI ALGORITMI ::"
    echo -e "\033[1;32m:::::::::::::::::::::::::::"
    echo -e ""
    echo -e "\033[1;34m  -.\033[0;37m Eserczio 1: ex1 \033[0;33m[\033[0;35m-\033[0;33m]"
    echo -e "\033[1;34m  -.\033[0;37m Eserczio 2: ex2 \033[0;33m[\033[0;35m-\033[0;33m]"
    echo -e "\033[1;34m  -.\033[0;37m Eserczio 3: ex3 \033[0;33m[\033[0;35m \033[0;33m]"
    echo -e "\033[1;34m  -.\033[1;37m Eserczio 4: ex4 \033[0;33m[\033[0;35m \033[0;33m]"
    echo -e "\033[1;34m  *.\033[0;32m << Torna Indietro"
    echo -e "\n"
    echo -e "\033[0;91m NOTE:"
    echo -e "\033[0;36m"
    cat note.txt
    echo -e "\n"
    echo -e ""
    echo -e -n "\033[1;32mScegli un'opzione: \033[1;34m"
    read scelta
#    echo -e "\033[1;37m"
#
#    clear
#    case $scelta in
#        1)  # ESEGUI
#            echo -e "\033[1;32m::\033[0;91m MAKE:"
#            echo -e "\033[0;33m"
#            make
#            echo -e ""
#            echo -e "\033[1;32m::\033[0;91m ESECUZIONE:"
#            echo -e "\033[0;36m"
#            ./build/main_ex2
#            echo -e ""
#            echo -e "\033[1;32m::\033[0;91m USCITA..."
#            ;;
#        2)  # TEST
#            echo -e "\033[1;32m::\033[0;91m TODO: \033[0;37mTEST da implementare"
#            ;;
#        *)
#            return 1
#            ;;
#    esac
#
#    echo -e "\n\n"
#    echo -e "\033[0;32m << Torna al Menu..."
#    echo -e "\033[1;37m"
#    read -n1 -s -r
}

Menu_0() {
    clear
    echo -e "\033[1;32m:::::::::::::::::::::::::::"
    echo -e "\033[1;32m:: PROGETTO DI ALGORITMI ::"
    echo -e "\033[1;32m:::::::::::::::::::::::::::"
    echo -e ""
    echo -e "\033[1;34m  1.\033[0;37m Eserczio 1: ex1 \033[0;33m[\033[0;35m-\033[0;33m]"
    echo -e "\033[1;34m  2.\033[0;37m Eserczio 2: ex2 \033[0;33m[\033[0;35m-\033[0;33m]"
    echo -e "\033[1;34m  3.\033[0;37m Eserczio 3: ex3 \033[0;33m[\033[0;35m \033[0;33m]"
    echo -e "\033[1;34m  4.\033[0;37m Eserczio 4: ex4 \033[0;33m[\033[0;35m \033[0;33m]"
    echo -e "\033[1;34m  *.\033[0;32m Esci dallo script"
    echo -e ""
    echo -e -n "\033[1;32mScegli un'opzione: \033[1;34m"
    read scelta
    echo -e "\033[1;37m"
    
    case $scelta in
        1)  
            cd ex1
            Menu_1
            ;;
        2)  
            cd ex2
            Menu_2
            ;;
        3)  
            cd ex3
            Menu_3
            ;;
        4)  
            cd ex4
            Menu_4
            ;;
        *)
            tput cnorm
            tput rmcup -T xterm
            tput sgr0
            clear
            exit 0
            ;;
    esac

    cd ..
}

while true; do
    clear
    Menu_0
done