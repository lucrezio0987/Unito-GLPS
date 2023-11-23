class C {
	public int i=0;
	public void m() {
		for(int k = 0; k < 100000; k++) i++;
		for(int k = 0; k < 100000; k++) i--;
	}
}
class T extends Thread {
	private int num;
	private C c;
	public T(int x, C y) {
		num = x; c = y;
	}

	public void run() {
		for (int i=0; i<10; i++) {
			c.m(); System.out.println("Thread " + num + ": c.i= " + c.i);
		}
	}
}
public class AccessoWrong {
	public static void main(String[] args) {
		C c = new C();
		T t1 = new T(1, c);
		T t2 = new T(2, c);
		t1.start();
		t2.start();
		try {
			t1.join();
		} catch(InterruptedException e1) {System.out.println(e1.getMessage());}
		try {
			t2.join();
		} catch(InterruptedException e1) {System.out.println(e1.getMessage());}
		System.out.println("Main: c.i= " + c.i);
	}
}