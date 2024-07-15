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
    private ArrayList<Job> jobs;

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


    public ServiceInfo getService() {
        return service;
    }

    public void setService(ServiceInfo service) {
        this.service = service;
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
    public void deleteSummarySheet() {
        this.service = null;
        this.jobs = null;
    }

    // Method to get the owner
    public User getOwner() {
        return owner;
    }

    public boolean isNotUsed() {
        return !DateUtils.isSameDay(this.service.getDate(), new Date());
    }

    public boolean clearSummarySheet() {
        this.jobs = null;
        return true;
    }

    public ArrayList<Job> sortJobs(String sorting) {
        jobs.sort(getJobComparator(sorting));
        return jobs;
    }

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
                ps.setInt(1, sheet.owner.getId());
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
        String sheetModify = "UPDATE sheets SET service = " + sheet.service + ", owner_id = " + sheet.owner + " WHERE id = " + sheet.id;
        int row = PersistenceManager.executeUpdate(sheetModify);
        if (row > 0) {
            for (Job job : sheet.jobs) {
                Job.modifyJobDB(job);
            }
        }
    }

    public static void deleteSheet(SummarySheet sheet){
        String sheetDelete = "DELETE FROM sheets WHERE id = " + sheet.getId();
        int row = PersistenceManager.executeUpdate(sheetDelete);
        if(row > 0){
            for(Job job : sheet.jobs)
                Job.deleteJobDB(job);
        }
    }

    public static void addJobToDB(Job job, SummarySheet sheet) {
        Job.saveJobDB(job, sheet);
    }

    public static void updateJobToDB(Job job){
        Job.modifyJobDB(job);
    }
}

