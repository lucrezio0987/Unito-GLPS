class Stampante {

	public static void stampa(String[] a) {
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

	public ThreadStampa(String[] arr) {
		a = arr;
	}

	public void run() {
		Stampante.stampa(a);}
	}

public class StampaStatic {
	public static void main(String[] args) {
		String[] a = {"aa1", "aa2", "aa3", "aa4"};
		String[] b = {"bb1", "bb2", "bb3", "bb4"};
		ThreadStampa t1 = new ThreadStampa(a);
		ThreadStampa t2 = new ThreadStampa(b);
		t1.start();
		t2.start();
	}
}