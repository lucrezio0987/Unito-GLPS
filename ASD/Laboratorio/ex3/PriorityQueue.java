import java.util.ArrayList;
import java.util.Comparator;

//public interface  AbstractQueue<E> {
//    public boolean empty(); // controlla se la coda è vuota
//    public boolean push(E e); // aggiunge un elemento alla coda
//    public boolean contains(E e); // controlla se un elemento è in coda
//    public E top(); // accede all'elemento in cima alla coda
//    public void pop(); // rimuove l'elemento in cima alla coda
//    public boolean remove (E e); // rimuove un elemento se presente in coda
//}

public class PriorityQueue<E> implements AbstractQueue<E> {
    Comparator<? super E> comparator = null;
    ArrayList<E> heap = new ArrayList<>();

    public PriorityQueue(Comparator<? super E> comparator) {
        this.heap = new ArrayList<>();
        this.comparator = comparator;
    }

    @Override
    public boolean empty() {
        return this.heap.isEmpty();
    }

    public int parent(int i) {
      return (i / 2);
    }
    public int child_l(int i) {
      return (2 * i);
    }
    public int child_r(int i) {
      return (2 * i) + 1;
    }

    @Override
    public boolean push(E e) {
      heap.add(e);
      int i = heap.size() - 1;
      int p_i = parent(i); 
      

      while (i > 0 && comparator.compare(heap.get(i), heap.get(p_i)) < 0) {
        swap(i, p_i);
        i = p_i;
        p_i = parent(p_i);
      }

      return true;
    }

    @Override
    public E top() {
      if (this.heap.isEmpty()) return null;
      return this.heap.get(0);
    }

    @Override
    public void pop() {
        int i = 0;
        int c_l = child_l(i);
        int c_r = child_r(i);

        if (this.heap.isEmpty()) return;
        swap(heap.size() - 1, 0);
        heap.remove(heap.size() - 1);
        
        while ((heap.size() - 1 > 1) && (c_l <= (heap.size() - 1)) && ((comparator.compare(heap.get(i), heap.get(c_l)) > 0) || (comparator.compare(heap.get(i), heap.get(c_r)) > 0))) {
          if (comparator.compare(heap.get(i), heap.get(c_l)) > 0) {
            swap(i, c_l);
            i = c_l;
            c_l = child_l(c_l);
            c_r = child_r(i);
            System.out.println("i " + i + " c_l " + c_l + " c_r " + c_r);
            if (c_r > heap.size() - 1 && c_l <= heap.size() - 1) {
              if (comparator.compare(heap.get(i), heap.get(c_l)) > 0)
                swap(i, c_l);
                break;
            } 
          } else if (comparator.compare(heap.get(i), heap.get(c_r)) > 0) {
            swap(i, c_r);
            i = c_r;
            c_r = child_r(c_r);
            c_l = child_l(i);
            System.out.println("i " + i + " c_l " + c_l + " c_r " + c_r);
            if (c_r > heap.size() - 1 && c_l <= heap.size() - 1) {
              if (comparator.compare(heap.get(i), heap.get(c_l)) > 0)
                swap(i, c_l);
                break;
            } 
          }
        }
        
    }

    private void swap(int i, int j) {
      E temp = heap.get(i);
      heap.set(i, heap.get(j));
      heap.set(j, temp);
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
};