package catering.businesslogic.tableManagement;

import catering.persistence.PersistenceManager;
import catering.persistence.ResultHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public class Turno {
    private int id;
    private Date date;
    private Time time;
    private Double durata;
    private ArrayList<Cuoco> cuochi = new ArrayList<>();

    public Turno(Date date, Time time, Double durata) {
        this.date = date;
        this.time = time;
        this.durata = durata;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public Double getDurata() {
        return durata;
    }

    public void setDurata(Double durata) {
        this.durata = durata;
    }

    public ArrayList<Cuoco> getCuochi() {
        return cuochi;
    }

    public void setCuochi(ArrayList<Cuoco> cuochi) {
        this.cuochi = cuochi;
    }

    // utility methods
    public ArrayList<Turno> getAllTurni(){
        ArrayList<Turno> allTurni = new ArrayList<>();
        String query = "SELECT * FROM Shifts";
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                while (rs.next()) {
                    Turno turno = new Turno(rs.getDate("date"), rs.getTime("time"), rs.getDouble("duration"));
                    turno.setId(rs.getInt("id"));
                    allTurni.add(turno);
                }
            }
        });
        return allTurni;
    }
}
