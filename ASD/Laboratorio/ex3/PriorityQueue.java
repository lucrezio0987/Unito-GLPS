import java.util.ArrayList;
import java.util.Comparator;

public class PriorityQueue<E> implements AbstractQueue<E> {
    Comparator<E> comparator = null;
    ArrayList<E> heap = new ArrayList<>();

    int front;      // front punta all'elemento front nella queue
    int rear;       // back punta all'ultimo elemento nella queue
    int count;      // dimensione attuale della queue

    public PriorityQueue(Comparator<E> comparator) {
        this.queue = new ArrayList<>();
        this.comparator = comparator;
    }

    @Override
    public boolean empty() {
        return (this.count) == 0;
    }

    @Override
    public boolean push(E e) {
        heap.add(e);
        int i = heap.size() - 1;
        while (i > 0) {
            int p = (i - 1) / 2; //parent
            if (comparator(heap.get(p), heap.get(i)) <= 0) {
                break;
            }
            swap(i, p);
            i = p;
        }
        return true;
    }

    private void swap(int i, int j) {
      E temp = heap.get(i);
      heap.set(i, heap.get(j));
      heap.set(j, temp);
    }

    @Override
    public E top() {
      return this.heap.get(rear);
    }

    @Override
    public void pop() {
      if(rear > -1) {
        this.heap.remove(rear);
        this.rear--;
        this.count--;
      }
      // todo
    }

    @Override
    public boolean contains(E e) {
      return true;
      // may be removed (PER EX4)
    }

    @Override
    public boolean remove(E e) {
      return true;

      // may be removed (PER EX4)
    }
};