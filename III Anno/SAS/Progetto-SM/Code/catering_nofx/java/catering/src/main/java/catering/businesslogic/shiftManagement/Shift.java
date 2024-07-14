package catering.businesslogic.shiftManagement;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public class Shift {
    private String description;
    private Date date;
    private Time time;
    private Time duration;
    private Date expire;
    private boolean lock;
    private ArrayList<Cook> cooks;

    public Shift(String description, Date date, Time time, Time duration, ArrayList<Cook> cooks) {
        this.description = description;
        this.date = date;
        this.time = time;
        this.duration = duration;
        this.cooks = cooks;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Time getDuration() {
        return duration;
    }

    public void setDuration(Time duration) {
        this.duration = duration;
    }

    public Date getExpire() {
        return expire;
    }

    public void setExpire(Date expire) {
        this.expire = expire;
    }

    public boolean isLock() {
        return lock;
    }

    public void setLock(boolean lock) {
        this.lock = lock;
    }

    public ArrayList<Cook> getCooks() {
        return cooks;
    }

    public void setCooks(ArrayList<Cook> cooks) {
        this.cooks = cooks;
    }
}
