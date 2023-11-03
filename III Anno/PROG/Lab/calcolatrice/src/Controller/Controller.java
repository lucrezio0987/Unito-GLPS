package Controller;

import Model.Model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller implements ActionListener {
    private final Model model;

    public Controller(Model model){
        this.model = model;
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        this.model.nonFareNulla();
    }
}
