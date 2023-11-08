package Dao;

import java.math.BigDecimal;

public class Lezione {
    private int Corso_ID;
    private int Docente_ID;
    private int Utente_ID=-1;
    private int valutazione=0;
    private String stato;
    private BigDecimal prezzo;
    private String data;
    private String Ora;


    //object to get
    public Lezione( String data, String ora,String stato,int corso_id, int docente_id, int utente_id ,int valutazione,double prezzo) {
        this.Corso_ID = corso_id;
        this.Docente_ID = docente_id;
        this.Utente_ID = utente_id;
        this.stato = stato;
        this.data = data;
        this.Ora = ora;
        this.valutazione=valutazione;
        this.prezzo = BigDecimal.valueOf(prezzo);
    }

    //object to insert
    public Lezione( String data, String ora,String stato,int corso_id, int docente_id,double prezzo) {
        this.Corso_ID = corso_id;
        this.Docente_ID = docente_id;
        this.stato = stato;
        this.data = data;
        this.Ora = ora;
        this.prezzo = BigDecimal.valueOf(prezzo);

    }


    public String getOra() {
        return Ora;
    }

    public String getData() {
        return data;
    }

    public String getStato() {
        return stato;
    }

    public int getDocente_ID() {
        return Docente_ID;
    }

    public int getCorso_ID() {
        return Corso_ID;
    }

    public int getUtente_ID() {
        return Utente_ID;
    }

    @Override
    public String toString() {
        return "Lezione{" +
                "Corso_ID=" + Corso_ID +
                ", Docente_ID=" + Docente_ID +
                ", Utente_ID=" + Utente_ID +
                ", valutazione=" + valutazione +
                ", stato='" + stato + '\'' +
                ", prezzo=" + prezzo +
                ", data='" + data + '\'' +
                ", Ora='" + Ora + '\'' +
                '}';
    }

    public int getValutazione() {
        return valutazione;
    }

    public void setValutazione(int valutazione) {
        this.valutazione = valutazione;
    }

    public double getPrezzo() {
        return prezzo.doubleValue();
    }

    public void setPrezzo(BigDecimal prezzo) {
        this.prezzo = prezzo;
    }
}
