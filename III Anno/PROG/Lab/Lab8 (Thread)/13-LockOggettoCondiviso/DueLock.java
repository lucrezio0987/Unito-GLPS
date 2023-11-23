import java.util.*;

class Stampa extends Thread {
	Object lock1;
	Object lock2;
	String space;

	public Stampa(Object l1, Object l2, String space) {
		lock1 = l1;
		lock2 = l2;
		this.space = space;
	}

// simuliamo l'esecuzione di due task complessi (1 e 2), demarcati da init e fin.
// task 1 e task 2 possono essere eseguiti liberamente, ma la loro esecuzione
// deve essere atomica.
// Se non facciamo eseguire i task in sezione critica, i thread interferiscono
// durante l'esecuzione dei task.
// Se usiamo un solo lock, si sincronizzano correttamente
// NB: in un metodo si possono usare più lock per sincronizzarsi
// separatamente su risorse diverse e migliorare il parallelismo
// dell'applicazione. Ma questo va bene solo se si usano risorse diverse.

	public void run() {
		Random r = new Random();
		int n = r.nextInt();
		for (int i=0; i<7; i++) {
			synchronized(lock1) {
				System.out.println(space + Thread.currentThread().getName() + ": init1");
				try {
					Thread.sleep(200);
				} catch (InterruptedException e)
					{System.out.println(e.getMessage());}
				System.out.println(space + Thread.currentThread().getName() + ": fin1");
			}
			try {
				Thread.sleep(400);
			} catch (InterruptedException e)
					{System.out.println(e.getMessage());}
			synchronized(lock2) {
				System.out.println(space + Thread.currentThread().getName() + ": init2");
				try {
					Thread.sleep(200);
				} catch (InterruptedException e)
					{System.out.println(e.getMessage());}
				System.out.println(space + Thread.currentThread().getName() + ": fin2");
			}
		}
	}
}

class DueLock {
	public static void main(String[] args) {
		Object lock1 = new Object();
		Object lock2 = new Object();
		// uso un solo lock per non far interrompere l'esecuzione delle operazioni
		//Stampa t1 = new Stampa(lock1, lock1, "");
		//Stampa t2 = new Stampa(lock1, lock1, "                      ");
		// uso due lock: le operazioni si interfogliano - OK solo se operano su risorse diverse
		Stampa t1 = new Stampa(lock1, lock2, "");
		Stampa t2 = new Stampa(lock1, lock2, "                      ");
		t1.start();
		t2.start();
	}
}