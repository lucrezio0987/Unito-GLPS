package catering.businesslogic.KitchenJobManagement;

import catering.businesslogic.recipe.Recipe;
import catering.persistence.PersistenceManager;
import catering.persistence.ResultHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Duty {
    private int id = 0;
    private String name;
    private String description;
    private int difficult;
    private int importance;
    private int time;
    private int quantity;
    private int portions;
    private ArrayList<Preparation> preparations;

    // Constructor
    public Duty(String name, String description, int difficult, int importance, int time, int quantity, int portions, ArrayList<Preparation> preparations) {
        this.name = name;
        this.description = description;
        this.difficult = difficult;
        this.importance = importance;
        this.time = time;
        this.quantity = quantity;
        this.portions = portions;
        this.preparations = preparations;
    }

    public Duty(String name, String description, int difficult, int importance, int time, int quantity, int portions) {
        this.name = name;
        this.description = description;
        this.difficult = difficult;
        this.importance = importance;
        this.time = time;
        this.quantity = quantity;
        this.portions = portions;
    }

    public Duty(String name){
        this.name = name;
    }

    public Duty(){}


    // Getters and Setters


    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDifficult() {
        return difficult;
    }

    public void setDifficult(int difficult) {
        this.difficult = difficult;
    }

    public int getImportance() {
        return importance;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setImportance(int importance) {
        this.importance = importance;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPortions() {
        return portions;
    }

    public void setPortions(int portions) {
        this.portions = portions;
    }

    public ArrayList<Preparation> getPreparations() {
        return preparations;
    }

    public void setPreparations(ArrayList<Preparation> preparations) {
        this.preparations = preparations;
    }

    // toString method
    @Override
    public String toString() {
        return "Duty{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", difficult=" + difficult +
                ", importance=" + importance +
                ", time=" + time +
                ", quantity='" + quantity + '\'' +
                ", portions='" + portions + '\'' +
                '}';
    }

    public int loadIdByName(String name) {
        String query = "SELECT id FROM duties WHERE name = " + name;
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                setId(rs.getInt("id"));
            }
        });
        return id;
    }
}
