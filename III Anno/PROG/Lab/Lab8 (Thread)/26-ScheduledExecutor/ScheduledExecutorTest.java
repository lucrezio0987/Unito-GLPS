import java.util.concurrent.*;
import java.util.*;

class Computazione1 implements Callable<Integer> {

    private int num;

    public Computazione1(int n) {
        num = n;
    }

    // call() deve restituire un Object
    public Integer call() {
        System.out.println("INIZIO computazione ");
        try {
            Thread.sleep((int) Math.round(Math.random() * 4000));
        } catch (InterruptedException e) {
            System.out.println("Interruzione thread");
        }
        System.out.println("FINE computazione");
        return new Integer(2 * num);
    }
}

class MyTask implements Runnable {

    private String name;

    public MyTask(String str) {
        name = str;
    }

    public void run() {
        System.out.println(name + " " + Thread.currentThread().getName());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            return;
        }
        System.out.println("DONE! " + name + " " + Thread.currentThread().getName());
    }
}

// schedule (Runnable task, long delay, TimeUnit timeunit)
public class ScheduledExecutorTest {

    public static void main(String[] args) {
        // esempio di scheduling di task con ritardo
        System.out.println("Lancio un task con ritardo");
        ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);
        ScheduledFuture<Integer> task = exec.schedule(new Computazione1(2),
                4,
                TimeUnit.SECONDS
        );
        try {
            System.out.println("Risultato = " + task.get());
            System.out.println();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // esempio di scheduling di task ciclico con ritardo iniziale
        // scheduleAtFixedRate (Runnable, long initialDelay, long period, TimeUnit timeunit)
        System.out.println("Ora lancio un task ciclico ma lo lascio andare avanti solo per pochi secondi");
        exec.scheduleAtFixedRate(new MyTask("TASK"),
                2,
                3,
                TimeUnit.SECONDS
        );
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        };
        exec.shutdown();
    }
}
