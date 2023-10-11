import java.util.Objects;
import javax.print.DocFlavor.STRING;
import java.util.Comparator;

public class Node<E> implements Comparable<Node<E>> {
  private E val;

  //* METODI BASE
  public Node(E val)        { this.val = val;  }
  public E getVal()         { return this.val; }
  public void setVal(E val) { this.val = val;  }

  //* OVERRIDE

  @Override
  public String toString(){ 
    return new StringBuilder().append("Node: ")
                              .append(this.getVal())
                              .toString(); 
  }

  @Override
  public int hashCode() { return Objects.hash(val); }

  @Override
  public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      return Objects.equals(val, ((Node<?>) o).val);
  }

  @Override
  public int compareTo(Node<E> other) {
    if (this == other) return 0;
    if (other == null) return 1;    // Consider non-null objects to be greater
    return ((Comparable<E>) this.val).compareTo((E) other.getVal());
  }

  /*
       if (val instanceof Comparable<?> && other.getVal() instanceof Comparable<?>) {
        Comparable<E> thisComparable = (Comparable<E>) val;
        Comparable<E> otherComparable = (Comparable<E>) other.getVal();
        return thisComparable.compareTo((E) otherComparable);
    } else {
        // Se i valori non sono comparabili, non è possibile effettuare un confronto diretto.
        // Puoi decidere come gestire questo caso, ad esempio, lanciando un'eccezione o restituendo un valore predefinito.
        throw new IllegalArgumentException("Impossibile confrontare nodi con valori non comparabili");
    }
   */

}