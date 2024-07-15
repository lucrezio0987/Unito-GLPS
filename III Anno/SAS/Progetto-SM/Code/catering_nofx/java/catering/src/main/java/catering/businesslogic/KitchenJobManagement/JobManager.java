package catering.businesslogic.KitchenJobManagement;

import catering.businesslogic.CatERing;
import catering.businesslogic.UseCaseLogicException;
import catering.businesslogic.shiftManagement.Cook;
import catering.businesslogic.shiftManagement.KitchenShift;
import catering.businesslogic.user.User;

import java.util.ArrayList;
import java.util.List;

public class JobManager {
    private List<JobEventReceiver> eventReceivers;

    // Constructor
    public JobManager() {
        this.eventReceivers = new ArrayList<>();
    }

    // Event sender methods
    public void addReceiver(JobEventReceiver er) {
        this.eventReceivers.add(er);
    }

    public void removeReceiver(JobEventReceiver er) {
        this.eventReceivers.remove(er);
    }

    private void notifyJobAssigned(Job job, KitchenShift shift) {
        for (JobEventReceiver er : eventReceivers) {
            er.updateJobAssigned(job, shift);
        }
    }

    // operation methods
    public Job assignJob(Job job, KitchenShift shift, ArrayList<Cook> cooks, int portions, int time) throws UseCaseLogicException {
        User user = CatERing.getInstance().getUserManager().getUser();
        SummarySheet sheet = CatERing.getInstance().getSheetMgr().getSheet();

        if (sheet != null && CatERing.getInstance().getSheetMgr().isOwner(user)) {
            job = job.assignJob(shift, cooks, portions, time);
            notifyJobAssigned(job, shift);
            return job;
        } else
            throw new UseCaseLogicException();
    }

    public boolean isChef(User user) {
        return user.isChef();
    }
}
