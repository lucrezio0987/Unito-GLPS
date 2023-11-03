package Views;

import Controller.Controller;
import Model.Model;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class View  extends JFrame  implements Observer {
    JButton b1, b2, b3, b4, b5, b6, b7, b8, b9, b0;
    JButton b_piu, b_meno, b_per, b_div, b_open, b_close;
    JButton b_uguale, b_reset;
    JLabel input, result;
    JPanel text_panel, button_panel, controll_panel;

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
        b_open = new JButton("(");
        b_close = new JButton(")");
        b_uguale = new JButton("=");
        b_reset = new JButton("res");

        input = new JLabel();   input.setBorder(BorderFactory.createTitledBorder("Input"));
        result = new JLabel();  result.setBorder(BorderFactory.createTitledBorder("Risultato"));

        text_panel = new JPanel();
        button_panel = new JPanel();
        controll_panel = new JPanel();

        setLayout(new GridLayout(3, 1));
        button_panel.setLayout(new GridLayout(4, 5));
        text_panel.setLayout(new GridLayout(2, 1));
        controll_panel.setLayout(new GridLayout(1, 2));

        add(text_panel);
            text_panel.add(input);
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
            button_panel.add(b_open);
            button_panel.add(b_close);
        add(controll_panel);
            controll_panel.add(b_uguale);
            controll_panel.add(b_reset);


        pack();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void update(Observable observable, Object o) {
        input.setText(((Model) observable).getTextString());
        result.setText(((Model) observable).getTextResutl());
    }

    public void setListener(Controller c) {
        b1.addActionListener(c);
        b2.addActionListener(c);
        b3.addActionListener(c);
        b4.addActionListener(c);
        b5.addActionListener(c);
        b6.addActionListener(c);
        b7.addActionListener(c);
        b8.addActionListener(c);
        b9.addActionListener(c);
        b0.addActionListener(c);
        b_piu.addActionListener(c);
        b_meno.addActionListener(c);
        b_per.addActionListener(c);
        b_div.addActionListener(c);
        b_uguale.addActionListener(c);
        b_reset.addActionListener(c);
        b_open.addActionListener(c);
        b_close.addActionListener(c);
    }
}
