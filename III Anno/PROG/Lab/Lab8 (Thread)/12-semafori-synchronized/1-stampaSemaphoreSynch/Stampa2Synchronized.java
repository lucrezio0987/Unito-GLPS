class Stampante {

// L'oggetto da utilizzare in mutua esclusione regola
// l'invocazione dei propri metodi dichiarandoli synchronized.

 	public synchronized void stampa(String[] a) {
		try {
			for(int i = 0; i < a.length; i++) {
				Thread.sleep((long)(Math.random() * 100));
				System.out.println(a[i]);
			}
			String s = a[4];
		}	catch(InterruptedException e) {System.out.println(e.getMessage());}
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

class Stampa2Synchronized {
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