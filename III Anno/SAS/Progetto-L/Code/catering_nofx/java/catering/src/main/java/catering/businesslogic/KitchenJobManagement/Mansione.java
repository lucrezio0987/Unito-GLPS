package catering.businesslogic.KitchenJobManagement;

import catering.businesslogic.recipe.Preparazione;
import catering.businesslogic.tableManagement.*;

public abstract class Mansione {
    private int id;
    private String nome;
    private String descrizione;
    private double tempo;
    private double quantita;

    public Mansione(String nome, String descrizione, double tempo, double quantita) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.tempo = tempo;
        this.quantita = quantita;
    }

    public Mansione(String title) {
        this.nome = title;
    }

    public Mansione() {

    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public double getTempo() {
        return tempo;
    }
    public void setTempo(double tempo) {
        this.tempo = tempo;
    }

    public double getQuantita() {
        return quantita;
    }
    public void setQuantita(double quantita) {
        this.quantita = quantita;
    }

    public int getId() {
        return id;
    };
    public void setId(int id) {
        this.id = id;
    }
}
