public interface AbstractQueue<E> {
    public boolean empty(); // controlla se la coda è vuota
    
    public boolean push(E e); // aggiunge un elemento alla coda
    
    public E top(); // accede all'elemento in cima alla coda
    
    public void pop(); // rimuove l'elemento in cima alla coda
};

