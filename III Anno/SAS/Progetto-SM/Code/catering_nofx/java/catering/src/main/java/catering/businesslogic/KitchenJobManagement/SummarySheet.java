package catering.businesslogic.KitchenJobManagement;

import catering.businesslogic.shiftManagement.Cook;
import catering.businesslogic.shiftManagement.Shift;

public class SummarySheet {
    private String service;
    private Job job;

    // Constructor
    public SummarySheet(String service) {
        this.service = service;
    }

    // Getters and Setters
    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    // Create method
    public void create(ServiceInfo service) {
        this.service = service.getName(); // assuming Service class has a method getServiceName()
    }

    // Method to add a job
    public void addJob(String title, boolean prepare, boolean completed) {
        this.job = new Job(title, prepare, completed);
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
        this.job = null;
    }

    // Method to get the owner
    public String getOwner() {
        // Assuming there's a way to determine the owner of the service
        return "Owner"; // Placeholder, replace with actual logic to get the owner
    }

    // Method to print summary sheet details
    public void printSummary() {
        System.out.println("Service: " + service);
        System.out.println("Job: " + job);
    }

    // toString method
    @Override
    public String toString() {
        return "SummarySheet{" +
                "service='" + service + '\'' +
                ", job=" + job +
                '}';
    }
}

