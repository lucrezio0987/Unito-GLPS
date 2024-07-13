package catering.businesslogic.KitchenJobManagement;

public class Duty {
    private String name;
    private String description;
    private int difficult;
    private int importance;
    private int time;
    private String quantity;
    private String portions;

    // Constructor
    public Duty(String name, String description, int difficult, int importance, int time, String quantity, String portions) {
        this.name = name;
        this.description = description;
        this.difficult = difficult;
        this.importance = importance;
        this.time = time;
        this.quantity = quantity;
        this.portions = portions;
    }

    // Getters and Setters
    public String getName() {
        return name;
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

    public String getPortions() {
        return portions;
    }

    public void setPortions(String portions) {
        this.portions = portions;
    }

    // Create method
    public void create(String name, String description, int time, String quantity, String portions) {

    }

}
