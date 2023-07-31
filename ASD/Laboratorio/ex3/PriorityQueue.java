import java.util.Comparator;

public class PriorityQueue<E> implements AbstractQueue<E> {
    private PriorityQueue<E> queue;
    public int count = 0;

    public PriorityQueue(Comparator<E> comparator) {
        queue = new PriorityQueue<>(comparator);
    }
    @Override
    public boolean empty() {
        return queue.count == 0;
    }
    @Override
    public boolean push(E e) {
      // todo
    }
    @Override
    public E top() {
      // todo
    }
    @Override
    public void pop() {
      // todo
    }
    @Override
    public boolean contains(E e) {
      // may be removed
    }
    @Override
    public boolean remove(E e) {
      // may be removed
    }
};
 