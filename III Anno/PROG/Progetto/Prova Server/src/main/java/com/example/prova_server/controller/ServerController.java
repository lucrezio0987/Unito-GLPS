package com.example.prova_server.controller;

import com.example.prova_server.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class ServerController {

    DataModel model;
    @FXML
    private TextArea textArea;
    @FXML
    private Button stopBtn, resetBtn, startBtn;
    @FXML
    private Label countLabel;

    public ServerController() {
    }


    public void initModel() {
        model = new DataModel();

        textArea.textProperty().bind(model.getTextAreaProperty());

        model.addElement();

        System.out.println("Server Start");


    }
}