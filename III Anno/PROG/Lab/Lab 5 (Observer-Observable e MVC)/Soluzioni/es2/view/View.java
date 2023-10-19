package es2.view;

import es2.controller.Controller;
import es2.model.Model;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class View extends JFrame implements Observer {

    private final JButton button;
    private final JLabel label;

    public View(String name) {
        super(name);
        setLayout(new FlowLayout(FlowLayout.LEFT, 20, 40));

        button = new JButton("Chiedi un proverbio");
        label = new JLabel("");
        add(button);
        add(label);

        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void setListener(Controller c) {
        button.addActionListener(c);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Model) {
            label.setText(((Model) o).getProverb());
        }
    }
}
