package catering.businesslogic.shiftManagement;

import java.util.ArrayList;

public class Board {
    String event;
    ArrayList<Shift> shifts;

    public Board(String event, ArrayList<Shift> shifts) {
        this.event = event;
        this.shifts = shifts;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public ArrayList<Shift> showBoard(String event) {
        return shifts;
    }
}
