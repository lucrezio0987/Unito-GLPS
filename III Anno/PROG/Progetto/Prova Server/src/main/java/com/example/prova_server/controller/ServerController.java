package com.example.prova_server.controller;

import com.example.prova_server.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.io.IOException;

public class ServerController {

    ServerModel_2 model;
    @FXML
    private TextArea textArea;
    @FXML
    private Button stopBtn, resetBtn, startBtn;
    @FXML
    private Label countLabel;

    public ServerController() {
    }


    public void initModel() {
        model = new ServerModel_2();

        textArea.textProperty().bind(model.getTextAreaProperty());

        model.start();

        System.out.println("Server Avviato");


    }
}