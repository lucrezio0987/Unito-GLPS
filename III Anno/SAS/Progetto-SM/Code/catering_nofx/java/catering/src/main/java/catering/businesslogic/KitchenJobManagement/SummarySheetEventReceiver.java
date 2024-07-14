package catering.businesslogic.KitchenJobManagement;

import catering.businesslogic.shiftManagement.Shift;

public interface SummarySheetEventReceiver {
    void updateSheetCreated(SummarySheet sheet);
    void updateSheetModified(SummarySheet sheet);
    void updateJobAdded(Job job);
    void updateJobUpdated(Job job);
    void updateSheetDeleted(SummarySheet sheet);
    void updateJobAssigned(Job job, Shift shift);
}

