package catering.businesslogic.KitchenJobManagement;

import catering.businesslogic.shiftManagement.Cook;
import catering.businesslogic.shiftManagement.Shift;
import catering.businesslogic.user.User;

import java.util.ArrayList;
import java.util.List;

public class JobManager {
    private List<SummarySheetEventReceiver> receivers;

    // Constructor
    public JobManager() {
        this.receivers = new ArrayList<>();
    }

    // Event sender methods
    public void addReceiver(SummarySheetEventReceiver er) {
        this.receivers.add(er);
    }

    public void removeReceiver(SummarySheetEventReceiver er) {
        this.receivers.remove(er);
    }

    private void notifyJobAssigned(Job job, Shift shift) {

    }

    public void assignJob(Job job, Shift shift, Cook cook, String portions, int time) {

    }

    public boolean isChef(User user) {
        return user.isChef();
    }
}
