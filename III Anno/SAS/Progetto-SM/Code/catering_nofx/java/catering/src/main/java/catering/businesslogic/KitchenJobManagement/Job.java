package catering.businesslogic.KitchenJobManagement;

import catering.businesslogic.shiftManagement.Cook;
import catering.businesslogic.shiftManagement.KitchenShift;
import catering.businesslogic.shiftManagement.Shift;

import java.util.ArrayList;

public class Job {
    private String title;
    private int time;
    private int portions;
    private boolean prepare;
    private boolean completed;
    private KitchenShift shift;
    private ArrayList<Cook> cooksAssigned;
    private Duty duty;

    public Job(String title, int portions, int time, boolean prepare, boolean completed, Duty duty) {
        this.title = title;
        this.portions = portions;
        this.time = time;
        this.prepare = prepare;
        this.completed = completed;
        this.duty = duty;
    }
    public Job(String title, boolean prepare, boolean completed, Duty duty) {
        this.title = title;
        this.prepare = prepare;
        this.completed = completed;
        this.duty = duty;
        this.time = duty.getTime();
        this.portions = duty.getPortions();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getPortions() {
        return portions;
    }

    public void setPortions(int portions) {
        this.portions = portions;
    }

    public boolean isPrepare() {
        return prepare;
    }

    public void setPrepare(boolean prepare) {
        this.prepare = prepare;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public KitchenShift getShift() {
        return shift;
    }

    public void setShift(KitchenShift shift) {
        this.shift = shift;
    }

    public ArrayList<Cook> getCook() {
        return cooksAssigned;
    }

    public void setCook(ArrayList<Cook> cooks) {
        this.cooksAssigned = cooks;
    }

    public Duty getDuty() {
        return duty;
    }

    public void setDuty(Duty duty) {
        this.duty = duty;
    }

    public ArrayList<Cook> getCooksAssigned() {
        return cooksAssigned;
    }

    public void setCooksAssigned(ArrayList<Cook> cooksAssigned) {
        this.cooksAssigned = cooksAssigned;
    }

    public Job updateJob(KitchenShift shift, ArrayList<Cook> cooks, int quantity, int time) {
        if (shift != null) {
            this.shift = shift;

            for (Cook c: cooksAssigned) {
                if(shift.isCookAssigned(c)) {
                    this.cooksAssigned.remove(c);
                }
            }
        }
        if (cooks != null && !cooks.isEmpty()) {
            for (Cook c: cooks) {
                if (this.shift.isCookAssigned(c)) {
                    this.cooksAssigned.add(c);
                }
            }
        }
        if (quantity > 0)
            this.portions = quantity;
        if (time > 0)
            this.time = time;

        return this;
    }

    public Job assignJob(KitchenShift shift, ArrayList<Cook> cooks, int portions, int time) {
        this.shift = shift;

        if (cooks != null && !cooks.isEmpty()) {
            for (Cook c: cooks) {
                if (this.shift.isCookAssigned(c))
                    this.cooksAssigned.add(c);
            }
        }
        if (portions > 0)
            this.portions = portions;
        if (time > 0)
            this.time = time;

        return this;
    }
}
