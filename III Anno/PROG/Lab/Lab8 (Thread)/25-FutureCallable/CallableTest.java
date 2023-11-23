import java.util.concurrent.*;
import java.util.*;

class Computazione implements Callable<Integer> {

    private int num;

    public Computazione(int n) {
        num = n;
    }

    // call() deve restituire un Object
    public Integer call() {
        System.out.println("INIZIO computazione " + num);
        try {
            Thread.sleep((int) Math.round(Math.random() * 10000));
        } catch (InterruptedException e) {
            System.out.println("Interruzione thread");
        }
        System.out.println("FINE computazione " + num + ": risultato = " + 2 * num);
        return new Integer(2 * num);
    }
}

public class CallableTest {

    private static int N = 10;
    private static int NUM_THREADS = 2;

    public static void main(String[] args) {

        System.out.println("inizio il main");
        int total = 0;
        ExecutorService exec = Executors.newFixedThreadPool(NUM_THREADS);
        //ExecutorService exec = Executors.newSingleThreadExecutor();
        // creo i task callable
        Vector<FutureTask<Integer>> tasks = new Vector<>();

        for (int i = 0; i < N; i++) {
            FutureTask<Integer> ft = new FutureTask<>(new Computazione(i));
            tasks.add(ft);
            exec.execute(ft);
        }
        try {
            for (int i = 0; i < tasks.size(); i++) {
                FutureTask<Integer> f = (FutureTask<Integer>) tasks.get(i);
                total = total + (f.get()).intValue();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("\nIl totale è: " + total);
        exec.shutdown();
    }
}