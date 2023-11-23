package v1WaitNotify;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args) {
        ConditionManager manager = new ConditionManager(9);
        Semaphore sem = new Semaphore(1);

        int[] noWait = {-1};
        ArrayList<Thread> threadList = new ArrayList<>();
        // NB: si poteva fare con i Runnable ma ho usato direttamente
        // Thread per semplificare la lettura del codice
        Thread a0 = new Activity(0, 2, noWait, manager);
        int[] constraintsFor123 = {0};
        threadList.add(a0);

        Thread a1 = new Activity(1, 8, constraintsFor123, manager);
        Thread a2 = new Activity(2, 3, constraintsFor123, manager);
        Thread a3 = new Activity(3, 6, constraintsFor123, manager);
        threadList.add(a1);
        threadList.add(a2);
        threadList.add(a3);

        int[] constraintsFor4 = {1, 2, 3};
        Thread a4 = new Activity(4, 4, constraintsFor4, manager);
        threadList.add(a4);

        int[] constraintsFor5 = new int[1];
        constraintsFor5[0] = 4;
        for(int i=1; i<=5; i++) {
            Activity a5 = new Activity(5,2, constraintsFor5, manager);
            constraintsFor5[0] = 5;
            a5.setSemaphore(sem);
            threadList.add(a5);
        }

        for(Thread thread: threadList) {
            thread.start();
        }

    }
}