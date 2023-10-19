import org.w3c.dom.ranges.Range;

import java.util.random.RandomGenerator;

public class Main {
    public static void main(String[] args) {
        int i = RandomGenerator.getDefault().nextInt(0, 10);
        System.out.println(i);

        Viewe v = new Viewe();
        Model m = new Model();
        Controller ct = new Controller(m);

        v.setListener(ct);
        m.addObserver(v);
        m.choseProverbio();
        v.start();

    }
}