package catering;
import catering.businesslogic.CatERing;
import catering.businesslogic.KitchenJobManagement.Compito;
import catering.businesslogic.KitchenJobManagement.FoglioRiepilogativo;
import catering.businesslogic.KitchenJobManagement.Mansione;
import catering.businesslogic.UseCaseLogicException;
import catering.businesslogic.event.EventInfo;
import catering.businesslogic.event.ServiceInfo;
import catering.businesslogic.menu.Menu;
import catering.businesslogic.menu.Section;
import catering.businesslogic.recipe.Recipe;
import catering.businesslogic.tableManagement.Cuoco;
import catering.businesslogic.tableManagement.Turno;
import catering.businesslogic.user.User;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

public class TestFoglioRiepilogativo2a {
    public static void main(String[] args) {
        try {
            System.out.println("TEST FAKE LOGIN");
            CatERing.getInstance().getUserManager().fakeLogin("Lidia");
            User user = CatERing.getInstance().getUserManager().getUser();
            System.out.println(CatERing.getInstance().getUserManager().getUser());

            System.out.println("\nTEST CREATE MENU");
            Menu m = CatERing.getInstance().getMenuManager().createMenu("Menu TestCreateSheet");

            System.out.println("\nTEST DEFINE SECTION");
            Section antipasti = CatERing.getInstance().getMenuManager().defineSection("Antipasti");
            // Section primi = CatERing.getInstance().getMenuManager().defineSection("Primi");
            Section secondi = CatERing.getInstance().getMenuManager().defineSection("Secondi");
            System.out.println(m.testString());

            System.out.println("\nTEST GET EVENT INFO");
            ArrayList<EventInfo> events = CatERing.getInstance().getEventManager().getEventInfo();
            for (EventInfo e: events) {
                System.out.println(e);
                for (ServiceInfo s: e.getServices()) {
                    System.out.println("\t" + s);
                }
            }
            System.out.println("");

            System.out.println("\nTEST GET RECIPES AND INSERT ITEM IN SECTION");
            ArrayList<Recipe> recipes = CatERing.getInstance().getRecipeManager().getRecipes();
            CatERing.getInstance().getMenuManager().insertItem(recipes.get(0), antipasti);
            CatERing.getInstance().getMenuManager().insertItem(recipes.get(1), antipasti);
            CatERing.getInstance().getMenuManager().insertItem(recipes.get(2), antipasti);
            CatERing.getInstance().getMenuManager().insertItem(recipes.get(6), secondi);
            CatERing.getInstance().getMenuManager().insertItem(recipes.get(7), secondi);
            System.out.println(m.testString());

            System.out.println("\nTEST INSERT FREE ITEM");
            CatERing.getInstance().getMenuManager().insertItem(recipes.get(3));
            CatERing.getInstance().getMenuManager().insertItem(recipes.get(4));
            System.out.println(m.testString());

            System.out.println("\nTEST PUBLISH");
            CatERing.getInstance().getMenuManager().publish();
            System.out.println(m.testString());

            System.out.println("\nTEST CREATE SHEET");
            EventInfo event = events.get(1);
            ServiceInfo service = event.getServices().get(0);
            FoglioRiepilogativo s = CatERing.getInstance().getFoglioRiepilogativoMng().creaFoglioRiepilogativo(service);

            System.out.println("Foglio riepilogativo creato relativo al servizio: " + s.getServizio());
            System.out.println("Owner: " + s.getProprietario());
            System.out.println("Compiti:");
            for (Compito compito : s.getCompiti()) {
                System.out.println(compito.getNome());
            }

            Mansione saintHonore = new Mansione(
                    "Torta Saint Honoré",
                    "Classica torta francese intitolata al patrono dei panettieri.",
                    5.0,
                    5
            ) {
            };
            saintHonore.setId(19);

            System.out.println("\nTEST ADDJOB");
            CatERing.getInstance().getFoglioRiepilogativoMng().aggiungiCompito(saintHonore, saintHonore.getTempo(), true);

            System.out.println("\nTEST MODIFIER JOB");

            CatERing.getInstance().getFoglioRiepilogativoMng().modificaCompito(s.getCompiti().get(0).getId(), 2.0, true, false, 3, 3, 3);
            System.out.println("Cuoco assegnato: " + s.getCompiti().get(0).getCuoco().getName());


        } catch (UseCaseLogicException ex) {
            System.out.println("Errore di logica nello use case");
        }
    }
}
