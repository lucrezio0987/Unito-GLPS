package catering.businesslogic.KitchenJobManagement;

import catering.businesslogic.shiftManagement.Shift;

public interface SummarySheetEventReceiver {
    void updateSheetCreated(SummarySheet sheet);
    void updateSheetModified(SummarySheet sheet);
    void updateSheetDeleted(SummarySheet sheet);
    void updateJobAdded(Job job, SummarySheet sheet);
    void updateJobUpdated(Job job);
}

