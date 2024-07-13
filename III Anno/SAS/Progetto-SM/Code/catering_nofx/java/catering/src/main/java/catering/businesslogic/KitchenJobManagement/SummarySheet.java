package catering.businesslogic.KitchenJobManagement;

import catering.businesslogic.shiftManagement.Cook;
import catering.businesslogic.shiftManagement.Shift;

public class SummarySheet {
    private String service;

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

    // Create method
    public void create(ServiceInfo service) {

    }

    // Method to add a job
    public void addJob(String title, boolean prepare, boolean completed) {
        this.job = new Job(title, prepare, completed);
    }

    // Method to update a job
    public void updateJob(Job job, Shift shift, Cook cook, String portions, int time) {

    }

    // Method to delete the summary sheet
    public void deleteSummarySheet() {

    }

    // Method to get the owner
    public String getOwner() {

    }
}

