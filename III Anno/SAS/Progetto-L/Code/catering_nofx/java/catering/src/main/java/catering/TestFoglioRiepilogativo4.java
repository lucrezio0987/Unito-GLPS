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
import catering.businesslogic.recipe.Preparazione;
import catering.businesslogic.recipe.Recipe;
import catering.businesslogic.tableManagement.Cuoco;
import catering.businesslogic.tableManagement.Turno;
import catering.businesslogic.user.User;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

public class TestFoglioRiepilogativo4 {
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

            ArrayList<Cuoco> cooks = new ArrayList<>();

            // Aggiunta dei cuochi alla lista
            cooks.add(new Cuoco( "Mario Rossi"));
            cooks.add(new Cuoco("Luigi Bianchi"));
            cooks.add(new Cuoco("Giuseppe Verdi"));
            cooks.add(new Cuoco("Anna Neri"));

            int i = 1;
            for(Cuoco c : cooks) {
                c.setId(i);
                i++;
            }

            Turno shift = new Turno(
                    Date.valueOf("2024-07-15"),
                    Time.valueOf("09:00:00"),
                    2.0
            );
            shift.setCuochi(cooks);
            shift.setId(1);

            ArrayList<Preparazione> preparations = new ArrayList<>();

            Mansione saintHonore = new Mansione(
                    "Torta Saint Honoré",
                    "Classica torta francese intitolata al patrono dei panettieri.",
                    5.0,
                    5
            ) {
            };
            saintHonore.setId(19);

            CatERing.getInstance().getFoglioRiepilogativoMng().aggiungiCompito(saintHonore, 2.0, true);

            System.out.println("\nTEST SET SHIFT");
            CatERing.getInstance().getFoglioRiepilogativoMng().impostaTurno(s.getCompiti().get(0).getId(), shift);
            System.out.println("Turno assegnato: " + s.getCompiti().get(0).getTurno().getId());


        } catch (UseCaseLogicException ex) {
            System.out.println("Errore di logica nello use case");
        }
    }
}
