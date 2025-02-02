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
import catering.businesslogic.user.User;

import java.util.ArrayList;

public class TestFoglioRiepilogativo3_3a {
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

            ArrayList<Preparazione> preparations = new ArrayList<>();

            Mansione millefoglie = new Mansione(
                    "Torta Millefoglie",
                    "Dolce composto da strati di pasta sfoglia e crema pasticcera.",
                    4.5,
                    4
            ) {};
            millefoglie.setId(20);
            CatERing.getInstance().getFoglioRiepilogativoMng().aggiungiCompito(millefoglie, 1.5, false);

            Mansione profiteroles = new Mansione(
                    "Profiteroles",
                    "Dolce francese con bignè ripieni di crema e coperti di cioccolato.",
                    5.0,
                    5
            ) {};
            profiteroles.setId(21);
            CatERing.getInstance().getFoglioRiepilogativoMng().aggiungiCompito(profiteroles, 2.5, true);

            Mansione tiramisu = new Mansione(
                    "Tiramisù",
                    "Classico dolce italiano con savoiardi, mascarpone e caffè.",
                    4.8,
                    4
            ) {};
            tiramisu.setId(22);
            CatERing.getInstance().getFoglioRiepilogativoMng().aggiungiCompito(tiramisu, 2.0, false);

            Mansione cheesecake = new Mansione(
                    "Cheesecake ai frutti di bosco",
                    "Torta con base di biscotto e crema al formaggio, guarnita con frutti di bosco.",
                    4.7,
                    4
            ) {};
            cheesecake.setId(23);
            CatERing.getInstance().getFoglioRiepilogativoMng().aggiungiCompito(cheesecake, 1.8, true);

            Mansione cassata = new Mansione(
                    "Cassata Siciliana",
                    "Dolce tipico siciliano con ricotta, pan di Spagna e frutta candita.",
                    4.9,
                    5
            ) {};
            cassata.setId(24);
            CatERing.getInstance().getFoglioRiepilogativoMng().aggiungiCompito(cassata, 2.2, false);

            for (Compito c : s.getCompiti()) {
                CatERing.getInstance().getFoglioRiepilogativoMng().modificaCompito(c.getId(), 2.0, true, false,
                        (int) (Math.random() * 10) + 1, (int) (Math.random() * 10) + 1, 3);
            }

            System.out.println("Foglio riepilogativo con compito aggiunto: " + s.getServizio());
            System.out.println("Owner: " + s.getProprietario());
            System.out.println("Compiti:");
            for (Compito job : s.getCompiti()) {
                System.out.println(job.getNome());
            }

            System.out.println("\nCompiti ordinati per importanza:\n");
            ArrayList<Compito> sortedJobs_i = CatERing.getInstance().getFoglioRiepilogativoMng().ordinaCompitiImportanza(1);
            for (Compito job: sortedJobs_i) {
                if (job.getImportanza() > 0)
                    System.out.println("job: " + job.getNome() + ", importanza: " + job.getImportanza());
            }

            System.out.println("\nCompiti ordinati per difficoltà:\n");
            ArrayList<Compito> sortedJobs_d = CatERing.getInstance().getFoglioRiepilogativoMng().ordinaCompitiDifficolta(1);
            for (Compito job: sortedJobs_d) {
                if (job.getImportanza() > 0)
                    System.out.println("job: " + job.getNome() + ", difficoltà: " + job.getDifficolta());
            }


        } catch (UseCaseLogicException ex) {
            System.out.println("Errore di logica nello use case");
        }
    }
}
