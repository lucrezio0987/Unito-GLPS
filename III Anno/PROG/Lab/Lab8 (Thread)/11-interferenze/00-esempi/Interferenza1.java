/* In questo esempio la variabile counter è privata del singolo thread
per cui NON ci sono problemi di accesso in mutua esclusione */


public class Interferenza1 extends Thread {
	private int counter = 0;

	public void run() {
		System.out.println("counter = " + counter);
		incr();
		return;
	}

	public void incr() {
		counter++;
		System.out.println("dopo incr counter = " + counter);
		return;
	}

	public static void main(String[] args) {
		Interferenza1 t1 = new Interferenza1();
		Interferenza1 t2 = new Interferenza1();
		t1.start();
		t2.start();
		System.out.println("fine main");
	}
}