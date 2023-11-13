class SimpleThread extends Thread {
    public SimpleThread(String str) {
        super(str);
    }
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(i + " " + getName());
            try {
                sleep((long)(Math.random() * 1000));
            } catch (InterruptedException exc)
					{System.out.println("Eccezione: "+ exc.getMessage());}
        }
        System.out.println("DONE! " + getName());
    }
}

public class TwoThreadsTest {
    public static void main (String[] args) {
        new SimpleThread("cinema").start();
        new SimpleThread("pizzeria").start();
    }
}