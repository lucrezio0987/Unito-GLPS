import java.util.Objects;

public class Arch<E> {
  Node<E> sorgente;
  Node<E> destinazione;
  float distance;

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

  public Node<E> getSorgente() {
    return sorgente;
  }

  public Node<E> getDestinazione() {
    return destinazione;
  }

  public float getDistance() {
    return distance;
  }

  public void setSorgente(Node<E>sorgente) {
    this.sorgente = sorgente;
  }

  public void setDestinazione(Node<E> destinazione) {
    this.destinazione = destinazione;
  }

  public void setDistance(float distance) {
    this.distance = distance;
  }

  @Override
  public boolean equals(Object obj) {
      if (this == obj) {
          return true;
      }
      if (obj == null || getClass() != obj.getClass()) {
          return false;
      }
      Arch<?> arch = (Arch<?>) obj;
      return Float.compare(arch.distance, distance) == 0 &&
             Objects.equals(sorgente, arch.sorgente) &&
             Objects.equals(destinazione, arch.destinazione);
  }   

  @Override
  public int hashCode() {
    return Objects.hash(sorgente, destinazione, distance);
  }
}
