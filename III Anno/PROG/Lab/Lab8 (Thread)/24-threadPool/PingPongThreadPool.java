import java.util.concurrent.*;
import java.util.*;

class PingPong implements Runnable {

    private int delay;
    private String name;

    public PingPong(String str, int d) {
        delay = d;
        name = str;
    }

    public void run() {
        for (int i = 1; i < 3; i++) {
            System.out.println(name + " " + Thread.currentThread().getName());
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                return;
            }
        }
        System.out.println("DONE! " + name + " " + Thread.currentThread().getName());
    }
}

public class PingPongThreadPool {

    private static final int NUM_THREAD = 5;

    public static void main(String[] args) {
        ExecutorService exec = Executors.newFixedThreadPool(NUM_THREAD);
        for (int i = 0; i < 10; i++) {
            Runnable task = new PingPong("PING" + i, 200);
            exec.execute(task);
        }
        exec.shutdown();
        try {
            exec.awaitTermination(5, TimeUnit.SECONDS); //Blocks until all tasks
                // have completed execution after a shutdown request,
                // or the timeout occurs, or the current thread is interrupted, whichever happens first.
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}