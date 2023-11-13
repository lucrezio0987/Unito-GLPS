public class RaceTest0 {

    private final static int NUMRUNNERS = 2;

/* Prova di modifica di priority dei thread
*/

    public static void main(String[] args) {
         SelfishRunner[] runners = new SelfishRunner[NUMRUNNERS];

         for (int i = 0; i < NUMRUNNERS; i++) {
             runners[i] = new SelfishRunner(i);
             //runners[i].setPriority(5*(i+1));
         }

		 runners[0].setPriority(Thread.MIN_PRIORITY);
		 runners[1].setPriority(Thread.MAX_PRIORITY);


         for (int i = 0; i < NUMRUNNERS; i++)
            runners[i].start();
    }
}

class SelfishRunner extends Thread {

    private int tick = 1;
    private int num;

    public SelfishRunner(int num) {
        this.num = num;
    }

    public void run() {
        while (tick < 20) {
            tick++;
            System.out.println("Thread #" + num + ", tick = " + tick);
        }
    }
}
