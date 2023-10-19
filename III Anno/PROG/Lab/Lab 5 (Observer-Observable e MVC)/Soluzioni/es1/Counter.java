package es1;

import java.util.Observable;

public class Counter extends Observable {
    private int c;

    public Counter() {
        this.c = 0;
    }

    public void start() {
        for(int i = 0; i < 50; i++) {
            this.c++;
            if (c % 5 == 0) {
                /* Notifica osservatori ogni volta che c è multiplo di 5 */
                setChanged();
                notifyObservers();
            }
        }
    }

    public int getVal() {
        return this.c;
    }
}
