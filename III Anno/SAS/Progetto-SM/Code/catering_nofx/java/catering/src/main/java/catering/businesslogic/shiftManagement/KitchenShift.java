package catering.businesslogic.shiftManagement;

import catering.businesslogic.KitchenJobManagement.Job;

import java.sql.Array;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public class KitchenShift extends Shift{
    String place;
    public KitchenShift(String description, Date date, Time time, Time duration, ArrayList<Cook> cooks, String place) {
        super(description, date, time, duration, cooks);
        this.place = place;
    }

    public KitchenShift(String description, Date date, Time time, Time duration, ArrayList<Cook> cooks) {
        super(description, date, time, duration, cooks);
    }

    public String getPlace() {return place;}

    public void setPlace(String place) {this.place = place;}

    public boolean isCookAssigned(Cook c) {
         return this.getCooks().contains(c);
    }
}
