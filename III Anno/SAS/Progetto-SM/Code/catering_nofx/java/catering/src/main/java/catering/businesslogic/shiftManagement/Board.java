package catering.businesslogic.shiftManagement;

import java.util.ArrayList;

public class Board {
    String name;
    String event;
    ArrayList<Shift> shifts;

    // Constructor
    public Board(String name, String event, ArrayList<Shift> shifts) {
        this.name = name;
        this.event = event;
        this.shifts = shifts;
    }

    // Getters and Setters
    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public ArrayList<Shift> getShifts() {return shifts;}
    public void setShifts(ArrayList<Shift> shifts) {this.shifts = shifts;}

    // method to show the board
    public ArrayList<Shift> showBoard(String event) {
        return shifts;
    }
}
