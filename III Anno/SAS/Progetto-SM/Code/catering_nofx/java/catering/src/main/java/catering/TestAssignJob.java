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
import catering.businesslogic.shiftManagement.KitchenShift;
import catering.businesslogic.shiftManagement.Shift;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

public class TestAssignJob {
    public static void main(String[] args) {
        try {
            System.out.println("TEST FAKE LOGIN");
            CatERing.getInstance().getUserManager().fakeLogin("Marinella");
            System.out.println(CatERing.getInstance().getUserManager().getUser());

            System.out.println("\nTEST CREATE MENU");
            Menu m = CatERing.getInstance().getMenuManager().createMenu("Menu TESTASSIGNJOB");

            System.out.println("\nTEST DEFINE SECTION");
            Section antipasti = CatERing.getInstance().getMenuManager().defineSection("Antipasti");
            Section primi = CatERing.getInstance().getMenuManager().defineSection("Primi");
            //Section secondi = CatERing.getInstance().getMenuManager().defineSection("Secondi");
            System.out.println(m.testString());

            System.out.println("\nTEST GET EVENT INFO");
            ArrayList<EventInfo> events = CatERing.getInstance().getEventManager().getEventInfo();
            for (EventInfo e : events) {
                System.out.println(e);
                for (ServiceInfo s : e.getServices()) {
                    System.out.println("\t" + s);
                }
            }
            System.out.println("");

            System.out.println("\nTEST GET RECIPES AND INSERT ITEM IN SECTION");
            ArrayList<Recipe> recipes = CatERing.getInstance().getRecipeManager().getRecipes();
            CatERing.getInstance().getMenuManager().insertItem(recipes.get(5), antipasti);
            CatERing.getInstance().getMenuManager().insertItem(recipes.get(6), antipasti);
            CatERing.getInstance().getMenuManager().insertItem(recipes.get(2), antipasti);
            CatERing.getInstance().getMenuManager().insertItem(recipes.get(4), primi);
            CatERing.getInstance().getMenuManager().insertItem(recipes.get(8), primi);
            System.out.println(m.testString());

            System.out.println("\nTEST INSERT FREE ITEM");
            CatERing.getInstance().getMenuManager().insertItem(recipes.get(1));
            CatERing.getInstance().getMenuManager().insertItem(recipes.get(3));
            System.out.println(m.testString());

            System.out.println("\nTEST PUBLISH");
            CatERing.getInstance().getMenuManager().publish();
            System.out.println(m.testString());

            System.out.println("\nTEST CREATE SHEET");
            EventInfo event = events.get(2);
            ServiceInfo service = event.getServices().get(1);
            SummarySheet s = CatERing.getInstance().getSheetMgr().createSheet(service);

            System.out.println("Foglio riepilogativo creato relativo al servizio:\n" + s.getService());
            System.out.println("Owner: " + s.getOwner());
            System.out.println("Compiti:");
            for (Job job : s.getJobs()) {
                System.out.println(job.getTitle());
            }

            System.out.println("\nTEST ASSIGN JOB");
            ArrayList<Cook> cooks = new ArrayList<>();

            // Aggiunta dei cuochi alla lista
            cooks.add(new Cook("Mario Rossi"));
            cooks.add(new Cook("Luigi Bianchi"));
            cooks.add(new Cook("Giuseppe Verdi"));

            int i = 1;
            for (Cook c : cooks) {
                c.setId(i);
                i++;
            }

            KitchenShift shift = new KitchenShift(
                    "Turno di cucina secondario", // Descrizione del turno
                    Date.valueOf("2024-09-20"), // Data del turno (esempio)
                    Time.valueOf("12:00:00"), // Ora di inizio del turno (esempio)
                    Time.valueOf("06:00:00"), // Durata del turno (esempio)
                    cooks);

            shift.setId(2);

            CatERing.getInstance().getJobMgr().assignJob(s.getJobs().get(2), shift, cooks, 3, 120);
            System.out.println("PER IL COMPITO:\n" + s.getJobs().get(2).getTitle() + " con ID: " + s.getJobs().get(2).getId());
            System.out.println("\nSONO STATI ASSEGNATI I CUOCHI:");
            for(Cook c : cooks) {
                System.out.println(c.getName() + " con ID: " + c.getId());
            }
            System.out.println("\nNEL TURNO DI CUCINA:\n" + shift.getDescription() + " con ID: " + shift.getId());

        } catch (UseCaseLogicException e) {
            System.out.println("Errore di logica nello use case");
        }
    }
}
