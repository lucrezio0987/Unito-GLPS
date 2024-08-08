package catering.persistence;

import catering.businesslogic.KitchenJobManagement.Job;
import catering.businesslogic.KitchenJobManagement.SummarySheet;
import catering.businesslogic.KitchenJobManagement.SummarySheetEventReceiver;
import catering.businesslogic.shiftManagement.Shift;

public class SummarySheetPersistence implements SummarySheetEventReceiver {
    @Override
    public void updateSheetCreated(SummarySheet sheet) {
        SummarySheet.createSheet(sheet);
    }

    @Override
    public void updateSheetModified(SummarySheet sheet) { SummarySheet.modifySheet(sheet);}

    @Override
    public void updateJobAdded(Job job, SummarySheet sheet) {
        SummarySheet.addJobToDB(job, sheet);
    }

    @Override
    public void updateJobUpdated(Job job) {
        SummarySheet.updateJobToDB(job);
    }

    @Override
    public void updateSheetDeleted(SummarySheet sheet) {
        SummarySheet.deleteSheet(sheet);
    }
}
