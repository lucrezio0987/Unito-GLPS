package catering.persistence;

import catering.businesslogic.KitchenJobManagement.Job;
import catering.businesslogic.KitchenJobManagement.SummarySheet;
import catering.businesslogic.KitchenJobManagement.SummarySheetEventReceiver;
import catering.businesslogic.shiftManagement.Shift;

public class KitchenJobPersistence implements SummarySheetEventReceiver {
    @Override
    public void updateSheetCreated(SummarySheet sheet) {
        SummarySheet.createSheet(sheet);
    }

    @Override
    public void updateSheetModified(SummarySheet sheet) {

    }

    @Override
    public void updateJobAdded(Job job) {

    }

    @Override
    public void updateJobUpdated(Job job) {

    }

    @Override
    public void updateSheetDeleted(SummarySheet sheet) {

    }

    @Override
    public void updateJobAssigned(Job job, Shift shift) {

    }
}
