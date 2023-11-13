import java.util.*;

class MyDaemon extends Thread {
	private int delay;

    public MyDaemon(String str, int d) {
        super(str);
		delay = d;
		setDaemon(true);
    }

    public void run() {
		Random r = new Random();
        while(true) {
            System.out.println(r.nextInt());
            try {
               sleep(delay);
            } catch (InterruptedException e) {System.out.println(e.getMessage());
            									return;}
        }
    }
}

public class DaemonApp1 {

    public static void main (String[] args) {
        Thread t1 = new MyDaemon("GeneratoreNumeri", 100);
        t1.start();
        try {
           Thread.sleep(4000);
            } catch (InterruptedException e) {System.out.println(e.getMessage());
            									return;}
    }
}