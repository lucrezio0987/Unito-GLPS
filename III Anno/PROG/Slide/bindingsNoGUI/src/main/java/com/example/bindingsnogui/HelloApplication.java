package com.example.bindingsnogui;

import model.BeanDipendente;
import model.BeanSorgente;

public class HelloApplication {
    public static void main(String[] args) {
        BeanSorgente b1 = new BeanSorgente();
        BeanDipendente b2 = new BeanDipendente();
        bindProperties(b1, b2);
        for (int i=0; i<5; i++) {
            b1.setAmount(i);
            System.out.println("Bean sorgente: " + b1.getAmount() +
                               "; Bean dipendente: "+b2.getAmount());
        }
    }

    public static void bindProperties(BeanSorgente b1, BeanDipendente b2) {
        b2.amountProperty().bind(b1.amountProperty());
    }
}