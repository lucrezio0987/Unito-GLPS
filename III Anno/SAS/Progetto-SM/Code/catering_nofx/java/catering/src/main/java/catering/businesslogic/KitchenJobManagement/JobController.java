package catering.businesslogic.KitchenJobManagement;

import catering.businesslogic.shiftManagement.Cook;
import catering.businesslogic.shiftManagement.Shift;
import catering.businesslogic.user.User;

import java.util.ArrayList;
import java.util.List;

public class JobController {
    private List<SummarySheetEventReceiver> receivers;

    // Constructor
    public JobController() {
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

    // Operations methods
    public Job createJob(String title, int portions, boolean prepare, boolean completed) {
        return new Job(title, portions, prepare, completed);
    }

    public void assignJob(Job job, Shift shift, Cook cook, String portions, int time) {
        if (cook != null) {
            job.setCook(cook);
        }
        if (portions != null) {
            job.setPortions(Integer.parseInt(portions));
        }
        if (time > 0) {
            job.setTime(time);
        }
        job.setShift(shift);
        notifyJobAssigned(job, shift);
    }

    public boolean isChef(User user) {
        return user.isChef();
    }
}
