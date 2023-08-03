public class Queue_obj{
    int elemento;
    int priorita;

    public Queue_obj(E elemento, int priorita) {
        this.elemento = elemento;
        this.priorita = priorita;
    }

    public E getElemento() {
        return elemento;
    }

    public int getPriorita() {
        return priorita;
    }
}