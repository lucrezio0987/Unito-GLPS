class MyThread extends Thread {
	public MyThread(String s)
	{	super(s); // invoco super che da' il nome al thread
		start();  // faccio partire il thread direttamente nel costruttore
	}

	public void run() {
		System.out.println("Sono il thread " + getName());
	}
}


class ProvaThread2 {
	public static void main(String[] args) {
		new MyThread("primo");
		new MyThread("secondo");
		System.out.println("Sono il thread " + Thread.currentThread().getName());
	}
}