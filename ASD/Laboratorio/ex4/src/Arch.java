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
}
