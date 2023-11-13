class MyThread extends Thread {

    public MyThread() {
        super();
        setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
				public void uncaughtException(Thread th, Throwable exc) {
					System.out.println("Eccezione");
				}
			});
    }

    public void run() {
        // genero eccezione non controllata (RunTimeException);
        String s=null;
        s.toString();
    }
}

public class EccezioneApp1 {

    public static void main (String[] args) {
        Thread t1 = new MyThread();
        t1.start();
        System.out.println("IO CONTINUO...");
        System.out.println("IO CONTINUO...");
        System.out.println("IO CONTINUO...");
        System.out.println("IO CONTINUO...");
        System.out.println("IO CONTINUO...");
        System.out.println("IO CONTINUO...");
        System.out.println("IO CONTINUO...");
        System.out.println("IO CONTINUO...");
        System.out.println("IO CONTINUO...");
        System.out.println("IO CONTINUO...");
        System.out.println("IO CONTINUO...");
        System.out.println("IO CONTINUO...");
        System.out.println("IO CONTINUO...");
    }
}