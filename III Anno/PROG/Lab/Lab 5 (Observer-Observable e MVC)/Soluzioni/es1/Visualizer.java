package es1;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class Visualizer implements Observer {

    /*
     * Override del metodo update dell'Interface Observer.
     */
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Filter) {
            Filter f = (Filter) o;
            List<Integer> list = f.getList();
            visualize(list);
        }
    }

    private void visualize(List<Integer> list) {
        list.forEach(System.out::println);
        System.out.println();
    }
}
