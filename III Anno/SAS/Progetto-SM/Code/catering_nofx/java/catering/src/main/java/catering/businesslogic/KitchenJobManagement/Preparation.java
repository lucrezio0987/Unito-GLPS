package catering.businesslogic.KitchenJobManagement;

import java.util.ArrayList;

public class Preparation extends Duty {
    public Preparation(String name, String description, int difficult, int importance, int time, String quantity, String portions, ArrayList<Preparation> preparations) {
        super(name, description, difficult, importance, time, quantity, portions, preparations);
    }

}
