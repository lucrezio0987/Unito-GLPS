package catering.businesslogic.KitchenJobManagement;

import catering.businesslogic.user.User;
import catering.businessLogic.KitchenJobManager.Duty;
import catering.businessLogic.KitchenJobManager.Job;
import catering.businessLogic.shiftManagement.*;

import java.security.Provider;
import java.util.ArrayList;
import java.util.List;

public class SummarySheetManager {
    // Event sender methods
    public void addReceiver(SummarySheetEventReceiver er) {
        this.receivers.add(er);
    }

    public void removeReceiver(SummarySheetEventReceiver er) {
        this.receivers.remove(er);
    }

    private void notifySheetCreated(SummarySheet sheet) {

    }

    private void notifySheetModified(SummarySheet sheet) {

    }

    private void notifyJobAdded(Job job) {

    }

    private void notifyJobUpdated(Job job) {

    }

    private void notifySheetDeleted(SummarySheet sheet) {

    }

    // Operations methods
    public SummarySheet createSheet(ServiceInfo service) {
        // Implementation for creating a summary sheet
        return null;
    }

    public boolean isChef(User user) {
        // Implementation to check if the user is a chef
        return false;
    }

    public void modifySheet(SummarySheet sheet) {
        // Implementation for modifying a summary sheet
    }

    public void deleteSheet(SummarySheet sheet) {
        // Implementation for deleting a summary sheet
    }

    public void addJob(String title, boolean prepare, boolean completed, Duty duty) {

    }

    public void updateJob(Job job, Shift shift, Cook cook, String portions, int time) {

    }

    public void sortJobs(String sorting) {

    }
}

