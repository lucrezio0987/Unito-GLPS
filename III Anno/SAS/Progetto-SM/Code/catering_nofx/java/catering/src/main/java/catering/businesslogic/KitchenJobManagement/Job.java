package catering.businesslogic.KitchenJobManagement;

import catering.businesslogic.shiftManagement.Cook;
import catering.businesslogic.shiftManagement.KitchenShift;
import catering.businesslogic.shiftManagement.Shift;
import catering.persistence.BatchUpdateHandler;
import catering.persistence.PersistenceManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Job {
    private int id;
    private String title;
    private int time;
    private int portions;
    private boolean prepare;
    private boolean completed;
    private KitchenShift shift;
    private ArrayList<Cook> cooksAssigned;
    private Duty duty;

    public Job(String title, int portions, int time, boolean prepare, boolean completed, Duty duty) {
        this.id = 0;
        this.title = title;
        this.portions = portions;
        this.time = time;
        this.prepare = prepare;
        this.completed = completed;
        this.duty = duty;
    }

    public Job(String title, boolean prepare, boolean completed, Duty duty) {
        this.id = 0;
        this.title = title;
        this.prepare = prepare;
        this.completed = completed;
        this.duty = duty;
        this.time = duty.getTime();
        this.portions = duty.getPortions();
    }

    public int getId() {
        return id;
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

            for (Cook c : cooksAssigned) {
                if (shift.isCookAssigned(c)) {
                    this.cooksAssigned.remove(c);
                }
            }
        }
        if (cooks != null && !cooks.isEmpty()) {
            for (Cook c : cooks) {
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
            for (Cook c : cooks) {
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

    public static void saveJob(Job job, SummarySheet sheet) {
        String jobAdd = "INSERT INTO jobs (name, time, portios, prepare, completed, duty_id, sheet_id) VALUES (?, ?, ?, ?, ?, ?, ?);";
        int[] result = PersistenceManager.executeBatchUpdate(jobAdd, 1, new BatchUpdateHandler() {
            @Override
            public void handleBatchItem(PreparedStatement ps, int batchCount) throws SQLException {
                ps.setString(1, job.getTitle());
                ps.setInt(2, job.getTime());
                ps.setInt(3, job.getPortions());
                ps.setBoolean(4, job.isPrepare());
                ps.setBoolean(5, job.isCompleted());
                ps.setInt(6, job.getDuty().loadIdByName(job.getDuty().getName()));
                ps.setInt(7, sheet.getId());
            }

            @Override
            public void handleGeneratedIds(ResultSet rs, int count) throws SQLException {
                if(count == 0)
                    job.id = rs.getInt(1); // Assuming 1 is the index of generated ID column
            }
        });
    }
    public static void modifyJob(Job job) {
        String modifyJob = "UPDATE jobs SET (name = ?, time = ?, portions = ?, prepare = ?, completed = ?, duty_id = ?) WHERE " +
                "id = ?";
        PersistenceManager.executeBatchUpdate(modifyJob, 1, new BatchUpdateHandler() {
            @Override
            public void handleBatchItem(PreparedStatement ps, int batchCount) throws SQLException {
                ps.setString(1, job.getTitle());
                ps.setInt(2, job.getTime());
                ps.setInt(3, job.getPortions());
                ps.setBoolean(4, job.isPrepare());
                ps.setBoolean(5, job.isCompleted());
                ps.setInt(6, job.getDuty().loadIdByName(job.getDuty().getName()));
                ps.setInt(7, job.getId());
            }
            @Override
            public void handleGeneratedIds(ResultSet rs, int count) throws SQLException {}
        });
    }

    public static void deleteJob(Job job) {
        String deleteJob = "DELETE FROM jobs WHERE id = " + job.getId();
        PersistenceManager.executeUpdate(deleteJob);
    }
}
