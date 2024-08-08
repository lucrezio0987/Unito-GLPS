package catering;

import catering.businesslogic.CatERing;
import catering.businesslogic.KitchenJobManagement.Job;
import catering.businesslogic.KitchenJobManagement.SummarySheet;
import catering.businesslogic.UseCaseLogicException;
import catering.businesslogic.event.EventInfo;
import catering.businesslogic.event.ServiceInfo;
import catering.businesslogic.menu.Menu;
import catering.businesslogic.menu.Section;
import catering.businesslogic.recipe.Recipe;
import catering.businesslogic.shiftManagement.Cook;
import catering.businesslogic.shiftManagement.Shift;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

public class TestShowBoard {
    public static void main(String[] args) {
        try {
            System.out.println("TEST FAKE LOGIN");
            CatERing.getInstance().getUserManager().fakeLogin("Lidia");
            System.out.println(CatERing.getInstance().getUserManager().getUser());

            System.out.println("\nTEST GET EVENT INFO");
            ArrayList<EventInfo> events = CatERing.getInstance().getEventManager().getEventInfo();
            for (EventInfo e : events) {
                System.out.println(e);
                for (ServiceInfo s : e.getServices()) {
                    System.out.println("\t" + s);
                }
            }
            System.out.println("");


            ArrayList<Cook> cooks = new ArrayList<>();
            cooks.add(new Cook( "Mario Rossi"));
            cooks.add(new Cook("Luigi Bianchi"));
            cooks.add(new Cook("Giuseppe Verdi"));
            cooks.add(new Cook("Anna Neri"));

            int i = 1;
            for(Cook c : cooks) {
                c.setId(i);
                i++;
            }
            ArrayList<Shift> shifts = new ArrayList<>();
            shifts.add(new Shift(
                    "Turno di cucina principale", // Descrizione del turno
                    Date.valueOf("2024-10-1"), // Data del turno (esempio)
                    Time.valueOf("05:00:00"), // Ora di inizio del turno (esempio)
                    Time.valueOf("11:00:00"), // Durata del turno (esempio)
                    cooks // Lista di cuochi assegnati al turno
            ));
            shifts.add(new Shift(
                    "Turno di cucina secondario", // Descrizione del turno
                    Date.valueOf("2024-8-8"), // Data del turno (esempio)
                    Time.valueOf("09:00:00"), // Ora di inizio del turno (esempio)
                    Time.valueOf("14:00:00"), // Durata del turno (esempio)
                    cooks
            ));
            int s = 1;
            for(Shift shift : shifts){
                shift.setId(s);
                s++;
            }

            CatERing.getInstance().getBoardMgr().createBoard("Board Test", events.get(0).getName(), shifts);

            System.out.println("\nTEST SHOW BOARD");
            ArrayList<Shift> board = CatERing.getInstance().getBoardMgr().showBoard();
            System.out.println("Board: " + CatERing.getInstance().getBoardMgr().getBoard().getName() + " - " + CatERing.getInstance().getBoardMgr().getBoard().getEvent());
            for(Shift shift : board){
                System.out.println(shift.getDescription() + ": " + shift.getDate() + "| " + shift.getTime() + "| " + shift.getDuration());
                for(Cook cook : shift.getCooks()){
                    System.out.println("\t" + cook.getName());
                }
            }



        } catch (UseCaseLogicException e) {
            System.out.println("Errore di logica nello use case");
        }
    }
}
