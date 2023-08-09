import java.util.Comparator;

public class Queue_obj<E>{
    E e;
    int heap_i;

    public Queue_obj(E elemento, int priorita) {
        this.e = elemento;
        this.heap_i = priorita;
    }

    public E getElemento() {
        return e;
    }

    public int getI() {
        return heap_i;
    }
}