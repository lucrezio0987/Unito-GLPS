package catering.businesslogic.KitchenJobManagement;

import java.util.ArrayList;

public class Duty {
    private String title;
    private String description;
    private int difficult;
    private int importance;
    private int time;
    private String quantity;
    private int portions;
    private ArrayList<Preparation> preparations;

    // Constructor
    public Duty(String name, String description, int difficult, int importance, int time, String quantity, int portions, ArrayList<Preparation> preparations) {
        this.title = name;
        this.description = description;
        this.difficult = difficult;
        this.importance = importance;
        this.time = time;
        this.quantity = quantity;
        this.portions = portions;
        this.preparations = preparations;
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public void setImportance(int importance) {
        this.importance = importance;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
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
                "name='" + title + '\'' +
                ", description='" + description + '\'' +
                ", difficult=" + difficult +
                ", importance=" + importance +
                ", time=" + time +
                ", quantity='" + quantity + '\'' +
                ", portions='" + portions + '\'' +
                '}';
    }
}
