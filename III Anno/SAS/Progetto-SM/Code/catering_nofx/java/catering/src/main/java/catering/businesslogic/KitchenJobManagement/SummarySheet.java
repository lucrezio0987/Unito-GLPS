package catering.businesslogic.KitchenJobManagement;

import catering.businesslogic.event.ServiceInfo;
import catering.businesslogic.shiftManagement.Cook;
import catering.businesslogic.shiftManagement.KitchenShift;
import catering.businesslogic.user.User;

import catering.persistence.BatchUpdateHandler;
import catering.persistence.PersistenceManager;
import org.apache.commons.lang3.time.DateUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ArrayList;
import java.util.Comparator;

public class SummarySheet {
    private int id;
    private ServiceInfo service;
    private User owner;
    private ArrayList<Job> jobs = new ArrayList<>();

    // Constructor
    public SummarySheet(ServiceInfo service, User owner) {
        this.id = 0;
        this.service = service;
        this.owner = owner;

        ArrayList<Duty> duties = service.getDuties();
        for (Duty duty : duties) {
            Job job = new Job(duty.getName(), duty.getPortions(), duty.getTime(), true, false, duty);
            jobs.add(job);
        }
    }
    // Getters and Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public ServiceInfo getService() {
        return service;
    }
    public void setService(ServiceInfo service) {
        this.service = service;
    }
    public User getOwner() {
        return owner;
    }
    public void setOwner(User owner) {
        this.owner = owner;
    }
    public ArrayList<Job> getJobs() {
        return jobs;
    }
    public void setJobs(ArrayList<Job> jobs) {
        this.jobs = jobs;
    }

    // Method to add a job
    public Job addJob(String title, boolean prepare, boolean completed, Duty duty) {
        Job job = new Job(title, prepare, completed, duty);
        this.jobs.add(job);
        return job;
    }

    // Method to update a job
    public Job updateJob(Job job, KitchenShift shift, ArrayList<Cook> cooks, int quantity, int time) {
        return job.updateJob(shift, cooks, quantity, time);
    }

    // Method to delete the summary sheet
    public void clearSummarySheet() {
        this.jobs = null;
    }

    // Method to sort
    public ArrayList<Job> sortJobs(String sorting) {
        jobs.sort(getJobComparator(sorting));
        return jobs;
    }

    // Method to check if the sheet is in use
    public boolean isNotUsed() {
        return !DateUtils.isSameDay(this.service.getDate(), new Date());
    }

    // Utility methods
    private Comparator<Job> getJobComparator(String sorting) {
        switch (sorting) {
            case "IMPORTANZA":
                return Comparator.comparing(job -> job.getDuty().getImportance());
            case "DIFFICOLTA":
                return Comparator.comparing(job -> job.getDuty().getDifficult());
            default:
                return null;
        }
    }

    public static void createSheet(SummarySheet sheet) {
        String sheetCreate = "INSERT INTO sheets (service, owner_id) VALUES (?, ?);";
        int[] result = PersistenceManager.executeBatchUpdate(sheetCreate, 1, new BatchUpdateHandler() {
            @Override
            public void handleBatchItem(PreparedStatement ps, int batchCount) throws SQLException {
                ps.setInt(1, sheet.service.getId());
                ps.setInt(2, sheet.owner.getId());
            }

            @Override
            public void handleGeneratedIds(ResultSet rs, int count) throws SQLException {
                if (count == 0) {
                    sheet.id = rs.getInt(1);
                }
            }
        });

        if (result[0] > 0) {
            for (Job job : sheet.jobs) {
                Job.saveJobDB(job, sheet);
            }
        }
    }

    public static void modifySheet(SummarySheet sheet) {
        String sheetModify = "UPDATE sheets SET service = ? , owner_id = ?  WHERE id = ?";
        int[] result = PersistenceManager.executeBatchUpdate(sheetModify, 1, new BatchUpdateHandler() {
            @Override
            public void handleBatchItem(PreparedStatement ps, int batchCount) throws SQLException {
                ps.setInt(1, sheet.service.getId());
                ps.setInt(2, sheet.owner.getId());
                ps.setInt(3, sheet.id);
            }
            @Override
            public void handleGeneratedIds(ResultSet rs, int count) throws SQLException {
                if (count == 0) {
                    sheet.id = rs.getInt(1);
                }
            }
        });

        if (result[0] > 0) {
            for (Job job : sheet.jobs) {
                Job.modifyJobDB(job);
            }
        }
    }

    public static void deleteSheet(SummarySheet sheet) {
        Job.deleteJobDB(sheet);
        sheet.clearSummarySheet();
        String sheetDelete = "DELETE FROM sheets WHERE id = " + sheet.getId();
        PersistenceManager.executeUpdate(sheetDelete);
    }

    public static void addJobToDB(Job job, SummarySheet sheet) {
        Job.saveJobDB(job, sheet);
    }

    public static void updateJobToDB(Job job) {
        Job.modifyJobDB(job);
    }
}

