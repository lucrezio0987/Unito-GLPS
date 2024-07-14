package catering.businesslogic.KitchenJobManagement;

import catering.businesslogic.CatERing;
import catering.businesslogic.UseCaseLogicException;
import catering.businesslogic.event.ServiceInfo;
import catering.businesslogic.shiftManagement.Cook;
import catering.businesslogic.shiftManagement.KitchenShift;
import catering.businesslogic.shiftManagement.Shift;
import catering.businesslogic.user.User;

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
        for (SummarySheetEventReceiver er : receivers) {
            er.updateSheetCreated(sheet);
        }
    }

    private void notifySheetModified(SummarySheet sheet) {
        for (SummarySheetEventReceiver er : receivers) {
            er.updateSheetModified(sheet);
        }
    }

    private void notifyJobAdded(Job job) {
        for (SummarySheetEventReceiver er : receivers) {
            er.updateJobAdded(job);
        }
    }

    private void notifyJobUpdated(Job job) {
        for (SummarySheetEventReceiver er : receivers) {
            er.updateJobUpdated(job);
        }
    }

    private void notifySheetDeleted(SummarySheet sheet) {
        for (SummarySheetEventReceiver er : receivers) {
            er.updateSheetDeleted(sheet);
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
        return user.isChef();
    }

    public void modifySheet(SummarySheet sheet) {
    }

    public void deleteSheet(SummarySheet sheet) {

    }

    public Job addJob(String title, boolean prepare, boolean completed, Duty duty) throws UseCaseLogicException {
        User user = CatERing.getInstance().getUserManager().getUser();
        if (isChef(user) && sheet != null) {
            Job job = sheet.addJob(title, prepare, completed, duty);
            notifyJobAdded(job);
            return job;
        }
        else
            throw new UseCaseLogicException();
    }

    public Job updateJob(Job job, KitchenShift shift, ArrayList<Cook> cooks, int portions, int time) throws UseCaseLogicException {
        User user = CatERing.getInstance().getUserManager().getUser();
        if (isChef(user) && sheet != null) {
           job = sheet.updateJob(job, shift, cooks, portions, time);
           notifyJobUpdated(job);
           return job;
        } else
            throw new UseCaseLogicException();
    }

    public void sortJobs(String sorting) {

    }
}

