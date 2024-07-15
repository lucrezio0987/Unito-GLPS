package catering.businesslogic.KitchenJobManagement;

import catering.businesslogic.CatERing;
import catering.businesslogic.UseCaseLogicException;
import catering.businesslogic.shiftManagement.Cook;
import catering.businesslogic.shiftManagement.KitchenShift;
import catering.businesslogic.shiftManagement.Shift;
import catering.businesslogic.user.User;

import java.util.ArrayList;
import java.util.List;

public class JobManager {
    private List<JobEventReceiver> receivers;

    // Constructor
    public JobManager() {
        this.receivers = new ArrayList<>();
    }

    // Event sender methods
    public void addReceiver(JobEventReceiver er) {
        this.receivers.add(er);
    }

    public void removeReceiver(JobEventReceiver er) {
        this.receivers.remove(er);
    }

    private void notifyJobAssigned(Job job, KitchenShift shift) {
        for (JobEventReceiver er : receivers) {
            er.updateJobAssigned(job, shift);
        }
    }

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
