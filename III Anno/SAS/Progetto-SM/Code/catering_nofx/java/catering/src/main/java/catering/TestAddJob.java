package catering;

import catering.businesslogic.CatERing;
import catering.businesslogic.KitchenJobManagement.Duty;
import catering.businesslogic.KitchenJobManagement.Job;
import catering.businesslogic.KitchenJobManagement.SummarySheet;
import catering.businesslogic.UseCaseLogicException;
import catering.businesslogic.event.EventInfo;
import catering.businesslogic.event.ServiceInfo;
import catering.businesslogic.menu.Menu;
import catering.businesslogic.menu.Section;
import catering.businesslogic.recipe.Preparation;
import catering.businesslogic.recipe.Recipe;

import java.util.ArrayList;

public class TestAddJob {
    public static void main(String[] args) {
        try {
            System.out.println("TEST FAKE LOGIN");
            CatERing.getInstance().getUserManager().fakeLogin("Tony");
            System.out.println(CatERing.getInstance().getUserManager().getUser());

            System.out.println("\nTEST CREATE MENU");
            Menu m = CatERing.getInstance().getMenuManager().createMenu("Menu TestADDJOB");

            System.out.println("\nTEST DEFINE SECTION");
            Section antipasti = CatERing.getInstance().getMenuManager().defineSection("Antipasti");
            // Section primi = CatERing.getInstance().getMenuManager().defineSection("Primi");
            Section secondi = CatERing.getInstance().getMenuManager().defineSection("Secondi");
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
            CatERing.getInstance().getMenuManager().insertItem(recipes.get(7), antipasti);
            CatERing.getInstance().getMenuManager().insertItem(recipes.get(4), antipasti);
            CatERing.getInstance().getMenuManager().insertItem(recipes.get(8), antipasti);
            CatERing.getInstance().getMenuManager().insertItem(recipes.get(2), secondi);
            CatERing.getInstance().getMenuManager().insertItem(recipes.get(3), secondi);
            System.out.println(m.testString());

            System.out.println("\nTEST INSERT FREE ITEM");
            CatERing.getInstance().getMenuManager().insertItem(recipes.get(4));
            CatERing.getInstance().getMenuManager().insertItem(recipes.get(5));
            System.out.println(m.testString());

            System.out.println("\nTEST PUBLISH");
            CatERing.getInstance().getMenuManager().publish();
            System.out.println(m.testString());

            System.out.println("\nCREATE SHEET");
            EventInfo event = events.get(2);
            ServiceInfo service = event.getServices().get(2);
            SummarySheet s = CatERing.getInstance().getSheetMgr().createSheet(service);

            System.out.println("Foglio riepilogativo creato relativo al servizio: " + s.getService());
            System.out.println("Owner: " + s.getOwner());
            System.out.println("Compiti:");
            for (Job job : s.getJobs()) {
                System.out.println(job.getTitle());
            }
            System.out.println("");

            ArrayList<Preparation> preparations = new ArrayList<>();

            Duty saintHonore = new Duty(
                    "Torta Saint Honoré",
                    "Classica torta francese intitolata al patrono dei panettieri.",
                    5,
                    5,
                    120,
                    8,
                    8,
                    preparations
            );
            saintHonore.setId(19);

            System.out.println("\nTEST ADDJOB");
            CatERing.getInstance().getSheetMgr().addJob("Torta Saint Honore", true, false, saintHonore);
            System.out.println("Foglio riepilogativo con compito aggiunto: " + s.getService());
            System.out.println("Owner: " + s.getOwner());
            System.out.println("Compiti:");
            for (Job job : s.getJobs()) {
                System.out.println(job.getTitle());
            }
        } catch (UseCaseLogicException e) {
            System.out.println("Errore di logica nello use case");
        }
    }
}
