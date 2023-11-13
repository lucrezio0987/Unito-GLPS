
class MyRun implements Runnable {
	public void run() {
		System.out.println(Thread.currentThread().getName());
	}
}

class ProvaRunnable {
	public static void main(String[] args) {
		MyRun mr = new MyRun();
					// ogni start fa partire un nuovo thread;
		new Thread(mr).start();
		new Thread(mr).start();
	}
}