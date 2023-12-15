import java.util.Objects;

public class Arch<E> {
  private Node<E> sorgente;
  private Node<E> destinazione;
  private float distance;

  //* COSTRUTTORI
  public Arch(E sorgente, E destinazione, float distance) {
    this.sorgente = new Node<E>(sorgente);
    this.destinazione = new Node<E>(destinazione);
    this.distance = distance;
  }
  public Arch(Node<E> sorgente, Node<E> destinazione, float distance) {
    this.sorgente = sorgente;
    this.destinazione = destinazione;
    this.distance = distance;
  }

  //* GET
  public Node<E> getSorgente()                      { return this.sorgente;      }
  public Node<E> getDestinazione()                  { return this.destinazione;  }
  public float   getDistance()                      { return this.distance;      }

  //* SET
  public void setSorgente(Node<E>sorgente)          { this.sorgente     = sorgente;     }
  public void setDestinazione(Node<E> destinazione) { this.destinazione = destinazione; }
  public void setDistance(float distance)           { this.distance     = distance;     }

  //* METODI
  public Arch<E> reveArch() { return new Arch<>(destinazione, sorgente, distance); }

  //* OVERRIDE

  @Override
  public int hashCode() { return Objects.hash(sorgente, destinazione, distance); }

  @Override
  public String toString() {
    return new StringBuilder().append("  Arch: ")
                              .append(this.getSorgente().getVal()).append(" -> ")
                              .append(this.getDestinazione().getVal()).append(", Distance: ")
                              .append(this.getDistance())
                              .toString();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)                                  return true;
    if (obj == null || getClass() != obj.getClass())  return false;
    
    return this.sorgente.equals(((Arch<?>) obj).getSorgente()) &&
           this.destinazione.equals(((Arch<?>) obj).getDestinazione())&&
           Float.compare(this.distance, ((Arch<?>) obj).getDistance()) == 0;
  }

}
