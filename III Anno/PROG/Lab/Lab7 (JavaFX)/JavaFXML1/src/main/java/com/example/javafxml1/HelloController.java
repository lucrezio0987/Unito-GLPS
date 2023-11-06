package com.example.javafxml1;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.Random;

public class HelloController {
    @FXML
    private Label welcomeText;

    private Random r = new Random();

    @FXML
    private Label label;

    @FXML
    protected void onHelloButtonClick() {

        welcomeText.setText("Welcome to JavaFX Application!");
        label.setText("Hello World!");
        int n = r.nextInt();
        String s = String.format("%d", n);
        label.setText(s);
    }
}