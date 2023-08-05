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
      heapifyUp(heap.size() - 1);
      return true;
      /*
      int i = (heap.size()) / 2;
      heap.add(i, e);
      
      minHeap(i);
      return true;*/
    }
    /*
    private void minHeap(int i) {
      while (i > 0 && i <= heap.size() - 2) {
        if (comparator.compare(heap.get(i), heap.get(i - 1)) < 0) {
          swap(i, i - 1);
          i = i - 1;
        } else if (comparator.compare(heap.get(i), heap.get(i + 1)) > 0) {
          swap(i, i + 1);
          i = i + 1;
        } else break;
      }
    }*/

    private int parentIndex(int index) {
      return (index - 1) / 2;
    }
  
    private int leftChildIndex(int index) {
        return 2 * index + 1;
    }
  
    private int rightChildIndex(int index) {
        return 2 * index + 2;
    }

    private void fixHeap(int index) {
      E e = heap.get(index);
      if (index == 0 || comparator.compare(e, heap.get(parentIndex(index))) >= 0) 
        heapifyDown(index);
      else 
        heapifyUp(index);
    }

    private void heapifyUp(int index) {
      while (index > 0) {
        if (comparator.compare(heap.get(parentIndex(index)), heap.get(index)) <= 0) break;
        swap(parentIndex(index), index);
        index = parentIndex(index);
      }
    }

    private void heapifyDown(int index) {
      int left = leftChildIndex(index);
      int right = rightChildIndex(index);
      while (left < heap.size()) {
        int smallestChildIndex = left;

        if (right < heap.size() && comparator.compare(heap.get(right), heap.get(left)) < 0)
          smallestChildIndex = right;
        
        if (comparator.compare(heap.get(index), heap.get(smallestChildIndex)) <= 0)
          break;

        swap(index, smallestChildIndex);
        index = smallestChildIndex;
        left = leftChildIndex(index);
        right = rightChildIndex(index);
      }
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
        heapifyDown(0);
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
        priorityQueue.pop();
        System.out.println(priorityQueue); // Stampa: [1, 2, 10, 5]
        priorityQueue.pop();
        System.out.println(priorityQueue); // Stampa: [1, 2, 10, 5]
    }
};