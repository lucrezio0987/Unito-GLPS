package catering.businesslogic.KitchenJobManagement;

import catering.businesslogic.shiftManagement.Cook;
import catering.businesslogic.shiftManagement.Shift;
import catering.businesslogic.user.User;

import java.util.ArrayList;
import java.util.List;

public class JobController {
    // Event sender methods
    public void addReceiver(SummarySheetEventReceiver er) {

    }

    public void removeReceiver(SummarySheetEventReceiver er) {


    }

    private void notifyJobAssigned(Job job, Shift shift) {

    }

    // Operations methods
    public void createJob(String title, int portions, boolean prepare, boolean completed) {

    }

    public void assignJob(Job job, Shift shift, Cook cook, String portions, int time) {

    }

    public boolean isChef(User user) {

    }
}
