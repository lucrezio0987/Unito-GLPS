package catering.businesslogic.KitchenJobManagement;

import catering.businesslogic.event.ServiceInfo;
import catering.businesslogic.shiftManagement.Cook;
import catering.businesslogic.shiftManagement.KitchenShift;
import catering.businesslogic.shiftManagement.Shift;
import catering.businesslogic.user.User;

import java.util.ArrayList;
import java.util.Comparator;

import static catering.businesslogic.KitchenJobManagement.SummarySheetManager.Sorting.DIFFICOLTA;
import static catering.businesslogic.KitchenJobManagement.SummarySheetManager.Sorting.IMPORTANZA;

public class SummarySheet {
    private ServiceInfo service;
    private User owner;
    private ArrayList<Job> jobs;

    // Constructor
    public SummarySheet(ServiceInfo service, User owner) {
        this.service = service;
        this.owner = owner;

        ArrayList<Duty> duties = service.getDuties();
        for (Duty duty: duties) {
            Job job = new Job(duty.getTitle(), duty.getPortions(), duty.getTime(), true, false, duty);
            jobs.add(job);
        }
    }

    // Getters and Setters
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


}

