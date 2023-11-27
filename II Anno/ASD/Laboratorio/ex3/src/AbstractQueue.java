/**
 * Generic interface for a PriorityQueue data structure.
 *
 * @param E the type of elements stored in the queue
 */
public interface AbstractQueue<E> {

    /**
     * Checks whether the queue is empty.
     *
     * @return true if the queue is empty, false otherwise
     */
    public boolean empty();

    /**
     * Adds an element to the end of the queue.
     *
     * @param e the element to be added to the queue
     * @return true if the element was successfully added, false otherwise
     */
    public boolean push(E e);

    /**
     * Checks if the specified element is present in the queue.
     *
     * @param e the element to check for presence in the queue
     * @return true if the element is present, false otherwise
     */
    public boolean contains(E e);

    /**
     * Retrieves the element at the front of the queue without removing it.
     *
     * @return the element at the front of the queue
     */
    public E top();

    /**
     * Removes the element at the front of the queue.
     */
    public void pop();

    /**
     * Removes the specified element from the queue, if present.
     *
     * @param e the element to be removed from the queue
     * @return true if the element was removed, false otherwise
     */
    public boolean remove(E e);

    /**
     * Returns a string representation of the queue.
     *
     * @return a string representation of the queue
     */
    public String toString();
}
