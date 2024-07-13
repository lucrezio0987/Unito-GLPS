package catering.businesslogic.KitchenJobManagement;

import catering.businesslogic.shiftManagement.Cook;
import catering.businesslogic.shiftManagement.Shift;

public class Job {
    private String title;
    private int time;
    private int portions;
    private boolean prepare;
    private boolean completed;

    public Job(String title, int portions, boolean prepare, boolean completed) {
        this.title = title;
        this.portions = portions;
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

    public void create(String title, int portions, boolean prepare, boolean completed) {

    }

    public void assign(Shift shift, Cook cook, String portions, int time) {

    }
}
