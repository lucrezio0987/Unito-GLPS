package view;

import controller.Controller;
import model.Model;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class View extends JFrame implements Observer {
    JButton button;
    JLabel label;

    public View(String name){
        super(name);
        button = new JButton("Chiedimi un proverbio:");
        label = new JLabel();

        setLayout(new FlowLayout(FlowLayout.LEFT, 20, 40));
        add(button);
        add(label);

        setSize(500,300);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof Model)
            label.setText(((Model) o).getProverb());
    }

    public void setListener(Controller c) {
        button.addActionListener(c);
    }
}
