package org.prog3.lab.week3.es1;
import java.util.List;

/**
 * Implementa una coda FIFO
 * @param <T> tipo degli elementi che possono essere inseriti in Queue
 */
class Queue<T> {
    private List<T> c;  // Si noti come il tipo generico per la lista T
                        // è vincolato a essere lo stesso usato dalla Queue

    /**
     * Costruttore della classe.
     *
     * @param c     la lista usata dalla coda. L'applicazione che fa
     *              uso della coda puo' decidere l'implementazione
     *              specifica dell'interfaccia List<T> da usare
     */
    public Queue(List<T> c) {
        this.c = c;
    }

    /**
     * Rimuove il primo elemento della coda e ritorna tale elemento.
     * Se la coda e' vuota, il metodo ritorna null.
     *
     * @return      l'elemento rimosso dalla coda, oppure null
     *              se la coda e' vuota.
     */
    public T dequeue() {
        T ris = null;
        if (c.size()>0) {
            ris = c.remove(0);
        }
        return ris;
    }

    /**
     * Inserisce un elemento di tipo T in fondo alla coda.
     *
     * @param el    l'elemento da inserire nella coda. */
    public void enqueue(T el) {
        c.add(el);
    }

    /**
     * Indica se la coda e' vuota oppure no.
     *
     * @return      true se la coda e' vuota, false altrimenti.
     */
    public boolean empty() {
        return c.size() == 0;
    }

    /**
     * Stampa il contenuto della coda.
     */
    public void print() {
        for (T t : c) {
            System.out.print(t + ", ");
        }
        System.out.println();
    }
}