package es2.main;

import es2.controller.Controller;
import es2.model.Model;
import es2.view.View;

public class ProverbsApp {

    public static void main(String[] args) {

        Model model = new Model();
        View view = new View("Proverbs App");
        Controller controller = new Controller(model);

        view.setListener(controller);
        model.addObserver(view);
        model.chooseProverb();
    }
}
