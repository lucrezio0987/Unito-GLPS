package catering.businesslogic.shiftManagement;

import java.sql.Array;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public class ServiceShift extends Shift{
    ArrayList<StaffMember> staff;

    // constructor
    public ServiceShift(String description, Date date, Time time, Time duration, ArrayList<Cook> cooks) {
        super(description, date, time, duration, cooks);
    }

    // getter and setter

    public ArrayList<StaffMember> getStaff() {
        return staff;
    }

    public void setStaff(ArrayList<StaffMember> staff) {
        this.staff = staff;
    }
}
