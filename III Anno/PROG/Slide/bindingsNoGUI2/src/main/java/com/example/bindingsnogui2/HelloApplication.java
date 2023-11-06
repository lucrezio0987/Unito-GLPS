package com.example.bindingsnogui2;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import model.BeanDipendente;
import model.BeanSorgente;

public class HelloApplication {

    public static void main(String[] args) {
        BeanSorgente b1 = new BeanSorgente();
        BeanDipendente b2 = new BeanDipendente();
        SimpleDoubleProperty property = b1.amountProperty();
        property.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                                Number oldValue, Number newVal) {
                b2.setAmount((Double)newVal);
                System.out.println("b1: " + newVal);
            }
        });
        for (int i=0; i<5; i++) {
            b1.setAmount(i);
            System.out.println("Bean sorgente: " + b1.getAmount() +
                    "; Bean dipendente: "+b2.getAmount());
        }
    }
}