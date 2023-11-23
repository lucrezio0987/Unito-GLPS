class C {
// NB: fondamentale che i sia private, se no risulta comunque accessibile senza
// usare i metodi synchronized
	private int i=0;

	synchronized public void m() {
		for(int k = 0; k < 10; k++) i++;
		for(int k = 0; k < 10; k++) i--;
		System.out.println("m(): " + Thread.currentThread().getName());
		//String s=null; s.equals("errore"); // genero eccezione
	}

	synchronized public int getI() {
		System.out.println("getI(): " + Thread.currentThread().getName());
		return i;
	}

	// non serve che questo metodo sia synchronized perché non accede alla variabile condivisa
	public static void saluta() {
		System.out.println("saluta: Thread " + Thread.currentThread().getName() + ": CIAO");
	}
}

class T extends Thread {
	private int num;
	private C c;

	public T(C y) {
		c = y;
	}

	public void run() {
		for (int i=0; i<2; i++) {
			c.m();
			System.out.println(Thread.currentThread().getName() + ": c.i= " + c.getI());
		}
	}
}

public class AccessoSynchronized {
	public static void main(String[] args) {
		C c = new C();
		T t1 = new T(c);
		T t2 = new T(c);
		t1.start();
		t2.start();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e)
				{System.out.println(e.getMessage());}
		c.saluta();
		System.out.println("Il main ha finito");
	}
}