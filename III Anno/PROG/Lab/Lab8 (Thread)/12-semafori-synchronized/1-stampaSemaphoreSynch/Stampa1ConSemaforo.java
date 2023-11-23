import java.util.concurrent.*;

// L'oggetto da utilizzare in mutua esclusione usa il semaforo
// per regolare l'esecuzione dei propri metodi

class Stampante {
	private Semaphore sem = new Semaphore(1);

	public void stampa(String[] a) {
		try {
			sem.acquire();
			for(int i = 0; i < a.length; i++) {
				Thread.sleep((long)(Math.random() * 100));
				System.out.println(a[i]);
			}
			// attenzione ad accertarvi che venga sempre eseguita la release!!!! Va
			// messa in una clausola finally
			String s = a[4];
			//sem.release();
		}	catch(InterruptedException e) {System.out.println(e.getMessage());}
			finally {sem.release();}
	}
}

class ThreadStampa extends Thread {
	String[] a;
	Stampante st;
	public ThreadStampa(String[] arr, Stampante s) {
		a = arr;
		st = s;
	}

	public void run() {
		st.stampa(a);
	}
}

public class Stampa1ConSemaforo {
	public static void main(String[] args) {
		Stampante st = new Stampante();
		String[] a = {"aa1", "aa2", "aa3", "aa4"};
		String[] b = {"bb1", "bb2", "bb3", "bb4", "bb5"};
		ThreadStampa t1 = new ThreadStampa(a,st);
		ThreadStampa t2 = new ThreadStampa(b,st);
		t1.start();
		t2.start();
	}
}