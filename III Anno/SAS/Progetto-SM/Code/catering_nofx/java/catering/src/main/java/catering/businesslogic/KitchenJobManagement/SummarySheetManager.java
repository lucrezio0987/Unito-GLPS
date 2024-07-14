package catering.businesslogic.KitchenJobManagement;

import catering.businesslogic.CatERing;
import catering.businesslogic.UseCaseLogicException;
import catering.businesslogic.event.ServiceInfo;
import catering.businesslogic.menu.Menu;
import catering.businesslogic.user.User;
import catering.businesslogic.user.UserManager;

import java.security.Provider;
import java.util.ArrayList;
import java.util.List;

public class SummarySheetManager {
    private List<SummarySheetEventReceiver> receivers;
    private SummarySheet sheet;


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

    public SummarySheet getSheet() {
        return sheet;
    }

    public void setSheet(SummarySheet sheet) {
        this.sheet = sheet;
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
    public SummarySheet createSheet(ServiceInfo service) throws UseCaseLogicException {
        User user = CatERing.getInstance().getUserManager().getUser();

        if (isChef(user)) {
            this.sheet = new SummarySheet(service, user);
            notifySheetCreated(this.sheet);
            return this.sheet;
        }
        else
            throw new UseCaseLogicException();
    }

    public boolean isChef(User user) {
        // Implementation to check if the user is a chef
        return user.isChef();
    }

    public void modifySheet(SummarySheet sheet) {
        // Implementation for modifying a summary sheet
    }

    public void deleteSheet(SummarySheet sheet) {
        // Implementation for deleting a summary sheet
    }
}

