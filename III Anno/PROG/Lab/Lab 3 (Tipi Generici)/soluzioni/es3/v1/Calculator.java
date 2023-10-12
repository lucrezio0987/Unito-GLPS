package org.prog3.lab.week3.es3.v1;

import java.util.List;

/**
 * Classe che offre alcuni metodi statici per eseguire operazioni su
 * liste di elementi di tipo numerico.
 */
class Calculator {
	/**
	 * Questa classe astratta è definita per imporre che il tipo gestito dai container
	 * passati a add e max definiscano le operazioni di somma e confronto.
	 *
	 * @param <T> tipo degli elementi gestiti da CNumber (in genere un tipo numerico che implementa l'interfaccia Number)
	 */

	static abstract class CNumber<T extends Comparable<T>> implements Comparable<CNumber<T>> {
		T number;

		CNumber(T number) {
			this.number = number;
		}

		public abstract CNumber<T> add(CNumber<T> number);

		@Override
		public int compareTo(CNumber<T> o) {
			return number.compareTo(o.number);
		}

		@Override
		public String toString() {
			return number.toString();
		}
	}

	/**
	 * Stampa il contenuto di una lista numerica.
	 */
    public static <T extends Comparable<T>> void printNumbers(List<? extends CNumber<T>> list) {
        for (CNumber<T> el : list) {
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
    public static <T extends Comparable<T>> CNumber<T> sum(List<CNumber<T>> list) {
		if(list.size() == 0) {
			return null;
		}

		CNumber<T> sum = list.get(0);
		for( int i=1; i<list.size(); ++i ) {
			sum = sum.add(list.get(i));
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
	 * @throws IndexOutOfBoundsException if list is empty
	 */
    public static <T extends Comparable<T>> CNumber<T> max(List<CNumber<T>> list) throws IndexOutOfBoundsException {
    	if (list.isEmpty()) {
    		throw new IndexOutOfBoundsException("Trying to find max number in empty list");
		}

		var max = list.get(0);
		for(var el: list) {
			if( el.compareTo(max) > 0 ) {
				max = el;
			}
		}

		return max;
    }
}