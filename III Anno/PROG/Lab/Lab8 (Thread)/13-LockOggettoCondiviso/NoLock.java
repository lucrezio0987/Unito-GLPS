import java.util.*;

class Stampa extends Thread {
	String space;

	public Stampa(String space) {
		this.space = space;
	}

// simuliamo l'esecuzione di due task complessi (1 e 2), demarcati da init e fin.
// task 1 e task 2 possono essere eseguiti liberamente, ma la loro esecuzione
// deve essere atomica.
// Se non facciamo eseguire i task in sezione critica, i thread interferiscono
// durante l'esecuzione dei task
	public void run() {
		Random r = new Random();
		int n = r.nextInt();
		for (int i=0; i<7; i++) {
												// primo task: stampare init1 + fin1
			System.out.println(space + Thread.currentThread().getName() + ": init1");
			try {
				Thread.sleep(200);
			} catch (InterruptedException e)
					{System.out.println(e.getMessage());}
			System.out.println(space + Thread.currentThread().getName() + ": fin1");

												// secondo task: stampare init2 + fin2
			System.out.println(space + Thread.currentThread().getName() + ": init2");
			try {
				Thread.sleep(200);
			} catch (InterruptedException e)
				{System.out.println(e.getMessage());}
			System.out.println(space + Thread.currentThread().getName() + ": fin2");
		}
	}
}

class NoLock {
	public static void main(String[] args) {
		Stampa t1 = new Stampa("");
		Stampa t2 = new Stampa("                      ");
		t1.start();
		t2.start();
	}
}