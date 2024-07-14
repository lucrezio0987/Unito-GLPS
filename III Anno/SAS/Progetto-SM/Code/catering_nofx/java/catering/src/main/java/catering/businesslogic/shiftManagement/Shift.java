package catering.businesslogic.shiftManagement;

import java.sql.Time;
import java.util.Date;

public class Shift {
    private String description;
    private Date date;
    private Time time;
    private Time duration;
    private Date expire;
    private boolean lock;

    public Shift(String description, Date date, Time time, Time duration) {
        this.description = description;
        this.date = date;
        this.time = time;
        this.duration = duration;
    }
}
