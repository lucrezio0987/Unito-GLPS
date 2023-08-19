import java.util.Objects;

public class Node<E extends Comparable<E>> implements Comparable<Node<E>> {
  E val;

  public Node(E val) {
    this.val = val;
  }

  public E getVal() {
    return this.val;
  }

  public void setVal(E val) {
    this.val = val;
  }

  @Override
  public int hashCode() {
    int hash = 3;
    hash = 83 * hash + Objects.hashCode(this.val);
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) 
      return true;
    if (obj == null || getClass() != obj.getClass()) 
      return false;
    
    Node<?> otherNode = (Node<?>) obj;
    return val.equals(otherNode.val);
  }

  @Override
  public int compareTo(Node<E> otherNode) {
    return val.compareTo(otherNode.getVal());
  }
}
