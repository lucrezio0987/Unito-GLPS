package com.example.prova_server.controller;

import com.example.prova_server.model.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.shape.Circle;

import java.util.Map;

public class ServerController {

    ServerModel model;
    @FXML
    private TextArea textArea;
    @FXML
    private Button clearBackupButton, clearBackupLogButton, clearBackupMailButton;
    @FXML
    private Label countLabel;
    @FXML
    public TextField serverHostLabel;
    @FXML
    private TableView<TableRowData>         clientTable;
    @FXML
    private TableColumn<TableRowData, String> usernameColumn, addressColumn, isOnColumn, sendColumn, receivedColumn, sendPortColumn, broadcastPortColumn;

    @FXML
    private Circle ConnectLedConPort, DisconnectLedConPort, ConnectLedMailPort, DisconnectLedMailPort, ConnectLedModPort, DisconnectLedModPort;

    public ServerController() {
    }


    public void initModel() {
        model = new ServerModel();

        textArea.textProperty().bind(model.getTextAreaProperty());
        countLabel.textProperty().bind(model.getCountProperty());
        model.getServeHostTextProperty().bindBidirectional(serverHostLabel.textProperty());

        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("Username"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("Address"));
        isOnColumn.setCellValueFactory(new PropertyValueFactory<>("IsOn"));
        sendColumn.setCellValueFactory(new PropertyValueFactory<>("Send"));
        receivedColumn.setCellValueFactory(new PropertyValueFactory<>("Received"));
        sendPortColumn.setCellValueFactory(new PropertyValueFactory<>("SendPort"));
        broadcastPortColumn.setCellValueFactory(new PropertyValueFactory<>("BroadcastPort"));

        model.start();
        addAllRowToTable();

        clearBackupButton.setOnAction( event -> model.clearAllBackup());
        clearBackupLogButton.setOnAction(event -> model.clearBackupLog());
        clearBackupMailButton.setOnAction(event -> model.clearBackupMail());

        setConnectLedConPort(model.ConnSocketIsOn());
        setConnectLedMailPort(model.MailSocketIsOn());
        setConnectLedModPort(model.ModSocketIsOn());

        textArea.textProperty().addListener((observable, oldValue, newValue) -> addAllRowToTable());

    }

    public void termModel() {
        model.stop();
    }

    public void setConnectLedConPort(Boolean bool) {
        ConnectLedConPort.setVisible(bool);
        DisconnectLedConPort.setVisible(!bool);
    }
    public void setConnectLedMailPort(Boolean bool) {
        ConnectLedMailPort.setVisible(bool);
        DisconnectLedMailPort.setVisible(!bool);
    }

    public void setConnectLedModPort(Boolean bool) {
        ConnectLedModPort.setVisible(bool);
        DisconnectLedModPort.setVisible(!bool);
    }
    public void addAllRowToTable() {
        clearTable();
        model.getClientMap().forEach((username, userData) -> {
            clientTable.getItems().add(new TableRowData(
                    username,
                    userData.getClientAddress(),
                    String.valueOf(userData.getMailPort()),
                    String.valueOf(userData.getBroadcastPort()),
                    String.valueOf(userData.getMailSentNotDelete().size()),
                    String.valueOf(userData.getMailReceivedNotDelete().size()),
                    String.valueOf(userData.isOn())
            ));
        });
    }

    public void clearTable() {
        clientTable.getItems().clear();
    }

    public static class TableRowData {
        private String username;
        private String address;
        private String sendPort;
        private String broadcastPort;
        private String send;
        private String received;
        private String isOn;

        public TableRowData(String username, String address, String sendPort, String broadcastPort, String send, String received, String isOn) {
            this.username = username;
            this.address = address;
            this.sendPort = sendPort;
            this.broadcastPort = broadcastPort;
            this.send = send;
            this.received = received;
            this.isOn = isOn;
        }

        public String getUsername() {
            return username;
        }
        public String getAddress() {
            return address;
        }
        public String getSendPort() {
            return sendPort;
        }
        public String getBroadcastPort() {
            return broadcastPort;
        }
        public String getSend() {
            return send;
        }
        public String getReceived() {
            return received;
        }
        public String getIsOn() {
            return isOn;
        }
    }
}