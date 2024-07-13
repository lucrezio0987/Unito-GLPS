package catering.businesslogic.KitchenJobManagement;

import catering.businesslogic.shiftManagement.Shift;

public interface JobEventReceiver {
    void updateJobAssigned(Job job, Shift shift);
}

