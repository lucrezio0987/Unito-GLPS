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
import catering.businesslogic.user.User;

import java.util.ArrayList;

public class TestModifiedSheet {
    public static void main(String[] args) {
        try {
            System.out.println("TEST FAKE LOGIN");
            CatERing.getInstance().getUserManager().fakeLogin("Lidia");
            User user = CatERing.getInstance().getUserManager().getUser();
            System.out.println(user);

            System.out.println("\nTEST CREATE MENU");
            Menu m = CatERing.getInstance().getMenuManager().createMenu("Menu TestMODIFIED");

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
            CatERing.getInstance().getMenuManager().insertItem(recipes.get(4), antipasti);
            CatERing.getInstance().getMenuManager().insertItem(recipes.get(6), antipasti);
            CatERing.getInstance().getMenuManager().insertItem(recipes.get(7), antipasti);
            CatERing.getInstance().getMenuManager().insertItem(recipes.get(1), secondi);
            CatERing.getInstance().getMenuManager().insertItem(recipes.get(3), secondi);
            System.out.println(m.testString());

            System.out.println("\nTEST INSERT FREE ITEM");
            CatERing.getInstance().getMenuManager().insertItem(recipes.get(5));
            CatERing.getInstance().getMenuManager().insertItem(recipes.get(6));
            System.out.println(m.testString());

            System.out.println("\nTEST PUBLISH");
            CatERing.getInstance().getMenuManager().publish();
            System.out.println(m.testString());

            System.out.println("\nTEST CREATE SHEET");
            EventInfo event = CatERing.getInstance().getEventManager().getEventInfo().get(0);
            ServiceInfo service = event.getServices().get(0);
            SummarySheet s = CatERing.getInstance().getSheetMgr().createSheet(service);

            System.out.println("Foglio riepilogativo creato relativo al servizio: " + s.getService());
            System.out.println("Owner: " + s.getOwner());
            System.out.println("Compiti:");
            for (Job job : s.getJobs()) {
                System.out.println(job.getTitle());
            }

            //NUOVO FOGLIO RIEPILOGATIVO
            event = CatERing.getInstance().getEventManager().getEventInfo().get(0);
            ServiceInfo service2 = event.getServices().get(1);
            SummarySheet s2 = new SummarySheet(service2, user);

            System.out.println("\nTEST GET ALL SHEET");
            ArrayList<SummarySheet> allSheets = CatERing.getInstance().getSheetMgr().getAllSheet(user);
            System.out.println("Fogli riepilogativi relativi all'utente: " + user);
            if (allSheets.isEmpty())
                System.out.println("Nessun foglio riepilogativo presente");
            else
                for (SummarySheet sheet : allSheets)
                    System.out.println(sheet.getService().getName());


            System.out.println("\nTEST MODIFY SHEET");
            System.out.println("Foglio riepilogativo da modificare relativo al servizio:\n" + s.getService());
            System.out.println("Owner: " + s.getOwner());
            System.out.println("Compiti:");
            for (Job job : s.getJobs()) {
                System.out.println(job.getTitle());
            }
            System.out.println("");

            CatERing.getInstance().getSheetMgr().modifySheet(s2);
            s = CatERing.getInstance().getSheetMgr().getSheet();
            System.out.println("Foglio riepilogativo modificato. Valore del foglio:\n" + s.getService());
            System.out.println("Owner: " + s.getOwner());
            System.out.println("Compiti:");
            for (Job job : s.getJobs()) {
                System.out.println(job.getTitle());
            }

            System.out.println("\nTEST GET ALL SHEET");
            allSheets = CatERing.getInstance().getSheetMgr().getAllSheet(user);
            System.out.println("Fogli riepilogativi relativi all'utente: " + user);
            if (allSheets.isEmpty())
                System.out.println("Nessun foglio riepilogativo presente");
            else
                for (SummarySheet sheet : allSheets)
                    System.out.println(sheet.getService().getName());

        } catch (UseCaseLogicException e) {
            System.out.println("Errore di logica nello use case");
        }
    }
}
