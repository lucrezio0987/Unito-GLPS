package model;

import java.util.ArrayList;

public class Book {
    private String titolo;
    private ArrayList<String> autori;

    public Book(String titolo, ArrayList<String> autori) {
        this.titolo = titolo;
        this.autori = autori;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public ArrayList<String> getAutori() {
        return autori;
    }

    public void setAutori(ArrayList<String> autori) {
        this.autori = autori;
    }

    @Override
    public String toString() {
        return "model.Book {" +
                "titolo='" + titolo + '\'' +
                ", autori=" + autori +
                '}';
    }
}
