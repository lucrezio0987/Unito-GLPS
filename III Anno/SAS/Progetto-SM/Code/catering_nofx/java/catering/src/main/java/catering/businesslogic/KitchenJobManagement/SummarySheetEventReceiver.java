package catering.businesslogic.KitchenJobManagement;

public interface SummarySheetEventReceiver {
    void updateSheetCreated(SummarySheet sheet);
    void updateSheetModified(SummarySheet sheet);
    void updateJobAdded(Job job);
    void updateJobUpdated(Job job);
    void updateSheetDeleted(SummarySheet sheet);
}

