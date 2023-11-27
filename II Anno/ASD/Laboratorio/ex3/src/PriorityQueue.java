import java.util.ArrayList;
import java.util.Comparator;

public class PriorityQueue < E > implements AbstractQueue < E > {
  private Comparator < ?super E > comparator = null;
  private ArrayList < E > heap;

  public PriorityQueue(Comparator < ?super E > comparator) {
    this.heap = new ArrayList<>();
    this.comparator = comparator;
  }

  @Override
  public boolean empty() {
    return heap.isEmpty();
  }

  private int parent(int i) {
    return (i / 2);
  }
  private int child_l(int i) {
    return (2 * i);
  }
  private int child_r(int i) {
    return (2 * i) + 1;
  }

  @Override
  public boolean push(E e) {
    if (!contains(e)) {
      heap.add(e);
      int i = heap.size() - 1;

      while (i > 0 && comparator.compare(heap.get(i), heap.get(parent(i))) < 0) {
        swap(i, parent(i));
        i = parent(i);
      }
      return true;
    }
    return false;
  }

  @Override
  public E top() {
    if (empty()) return null;
    return heap.get(0);
  }

  @Override
  public void pop() {
    heap.set(0, heap.get(heap.size() - 1));
    heap.remove(heap.size() - 1);
    //if (heap.size() > 1)
    heapify(0);
  }

  private void swap(int i, int j) {
    E temp = heap.get(i);
    heap.set(i, heap.get(j));
    heap.set(j, temp);
  }

  @Override
  public boolean contains(E e) {
    return heap.contains(e);
  }

  @Override
  public boolean remove(E e) {
    int i;
    if ((i = heap.indexOf(e)) != -1) {
      heap.set(i, heap.get(heap.size() - 1));
      heap.remove(heap.size() - 1);
      //if (i < heap.size())
      heapify(i);
      return true;
    }
    return false;
  }

  private void heapify(int i) {
    int left = child_l(i);
    int right = child_r(i);
    int smallest = i;

    if (left < heap.size() && comparator.compare(heap.get(left), heap.get(smallest)) < 0) smallest = left;

    if (right < heap.size() && comparator.compare(heap.get(right), heap.get(smallest)) < 0) smallest = right;

    if (smallest != i) {
      swap(i, smallest);
      heapify(smallest);
    }
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("[");
    for (int i = 0; i < heap.size(); i++) {
      sb.append(heap.get(i));
      if (i < heap.size() - 1) sb.append(", ");
    }
    sb.append("]");
    return sb.toString();
  }
};
