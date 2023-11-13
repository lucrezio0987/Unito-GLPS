
// creo due thread e verifico alternanza di esecuzione
// se applico priorità diverse non ottengo effetti distinguibili nell'alternanza


class PingPong extends Thread {
	private int delay;

    public PingPong(String str, int d) {
        super(str);
		delay = d;
    }

    public void run() {
        for (int i = 1; i < 40; i++) {
            System.out.println(getName());
            try {
               sleep(delay);
            } catch (InterruptedException e) {return;}
        }
        System.out.println("DONE! " + getName());
    }
}

public class PingPongApp1 {

    public static void main (String[] args) {
        Thread t1 = new PingPong("PING", 150);
        //t1.setPriority(Thread.MAX_PRIORITY);
        t1.start();
        Thread t2 = new PingPong("          PONG", 150);
        //t2.setPriority(Thread.MIN_PRIORITY);
        t2.start();

    }
}