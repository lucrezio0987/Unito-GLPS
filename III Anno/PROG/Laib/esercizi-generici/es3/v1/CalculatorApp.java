package org.prog3.lab.week3.es3.v1;

import java.util.ArrayList;
import java.util.Random;

public class CalculatorApp {
	/**
	 * Class base (astratta) per le Factory di CNumber.
	 * Sottoclassi concrete dovranno implementare il metodo randomInstance
	 *
	 * @param <T>
	 */
	abstract static class RandomFactory<T extends Comparable<T>> {
		Random r;

		RandomFactory() {
			r = new Random();
		}

		/**
		 * @return un elemento CNumber<T> estratto a caso usando il generatore
		 * 		   di numeri casuali istanziato nel costruttore.
		 */
		public abstract Calculator.CNumber<T> randomInstance();
	}

	/**
	 * Crea instance casuali di CNumber<Integer>
	 */
	static class CIntFactory extends RandomFactory<Integer> {
		public Calculator.CNumber<Integer> randomInstance() {
			return new CInt(r.nextInt(100));
		}
	}

	/**
	 * Crea instance casuali di CNumber<Double>
	 */
	static class CDoubleFactory extends RandomFactory<Double> {
		public Calculator.CNumber<Double> randomInstance() {
			return new CDouble(r.nextDouble());
		}
	}

	/**
	 * Crea instance casuali di CNumber<Float>
	 */
	static class CFloatFactory extends RandomFactory<Float> {
		public Calculator.CNumber<Float> randomInstance() {
			return new CFloat(r.nextFloat());
		}
	}


	static class CInt  extends Calculator.CNumber<Integer>  {
		CInt(Integer number) {
			super(number);
		}

		@Override
		public Calculator.CNumber<Integer> add(Calculator.CNumber<Integer> other) {
			CInt otherInt = (CInt) other;
			return new CInt(number + otherInt.number);
		}
	}

	static class CDouble extends Calculator.CNumber<Double> {

		CDouble(Double number) {
			super(number);
		}

		@Override
		public Calculator.CNumber<Double> add(Calculator.CNumber<Double> other) {
			CDouble otherDouble = (CDouble) other;
			return new CDouble(number + otherDouble.number);
		}
	 }
	static class CFloat extends Calculator.CNumber<Float> {
		CFloat(Float number) {
			super(number);
		}

		@Override
		public Calculator.CNumber<Float> add(Calculator.CNumber<Float> other) {
			CFloat otherFloat = (CFloat) other;
			return new CFloat(number + otherFloat.number);
		}
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
		processNumbers( new CIntFactory() );
		processNumbers( new CDoubleFactory() );
		processNumbers( new CFloatFactory() );
    }

    /**
	 * Genera una lista di elementi numerici casuali di tipo T
	 * e ne calcola sommatoria e valore massimo.
	 *
	 * @param gn	implementazione dell'interfaccia GetRandomNumber<T> che
	 * 				fornisce un metodo per ottenere numeri casuali di tipo T
	 */
	private static <T extends Comparable<T>> void processNumbers(RandomFactory<T> gn) {
		ArrayList<Calculator.CNumber<T>> numbers = new ArrayList<>();
		for (int i=0; i<3; i++) {
			numbers.add(gn.randomInstance());
		}

		Calculator.printNumbers(numbers);
		System.out.println("Max: " + Calculator.max(numbers) +
						   "; Tot: " + Calculator.sum(numbers));
		System.out.println();
	}
}