
// specifico il codice da eseguire in flussi paralleli come Runnable

class PingPong implements Runnable {
	private int delay;
	private String name;

    public PingPong(String str, int d) {
		delay = d;
		name = str;
    }

    public void run() {
        for (int i = 1; i < 40; i++) {
            System.out.println(name);
            try {
               //sleep(delay); // non è metodo di Runnable!!!
               Thread.sleep(delay);
            } catch (InterruptedException e) {return;}
        }
        System.out.println("DONE! " + name);
    }
}

public class PingPongApp2 {

    public static void main (String[] args) {
        Thread t1 = new Thread(new PingPong("PING", 150));
        t1.start();
        Thread t2 = new Thread(new PingPong("          PONG", 150));
        t2.start();
        //t2.start(); // se invocato + di 1 volta il metodo genera eccezione

    }
}