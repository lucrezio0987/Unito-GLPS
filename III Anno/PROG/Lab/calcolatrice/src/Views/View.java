package Views;

import Controller.Controller;
import Model.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class View  extends JFrame  implements Observer {
    JButton b1, b2, b3, b4, b5, b6, b7, b8, b9, b0;
    JButton b_piu, b_meno, b_uguale, b_per, b_div;
    JLabel label, result;
    JPanel text_panel, button_panel;

    public View() {
        super("Calcolatrice");

        b1 = new JButton("1"); b1.setPreferredSize(new Dimension(60, 40));
        b2 = new JButton("2");
        b3 = new JButton("3");
        b4 = new JButton("4");
        b5 = new JButton("5");
        b6 = new JButton("6");
        b7 = new JButton("7");
        b8 = new JButton("8");
        b9 = new JButton("9");
        b0 = new JButton("0");
        b_piu = new JButton("+");
        b_meno = new JButton("-");
        b_per = new JButton("*");
        b_div = new JButton("/");
        b_uguale = new JButton("=");

        label = new JLabel();   label.setBorder(BorderFactory.createTitledBorder("Testo"));
        result = new JLabel();  result.setBorder(BorderFactory.createTitledBorder("Risultato"));

        text_panel = new JPanel();
        button_panel = new JPanel();

        setLayout(new GridLayout(2, 1));
        button_panel.setLayout(new GridLayout(3, 5));
        text_panel.setLayout(new GridLayout(2, 1));

        add(text_panel);
            text_panel.add(label);
            text_panel.add(result);
        add(button_panel);
            button_panel.add(b1);
            button_panel.add(b2);
            button_panel.add(b3);
            button_panel.add(b4);
            button_panel.add(b5);
            button_panel.add(b6);
            button_panel.add(b7);
            button_panel.add(b8);
            button_panel.add(b9);
            button_panel.add(b0);
            button_panel.add(b_piu);
            button_panel.add(b_meno);
            button_panel.add(b_per);
            button_panel.add(b_div);
            button_panel.add(b_uguale);


        pack();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void update(Observable observable, Object o) {
        if(o instanceof Model)
            label.setText(((Model) o).nonScrivereNulla());
    }

    public void setListener(Controller c) {
        b1.addActionListener((ActionListener) c);
    }
}
