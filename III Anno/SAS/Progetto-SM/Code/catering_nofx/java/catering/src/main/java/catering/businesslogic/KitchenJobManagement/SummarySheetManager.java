package catering.businesslogic.KitchenJobManagement;

import catering.businesslogic.CatERing;
import catering.businesslogic.UseCaseLogicException;
import catering.businesslogic.event.ServiceInfo;
import catering.businesslogic.shiftManagement.Cook;
import catering.businesslogic.shiftManagement.KitchenShift;
import catering.businesslogic.user.User;
import catering.persistence.PersistenceManager;
import catering.persistence.ResultHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SummarySheetManager {
    private List<SummarySheetEventReceiver> eventReceivers;
    private SummarySheet sheet;


    // Constructor
    public SummarySheetManager() {
        this.eventReceivers = new ArrayList<>();
    }

    // Getters and Setters
    public List<SummarySheetEventReceiver> getEventReceivers() {
        return eventReceivers;
    }

    public void setEventReceivers(List<SummarySheetEventReceiver> eventReceivers) {
        this.eventReceivers = eventReceivers;
    }

    public SummarySheet getSheet() {
        return sheet;
    }

    public void setSheet(SummarySheet sheet) {
        this.sheet = sheet;
    }

    // Event sender methods
    public void addEventReceiver(SummarySheetEventReceiver er) {
        this.eventReceivers.add(er);
    }

    public void removeEventReceiver(SummarySheetEventReceiver er) {
        this.eventReceivers.remove(er);
    }

    private void notifySheetCreated(SummarySheet sheet) {
        for (SummarySheetEventReceiver er : eventReceivers) {
            er.updateSheetCreated(sheet);
        }
    }

    private void notifySheetModified(SummarySheet sheet) {
        for (SummarySheetEventReceiver er : eventReceivers) {
            er.updateSheetModified(sheet);
        }
    }

    private void notifySheetDeleted(SummarySheet sheet) {
        for (SummarySheetEventReceiver er : eventReceivers) {
            er.updateSheetDeleted(sheet);
        }
    }

    private void notifyJobAdded(Job job, SummarySheet sheet) {
        for (SummarySheetEventReceiver er : eventReceivers) {
            er.updateJobAdded(job, sheet);
        }
    }

    private void notifyJobUpdated(Job job) {
        for (SummarySheetEventReceiver er : eventReceivers) {
            er.updateJobUpdated(job);
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

    public boolean isOwner(User user) {
        return this.sheet.getOwner().equals(user);
    }

    public SummarySheet modifySheet(SummarySheet sheet) throws UseCaseLogicException {
        User user = CatERing.getInstance().getUserManager().getUser();

        if (CatERing.getInstance().getSheetMgr().isOwner(user)) {
            this.sheet = sheet;
            notifySheetModified(sheet);
            return this.sheet;
        } else
            throw new UseCaseLogicException();
    }

    public SummarySheet deleteSheet(SummarySheet sheet) throws UseCaseLogicException {
        User user = CatERing.getInstance().getUserManager().getUser();

        if (!isOwner(user))
            throw new UseCaseLogicException();
        else{
            if (sheet.isNotUsed()) {
                sheet.clearSummarySheet();
                this.sheet = null;
                notifySheetDeleted(sheet);
                return this.sheet;
            } else
                throw new UseCaseLogicException();
        }
    }

    public Job addJob(String title, boolean prepare, boolean completed, Duty duty) throws UseCaseLogicException {
        User user = CatERing.getInstance().getUserManager().getUser();
        if (sheet != null && isOwner(user)) {
            Job job = sheet.addJob(title, prepare, completed, duty);
            notifyJobAdded(job, sheet);
            return job;
        }
        else
            throw new UseCaseLogicException();
    }

    public Job updateJob(Job job, KitchenShift shift, ArrayList<Cook> cooks, int portions, int time) throws UseCaseLogicException {
        User user = CatERing.getInstance().getUserManager().getUser();
        if (sheet != null && isOwner(user)) {
           job = sheet.updateJob(job, shift, cooks, portions, time);
           notifyJobUpdated(job);
           return job;
        } else
            throw new UseCaseLogicException();
    }

    public ArrayList<Job> sortJobs(String sorting) throws UseCaseLogicException {
        User user = CatERing.getInstance().getUserManager().getUser();

        if (isChef(user) && sheet != null) {
            return sheet.sortJobs(sorting);
        } else {
            throw new UseCaseLogicException();
        }
    }

    // utility methods
    public ArrayList<SummarySheet> getAllSheet(User user) {
        String getAllSheet = "SELECT * FROM sheets WHERE owner_id = " + user.getId();
        ArrayList<SummarySheet> sheets = new ArrayList<>();
        PersistenceManager.executeQuery(getAllSheet, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                SummarySheet sheet = new SummarySheet(ServiceInfo.getService(rs.getInt("service")), user);
                sheet.setId(rs.getInt("id"));
                sheets.add(sheet);
            }
        });
        return sheets;
    }
}

