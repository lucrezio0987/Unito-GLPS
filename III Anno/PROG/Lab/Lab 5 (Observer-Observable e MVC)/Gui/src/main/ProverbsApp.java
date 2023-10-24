package main;

import controller.Controller;
import model.Model;
import view.View;

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