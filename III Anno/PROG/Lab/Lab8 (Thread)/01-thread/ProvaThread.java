class MyThread extends Thread {
	public void run() {
		System.out.println("Sono il thread " + getName());
	}
}


class ProvaThread {
	public static void main(String[] args) {
		new MyThread().start();
		new MyThread().start();
		System.out.println("Sono il thread " + (Thread.currentThread()).getName());
	}
}