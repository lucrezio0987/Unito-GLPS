package org.prog3.lab.week3.es3.v2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Classe che offre alcuni metodi statici per eseguire operazioni su
 * liste di elementi di tipo numerico.
 *
 * NB: per raggiungere un buon compromesso tra semplicita' dei metodi
 * e possibilita' di riuso del codice, il valore di ritorno per i metodi
 * sum() e max() e' di tipo Number; cio' comporta che, anche nel caso di
 * una lista di numeri interi, venga ritornato un numero decimale.
 */
public class Calculator {

	/**
	 * Stampa il contenuto di una lista numerica.
	 */
    public static void printNumbers(List<? extends Number> list) {
        for (Number el : list) {
            System.out.println(el);
        }
    }

    /**
     * Esegue la sommatoria degli elementi di una lista numerica.
	 * Se la lista e' vuota, il risultato e' 0.
	 *
	 * @param list	lista generica di elementi numerici,
	 *              di cui calcolare la sommatoria
	 * @return 		sommatoria degli elementi della lista, oppure 0
	 * 				se la lista e' vuota.
	 */
    public static Number sum(List<? extends Number> list) {
		if(list.size() == 0) {
			return 0;
		}

		var sum = 0.0;
		for( var number: list ) {
			sum += number.doubleValue();
		}

		return sum;
	}

	/**
	 * Ritorna il valore piu' grande presente nella lista.
	 * Lancia un'eccezione se la lista e' vuota.
	 *
	 * @param list	lista generica di elementi numerici,
	 * 				di cui trovare il massimo
	 * @return		l'elemento massimo della lista.
	 * @throws IndexOutOfBoundsException if the list is empty
	 */
    public static Number max(List<? extends Number> list) throws IndexOutOfBoundsException {
    	if (list.isEmpty()) {
    		throw new IndexOutOfBoundsException("Trying to find max number in empty list");
		}

		var max = list.get(0);
		for(var el: list) {
			if( el.doubleValue() > max.doubleValue() ) {
				max = el;
			}
		}

		return max;
    }
}