import java.util.ArrayList;
import java.util.TreeSet;
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
    ArrayList<E> heap;
    TreeSet<Queue_obj> sortered_Array;

    public PriorityQueue(Comparator<? super E> comparator) {
        this.heap = new ArrayList<>();
        this.sortered_Array = new TreeSet<>();
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
      if (contains(e) == true)
        return false; 

      heap.add(e);
      int i = heap.size() - 1;
      int p_i = parent(i); 

      while (i > 0 && comparator.compare(heap.get(i), heap.get(p_i)) < 0) {
        swap(i, p_i);
        i = p_i;
        p_i = parent(p_i);
      }

      sortered_Array.add(new Queue_obj<>(e, i, comparator));

      return true;
    }

    @Override
    public E top() {
      if (this.heap.isEmpty()) return null;
      return this.heap.get(0);
    }

    public void remove_i(int i) {
        int c_l = child_l(i);
        int c_r = child_r(i);
        int min_c;

        if (heap.isEmpty()) return;
        swap(i, heap.size() - 1);
        heap.remove(heap.size() - 1);

        while (c_l <= heap.size() - 1) {
          min_c = c_l;
  
          if (c_r <= heap.size() - 1 && comparator.compare(heap.get(c_r), heap.get(c_l)) < 0) {
              min_c = c_r;
          }

          if (comparator.compare(heap.get(i), heap.get(min_c)) > 0) {
            swap(i, min_c);
            i = min_c;
            c_l = child_l(i);
            c_r = child_r(i);
          } else break;
        }
    }

    @Override
    public void pop() {
        remove_i(0);
    }

    private void swap(int i, int j) {
      E temp = heap.get(i);
      heap.set(i, heap.get(j));
      heap.set(j, temp);
    }

    @Override
    public boolean contains(E e) {
      return (contains_element(e) != -1);
    }

    public int contains_i(E e) {
      int i = 0;
      while (i <= heap.size() - 1) {
        if (comparator.compare(heap.get(i), e) == 0)
          return i;
        i++;
      }
      return -1;
    }

    public int contains_element(E e) {
      for (Queue_obj<E> obj : sortered_Array) 
        if (compareElement(e) == 0) 
          return obj.getI();
      return -1;
    }

    @Override
    public boolean remove(E e) {
      int i = contains_element(e);
      if (i == -1)
        return false;
      else {
        remove_i(i);
        sortered_Array.remove(new Queue_obj<>(e, i, comparator) );
      }
      return true;
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
        
        sb.append("  [ ");
        for (Queue_obj obj : sortered_Array) {
            sb.append(obj.getElemento());
            sb.append(" ");
        }
        sb.append("]");
        return sb.toString();
    }
};