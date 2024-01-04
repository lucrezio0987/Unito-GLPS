package com.example.prova_server.controller;

import com.example.prova_server.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class ServerController {

    ServerModel_2 model;
    @FXML
    private TextArea textArea;
    @FXML
    private Button stopBtn, resetBtn, startBtn, clearBackupButton;
    @FXML
    private Label countLabel;

    public ServerController() {
    }


    public void initModel() {
        model = new ServerModel_2();

        textArea.textProperty().bind(model.getTextAreaProperty());
        countLabel.textProperty().bind(model.getCountProperty());

        model.start();

        clearBackupButton.setOnAction( event -> model.clearBackup());

        startBtn.setOnAction(event -> { model.start();});
        stopBtn.setOnAction(event -> { model.stop();});
        resetBtn.setOnAction(event -> {
            model.stop();
            try {
                wait(1050);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            model.start();
        });

        startBtn.setDisable(true);
        stopBtn.setDisable(true);
        resetBtn.setDisable(true);

    }


    public void termModel() {
        model.stop();
    }
}