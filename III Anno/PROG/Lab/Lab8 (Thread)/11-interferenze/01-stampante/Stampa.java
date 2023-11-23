class Stampante {

	public void stampa(String[] a) {
		for(int i = 0; i < a.length; i++) {
			try {
				Thread.sleep((long)(Math.random() * 100));
			}
			catch(InterruptedException e)
				{System.out.println(e.getMessage());}
			System.out.println(a[i]);
		}
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
		st.stampa(a);}
	}

public class Stampa {
	public static void main(String[] args) {
		Stampante st = new Stampante();
		String[] a = {"aa1", "aa2", "aa3", "aa4"};
		String[] b = {"bb1", "bb2", "bb3", "bb4"};
		ThreadStampa t1 = new ThreadStampa(a,st);
		ThreadStampa t2 = new ThreadStampa(b,st);
		t1.start();
		t2.start();
	}
}