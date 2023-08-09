import java.util.Comparator;

public class Queue_obj<E> implements Comparable<Queue_obj<E>> {
    private E e;
    private int i;
    Comparator<? super E> comparator = null;

    public Queue_obj(E e, int i) {
        this.e = e;
        this.i = i;
    }

    public E getE() {
        return e;
    }

    public int getI() {
        return i;
    }

    @Override
    public int compareTo(Queue_obj o) {
        return this.comparator.comare(this.e, o.e);
    }

    public int compareElement(E e) {
        return this.comparator.comare(this.e, e);
    }
}