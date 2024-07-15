package catering.businesslogic.recipe;

import catering.businesslogic.KitchenJobManagement.Duty;

import java.util.ArrayList;

public class Preparation extends Duty {
    public Preparation(String name, String description, int difficult, int importance, int time, int quantity, int portions, ArrayList<Preparation> preparations) {
        super(name, description, difficult, importance, time, quantity, portions, preparations);
    }

}
