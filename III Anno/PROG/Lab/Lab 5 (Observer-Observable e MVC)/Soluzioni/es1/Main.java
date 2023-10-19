package es1;

public class Main {

    public static void main(String[] args) {

        Visualizer v = new Visualizer();
        Filter f = new Filter();
        Counter c = new Counter();

        c.addObserver(f);
        f.addObserver(v);
        c.start();
    }
}
