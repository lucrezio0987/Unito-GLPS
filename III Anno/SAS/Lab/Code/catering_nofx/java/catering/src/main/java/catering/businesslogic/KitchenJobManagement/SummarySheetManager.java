package catering.businesslogic.KitchenJobManagement;

import catering.businesslogic.user.User;

import java.security.Provider;
import java.util.ArrayList;
import java.util.List;

public class SummarySheetManager {
    private List<SummarySheetEventReceiver> receivers;

    // Constructor
    public SummarySheetManager() {
        this.receivers = new ArrayList<>();
    }

    // Getters and Setters
    public List<SummarySheetEventReceiver> getReceivers() {
        return receivers;
    }

    public void setReceivers(List<SummarySheetEventReceiver> receivers) {
        this.receivers = receivers;
    }

    // Event sender methods
    public void addReceiver(SummarySheetEventReceiver er) {
        this.receivers.add(er);
    }

    public void removeReceiver(SummarySheetEventReceiver er) {
        this.receivers.remove(er);
    }

    private void notifySheetCreated(SummarySheet sheet) {
        for (SummarySheetEventReceiver receiver : receivers) {
            receiver.sheetCreated(sheet);
        }
    }

    private void notifySheetModified(SummarySheet sheet) {
        for (SummarySheetEventReceiver receiver : receivers) {
            receiver.sheetModified(sheet);
        }
    }

    private void notifyJobAdded(Job job) {
        for (SummarySheetEventReceiver receiver : receivers) {
            receiver.jobAdded(job);
        }
    }

    private void notifyJobUpdated(Job job) {
        for (SummarySheetEventReceiver receiver : receivers) {
            receiver.jobUpdated(job);
        }
    }

    private void notifySheetDeleted(SummarySheet sheet) {
        for (SummarySheetEventReceiver receiver : receivers) {
            receiver.sheetDeleted(sheet);
        }
    }

    // Operations methods
    public SummarySheet createSheet(Service service) {
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
}

