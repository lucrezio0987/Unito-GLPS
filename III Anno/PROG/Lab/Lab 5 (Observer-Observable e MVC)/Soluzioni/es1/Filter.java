package es1;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class Filter extends Observable implements Observer {
    private final List<Integer> list;

    public Filter() {
        this.list = new ArrayList<>();
    }

    private void filters(int c) {
        this.list.add(c);
        if (this.list.size() % 2 == 0) {
            System.out.println("List size: " + this.list.size());

            /* Notifica osservatori ogni volta che la lunghezza della lista è pari*/
            setChanged();
            notifyObservers();
        }
    }

    public List<Integer> getList() {
        return list;
    }

    /*
     * Override del metodo update dell'Interface Observer.
     */
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Counter) {
            Counter c = (Counter) o;
            int v = c.getVal();
            filters(v);
        }
    }
}
