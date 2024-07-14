package catering.businesslogic.KitchenJobManagement;

import catering.businesslogic.shiftManagement.Cook;
import catering.businesslogic.shiftManagement.Shift;

public class Job {
    private String title;
    private int time;
    private int portions;
    private boolean prepare;
    private boolean completed;
    private Shift shift;
    private Cook cook;
    private Duty duty;

    public Job(String title, int portions, int time, boolean prepare, boolean completed, Duty duty) {
        this.title = title;
        this.portions = portions;
        this.time = time;
        this.prepare = prepare;
        this.completed = completed;
        this.duty = duty;
    }
    public Job(String title, boolean prepare, boolean completed) {
        this.title = title;
        this.prepare = prepare;
        this.completed = completed;
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

    public Shift getShift() {
        return shift;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }

    public Cook getCook() {
        return cook;
    }

    public void setCook(Cook cook) {
        this.cook = cook;
    }

    public Duty getDuty() {
        return duty;
    }

    public void setDuty(Duty duty) {
        this.duty = duty;
    }
}
