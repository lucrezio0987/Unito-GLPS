import java.util.Objects;

public class Arch<E> {
  E sorgente;
  E destinazione;
  float distance;

  public Arch(E sorgente, E destinazione, float distance) {
    this.sorgente = sorgente;
    this.destinazione = destinazione;
    this.distance = distance;
  }

  public E getSorgente() {
    return sorgente;
  }

  public E getDestinazione() {
    return destinazione;
  }

  public float getDistance() {
    return distance;
  }

  public void setSorgente(E sorgente) {
    this.sorgente = sorgente;
  }

  public void setDestinazione(E destinazione) {
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
    return Objects.equals(sorgente, arch.sorgente) &&
           Objects.equals(destinazione, arch.destinazione) &&
           Objects.equals(distance, arch.distance);
  }

  @Override
  public int hashCode() {
    return Objects.hash(sorgente, destinazione, distance);
  }
}
