public class Arch {
  String sorgente;
  String destinazione;
  float distance;

  public Arch(String sorgente, String destinazione, float distance) {
    this.sorgente = sorgente;
    this.destinazione = destinazione;
    this.distance = distance;
  }

  public String getSorgente() {
    return sorgente;
  }

  public String getDestinazione() {
    return destinazione;
  }

  public float getDistance() {
    return distance;
  }

  public void setSorgente(String sorgente) {
    this.sorgente = sorgente;
  }

  public void setDestinazione(String destinazione) {
    this.destinazione = destinazione;
  }

  public void setDistance(float distance) {
    this.distance = distance;
  }
}
