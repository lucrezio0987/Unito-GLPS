package catering.persistence;

import catering.businesslogic.KitchenJobManagement.Job;
import catering.businesslogic.KitchenJobManagement.JobEventReceiver;
import catering.businesslogic.shiftManagement.KitchenShift;
import catering.businesslogic.shiftManagement.Shift;

public class JobPersistence implements JobEventReceiver
{
    @Override
    public void updateJobAssigned(Job job, KitchenShift shift) {
        Job.assignJobDB(job, shift);
    }
}
