package ex3;

public interface  AbstractQueue<E> {
    public boolean empty(); // controlla se la coda è vuota
    public boolean push(E e); // aggiunge un elemento alla coda
    public boolean contains(E e); // controlla se un elemento è in coda
    public E top(); // accede all'elemento in cima alla coda
    public void pop(); // rimuove l'elemento in cima alla coda
    public boolean remove (E e); // rimuove un elemento se presente in coda
}
