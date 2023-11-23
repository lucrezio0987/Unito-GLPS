/* In questo caso entrambi i thread partono con counter = 0
ma l'esecuzione parallela fa sí che la variabile condivisa
possa essere modificata in modo inconsistente dai due */

class MiaClasse implements Runnable {
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
}

public class Interferenza2Runnable {

	public static void main(String[] args) {
		MiaClasse mia = new MiaClasse();
		Thread t1 = new Thread(mia);
		Thread t2 = new Thread(mia);
		t1.start();
		t2.start();
		System.out.println("fine main");
	}
}