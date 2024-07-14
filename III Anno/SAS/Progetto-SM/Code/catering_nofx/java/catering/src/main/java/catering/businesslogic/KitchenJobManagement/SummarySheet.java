package catering.businesslogic.KitchenJobManagement;

import catering.businesslogic.event.ServiceInfo;
import catering.businesslogic.shiftManagement.Cook;
import catering.businesslogic.shiftManagement.Shift;
import catering.businesslogic.user.User;

import java.util.ArrayList;

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
    public void addJob(String title, boolean prepare, boolean completed) {
        this.jobs.add(new Job(title, prepare, completed));
    }

    // Method to update a job
    public void updateJob(Job job, Shift shift, Cook cook, String portions, int time) {
        if (shift != null) {
            job.setShift(shift); // assuming Job class has a method setShift()
        }
        if (cook != null) {
            job.setCook(cook); // assuming Job class has a method setCook()
        }
        if (portions != null) {
            job.setPortions(Integer.parseInt(portions));
        }
        if (time > 0) {
            job.setTime(time);
        }
    }

    // Method to delete the summary sheet
    public void deleteSummarySheet() {
        this.service = null;
        this.jobs = null;
    }

    // Method to get the owner
    public String getOwner() {
        // Assuming there's a way to determine the owner of the service
        return "Owner"; // Placeholder, replace with actual logic to get the owner
    }

    // Method to print summary sheet details
    public void printSummary() {
        System.out.println("Service: " + service);
        System.out.println("Job: " + jobs);
    }

    // toString method
    @Override
    public String toString() {
        return "SummarySheet{" +
                "service='" + service + '\'' +
                ", job=" + jobs +
                '}';
    }
}

