#!/bin/bash
gcc -std=c89 -Wpedantic -w Master.c -o Master;
echo "........................................."                    > file
echo "::: COMPILAZIONE INIZIALE             :::"                    >> file
echo ":::...................................:::"                    >> file
echo " "                                                            >> file
echo ">>>   MASTER COMPILATO          <<<"                          >> file
gcc -std=c89 -Wpedantic -w Giornaliera.c -o Giornaliera;
echo ">>>   GIORNALIERA COMPILATO     <<<"                          >> file
gcc -std=c89 -Wpedantic -w Movimento.c -o Movimento;
echo ">>>   MOVIMENTO_NAVI COMPILATO  <<<"                          >> file
gcc -std=c89 -Wpedantic -w Maelstrom.c -o Maelstrom;
echo ">>>   MOVIMENTO_NAVI COMPILATO  <<<"                          >> file
echo " "                                                            >> file
echo "........................................."                    >> file
echo "::: INIZIO ESECUZIONE                 :::"                    >> file
echo ":::...................................:::"                    >> file
echo " "                                                            >> file
./Master                                                            >> file
#echo ">>>   FINE MASTER               <<<"                          >> file
#echo ">>>   INIZIO GIORNALEIRA        <<<"                          >> file
#./Giornaliera                                                       >> file
#echo ">>>   FINE GIORNALEIRA          <<<"                          >> file
#echo ">>>   INIZIO MOVIMENTO_NAVI     <<<"                          >> file
#./movimento_navi                                                    >> file
#echo ">>>   FINE MOVIMENTO_NAVI       <<<"                          >> file
echo " "                                                            >> file
echo "........................................."                    >> file
echo "::: TERMINE ESECUZIONE                :::"                    >> file
echo ":::...................................:::"                    >> file
echo " "                                                            >> file
echo "............................."
echo "::: Esecuzione terminata  :::"
echo "::: > output in FILE      :::"
echo ":::.......................:::"
echo ""
rm Master Giornaliera Movimento Maelstrom;
echo ""
echo "............................."
echo "::: (eseguibili rimossi)  :::"
echo ":::.......................:::"
echo "........................................."                    >> file
echo "::: controllo IPCs attivi             :::"                    >> file
echo ":::...................................:::"                    >> file
ipcs                                                                >> file
echo "........................................."                    >> file
echo "::: tentativo rimozione IPCs          :::"                    >> file
echo ":::...................................:::"                    >> file
ipcrm -a;
ipcs                                                                >> file
exit 0