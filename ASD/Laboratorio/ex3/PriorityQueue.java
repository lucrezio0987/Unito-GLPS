import java.util.ArrayList;
import java.util.Comparator;

public class PriorityQueue<E> implements AbstractQueue<E> {
    Comparator<E> comparator = null;
    ArrayList<E> heap = new ArrayList<>();

    public PriorityQueue(Comparator<E> comparator) {
        this.heap = new ArrayList<>();
        this.comparator = comparator;
    }

    @Override
    public boolean empty() {
        return this.heap.isEmpty();
    }

    @Override
    public boolean push(E e) {
      heap.add(e);
      int i = heap.size() - 1;
      int p = i - 1;
      while (i > 0 && comparator.compare(heap.get(i), heap.get(p)) < 0) {
        swap(i, p);
        i = p;
        p = p - 1;
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
      if (this.heap.isEmpty()) return null;
      return this.heap.get(0);
    }

    @Override
    public void pop() {
        if (this.heap.isEmpty()) return;
        (this.heap).remove(0);
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < heap.size(); i++) {
            sb.append(heap.get(i));
            if (i < heap.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    public static void main(String[] args) {
        // Esempio di utilizzo
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Integer::compare);
        priorityQueue.push(5);
        priorityQueue.push(2);
        priorityQueue.push(10);
        priorityQueue.push(1);
        priorityQueue.push(3);

        System.out.println(priorityQueue); // Stampa: [1, 2, 10, 5]
    }
};