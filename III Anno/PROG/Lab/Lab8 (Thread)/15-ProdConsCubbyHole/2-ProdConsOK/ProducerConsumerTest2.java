public class ProducerConsumerTest2 {
    public static void main(String[] args) {
        CubbyHole c = new CubbyHole();
        Consumer c1 = new Consumer(c, 1);
        Consumer c2 = new Consumer(c, 2);
        Consumer c3 = new Consumer(c, 3);

        Producer p1 = new Producer(c, 10);
        Producer p2 = new Producer(c, 20);
        Producer p3 = new Producer(c, 30);

        p1.start();
        p2.start();
        p3.start();
        c1.start();
        c2.start();
        c3.start();
    }
}

/* NB: se i produttori non producono quanto necessario ai
consumatori il programma non termina perché restano dei consumatori
in attesa di dati */

class Producer extends Thread {
    private CubbyHole cubbyhole;
    private int number;

    public Producer(CubbyHole c, int number) {
        cubbyhole = c;
        this.number = number;
    }

    public void run() {
        //try {
        //   sleep(4000);
        //} catch (InterruptedException e) { }
        for (int i = 1; i < 5; i++) {
			int num = number*i;
            cubbyhole.put(num);
            System.out.println("Producer #" + this.number + " put: " + num);
            try {
                sleep((int)(Math.random() * 10));
            } catch (InterruptedException e) { }
        }
        System.out.println("Producer #" + this.number + " ha finito di produrre");
    }
}

class Consumer extends Thread {
    private CubbyHole cubbyhole;
    private int number;

    public Consumer(CubbyHole c, int number) {
        cubbyhole = c;
        this.number = number;
    }

    public void run() {
        int value = 0;
        for (int i = 1; i < 5; i++) {
            value = cubbyhole.get();
            System.out.println("Consumer #" + this.number + " got: " + value);
            try {
                sleep((int)(Math.random() * 1000));
            } catch (InterruptedException e) { }
        }
        System.out.println("Consumer #" + this.number + " ha finito di consumare");
    }
}

// sincronizzazione lato server
class CubbyHole {
    private int content = -1;
    private boolean available = false;

    public synchronized int get() {
        while (!available) {
            try {
                wait();
            } catch (InterruptedException e) { }
        }
        available = false;
        			/* fondamentale notifyAll(), anziché notify(), per prevenire deadlock */
        notifyAll();
        return content;
    }

    public synchronized void put(int value) {
        while (available) {
            try {
                wait();
            } catch (InterruptedException e) { }
        }
        content = value;
        available = true;
        notifyAll();
    }
}
