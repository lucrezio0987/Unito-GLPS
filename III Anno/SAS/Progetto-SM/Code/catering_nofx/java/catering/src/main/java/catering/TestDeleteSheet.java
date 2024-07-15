package catering;

import catering.businesslogic.CatERing;
import catering.businesslogic.KitchenJobManagement.SummarySheet;
import catering.businesslogic.UseCaseLogicException;
import catering.businesslogic.user.User;

import java.util.ArrayList;

public class TestDeleteSheet {
    public static void main(String[] args) {
        try {
            System.out.println("TEST FAKE LOGIN");
            CatERing.getInstance().getUserManager().fakeLogin("Lidia");
            User user = CatERing.getInstance().getUserManager().getUser();
            System.out.println(user);

            ArrayList<SummarySheet> sheet = CatERing.getInstance().getSheetMgr().getAllSheet(user);
            boolean delete = CatERing.getInstance().getSheetMgr().deleteSheet(sheet.get(0));

            System.out.println("Foglio riepilogativo eliminato");
        } catch (UseCaseLogicException e) {
            System.out.println("Errore di logica nello use case");
        }
    }
}
