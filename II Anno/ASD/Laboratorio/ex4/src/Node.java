import java.util.Objects;
import java.util.Comparator;

public class Node<E> implements Comparable<Node<E>> {
  private E val;

  public Node(E val) {
      this.val = val;
  }

  public E getVal() {
      return this.val;
  }

  @Override
  public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Node<?> node = (Node<?>) o;
      return Objects.equals(val, node.val);
  }

  @Override
  public int hashCode() {
      return Objects.hash(val);
  }

  @Override
  public int compareTo(Node<E> other) {
      if (this == other) return 0;
      if (other == null) return 1;  // Consider non-null objects to be greater

      // Assuming that E is Comparable
      if (val instanceof Comparable && other.val instanceof Comparable) {
          Comparable<E> thisComparable = (Comparable<E>) val;
          return thisComparable.compareTo(other.val);
      }

      throw new IllegalArgumentException("Type E must implement Comparable to use compareTo");
  }
}
