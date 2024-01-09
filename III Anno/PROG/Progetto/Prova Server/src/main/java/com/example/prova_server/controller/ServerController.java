package com.example.prova_server.controller;

import com.example.prova_server.model.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Map;

public class ServerController {

    ServerModel model;
    @FXML
    private TextArea textArea;
    @FXML
    private Button stopBtn, resetBtn, startBtn, clearBackupButton;
    @FXML
    private Label countLabel;
    @FXML
    public TextField serverHostLabel;
    @FXML
    private TableView<TableRowData>         clientTable;
    @FXML
    private TableColumn<TableRowData, String> usernameColumn, addressColumn, isOnColumn, sendColumn, receivedColumn;


    public ServerController() {
    }


    public void initModel() {
        model = new ServerModel();

        textArea.textProperty().bind(model.getTextAreaProperty());
        countLabel.textProperty().bind(model.getCountProperty());
        model.getServeHostTextProperty().bindBidirectional(serverHostLabel.textProperty());

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

        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        isOnColumn.setCellValueFactory(new PropertyValueFactory<>("isOn"));
        sendColumn.setCellValueFactory(new PropertyValueFactory<>("Send"));
        receivedColumn.setCellValueFactory(new PropertyValueFactory<>("Received"));

        textArea.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                addAllRowToTable();
            }
        });

    }

    public void termModel() {
        model.stop();
    }

    public void addAllRowToTable() {
        clearTable();
        model.getClientMap().forEach((key, value) -> {
            clientTable.getItems().add(new TableRowData(
                    key,
                    value.getClientAddress(),
                    String.valueOf(value.isOn()),
                    String.valueOf(value.getMailSent().size()),
                    String.valueOf(value.getMailReceived().size())));
        });
    }

    public void clearTable() {
        clientTable.getItems().clear();
    }

    public static class TableRowData {
        private String username;
        private String address;
        private String isOn;
        private String send;
        private String received;

        public TableRowData(String username, String address, String isOn, String send, String received) {
            this.username = username;
            this.address = address;
            this.isOn = isOn;
            this.send = send;
            this.received = received;
        }
        public String getUsername() {
            return username;
        }
        public String getAddress() {
            return address;
        }
        public String getIsOn() {
            return isOn;
        }
        public String getSend() {
            return send;
        }
        public String getReceived() {
            return received;
        }
    }
}