public class ProducerConsumerTest {
    public static void main(String[] args) {
        CubbyHole c = new CubbyHole();
        Producer p1 = new Producer(c, 1);
        Consumer c1 = new Consumer(c, 1);

        p1.start();
        c1.start();

    }
}

class Producer extends Thread {
    private CubbyHole cubbyhole;
    private int number;

    public Producer(CubbyHole c, int number) {
        cubbyhole = c;
        this.number = number;
    }

    public void run() {
            try {
                sleep(3000);
            } catch (InterruptedException e) { }
        for (int i = 0; i < 10; i++) {
            try {
                sleep((int)(Math.random() * 2000));
            } catch (InterruptedException e) { }
            cubbyhole.put(i);
            System.out.println("Producer #" + this.number + " put: " + i);
            try {
                sleep((int)(Math.random() * 10));
            } catch (InterruptedException e) { }
        }
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
        for (int i = 0; i < 10; i++) {
            value = cubbyhole.get();
            System.out.println("Consumer #" + this.number + " got: " + value);
            try {
                sleep((int)(Math.random() * 1000));
            } catch (InterruptedException e) { }
        }
    }
}

class CubbyHole {
    private int contents = -1;

    public int get() {
        return contents;
    }

    public  void put(int value) {
        contents = value;
    }
}
