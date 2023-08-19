import java.util.Objects;

public class Node<E> {
  E val;

  public Node(E val) {
      this.val = val;
  }

  public E getVal() {
      return this.val;
  }


  @Override
  public int hashCode() {
      return Objects.hash(val);
  }
}

