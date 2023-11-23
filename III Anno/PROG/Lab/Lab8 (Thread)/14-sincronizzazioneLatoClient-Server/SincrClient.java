class Stampa extends Thread {
	MiaClasse obj;

	public Stampa(MiaClasse o) {
		obj = o;
	}

// lato client si sincronizza l'accesso all'oggetto condiviso usando il lock dell'oggetto
// il client può sincronizzare una operazione complessa, come il ciclo qui sotto.
	public void run() {
		synchronized(obj) {
			for (int i=0; i<7; i++) {
				try {
					Thread.sleep(200);
				} catch (InterruptedException e)
					{System.out.println(e.getMessage());}
				System.out.println(Thread.currentThread().getName() + ": " + obj);
			}
		}
	}
}

class MiaClasse {
	public String toString() {
		return "stato dell'oggetto";
	}
}


public class SincrClient {
	public static void main(String[] args) {
		MiaClasse obj = new MiaClasse();
		Stampa t1 = new Stampa(obj);
		Stampa t2 = new Stampa(obj);
		t1.start();
		t2.start();
	}
}