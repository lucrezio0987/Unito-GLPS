package org.prog3.lab.week3.es3.v2;

import java.util.ArrayList;
import java.util.Random;

public class CalculatorApp {

	/**
	 * Interfaccia generica che definisce un metodo per ritornare un numero
	 * casuale di tipo T.
	 */
	interface GetRandomNumber<T extends Number> {
		T nextNumber();
	}

	/**
	 * Chiama tre volte il metodo processNumbers() per fare operazioni
	 * su liste di elementi di tipo int, double e float rispettivamente.
	 *
	 * NB: il parametro passato al metodo e' un metodo, con il quale Java
	 * costruisce una implementazione dell'interfaccia GetRandomNumber
	 * senza doverla definire esplicitamente nel codice.
	 *
	 * Informazioni a riguardo sono reperibili sulla documentazione Java:
	 * https://docs.oracle.com/javase/tutorial/java/javaOO/methodreferences.html
	 */
	public static void main(String[] args) {
		Random r = new Random();
		processNumbers( () -> r.nextInt(100));
		processNumbers( r::nextDouble);
		processNumbers( r::nextFloat);
    }

    /**
	 * Genera una lista di elementi numerici casuali di tipo T
	 * e ne calcola sommatoria e valore massimo.
	 *
	 * @param gn	implementazione dell'interfaccia GetRandomNumber<T> che
	 * 				fornisce un metodo per ottenere numeri casuali di tipo T
	 */
	private static <T extends Number> void processNumbers(GetRandomNumber<T> gn) {
		ArrayList<T> numbers = new ArrayList<>();
		for (int i=0; i<3; i++) {
			numbers.add(gn.nextNumber());
		}

		Calculator.printNumbers(numbers);
		System.out.println("Max: " + Calculator.max(numbers) +
						   "; Tot: " + Calculator.sum(numbers));
		System.out.println();
	}
}