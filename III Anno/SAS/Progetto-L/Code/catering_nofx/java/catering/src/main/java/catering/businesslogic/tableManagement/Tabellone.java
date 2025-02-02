package catering.businesslogic.tableManagement;

import java.util.ArrayList;

public class Tabellone {
    String name;
    String event;
    ArrayList<Turno> turni;

    // Constructor
    public Tabellone(String name, String event, ArrayList<Turno> turni) {
        this.name = name;
        this.event = event;
        this.turni = turni;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public ArrayList<Turno> getTurni() {
        return turni;
    }

    public void setTurni(ArrayList<Turno> turni) {
        this.turni = turni;
    }

    public ArrayList<Turno> mostraTabellone() {
        return turni;
    }
}