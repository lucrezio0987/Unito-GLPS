package catering.businesslogic.KitchenJobManagement;

public interface SummarySheetEventReceiver {
    void sheetCreated(SummarySheet sheet);
    void sheetModified(SummarySheet sheet);
    void jobAdded(Job job);
    void jobUpdated(Job job);
    void sheetDeleted(SummarySheet sheet);
}

