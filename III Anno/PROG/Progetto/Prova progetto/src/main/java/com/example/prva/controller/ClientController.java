package com.example.prva.controller;

import com.example.prva.model.*;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.scene.text.*;

import java.util.Map;

public class ClientController {
    @FXML
    private TextField   localAddressReceived, localAddressSent, localAddressSend, localAddressLog,
                        addressMailSent, addressMailRecived, addressMailSend,
                        objectMailSent, objectMailRecived, objectMailSend,
                        ServerHost;
    @FXML
    private Button      Cancella_Tutto_Ricevuta, Cancella_Tutto_Inviata,
                        forwardBtnSent, forwardBtnReceived,sendBtnClear,
                        deleteBtnSent, replyBtnReceived, deleteBtnRecived, sendBtn, replyAllBtnReceived,
                        reconnectBtnSent, reconnectBtnReceived, reconnectBtnSend,
                        connectServerBtnLog, disconnectServerBtnLog, reconnectBtnLog,
                        clearBackupData, clearAllBackup, clearBackupMail, clearBackupLog;
    @FXML
    private Label       addressLabelSent, objectLabelSent, addressLabelReceived, objectLabelReceived,
                        countMailSent, countMailReceived;
    @FXML
    private TextArea    textMailSent, textMailReceived, textMailSend, textLog;
    @FXML
    private ImageView   imgEmailReceived, imgEmailSent;
    @FXML
    private VBox        Lista_posta_inviata, Lista_posta_ricevuta;
    @FXML
    private TabPane     tabPanel;
    @FXML
    private Tab         tabSend;

    @FXML
    private Circle      ConnectLedReceived, ConnectLedSend, ConnectLedSent, ConnectLedLog,
                        DisconnectLedReceived, DisconnectLedSend, DisconnectLedSent, DisconnectLedLog;
    @FXML
    private TableView<TableRowData>         tableLastConnectInfo;
    @FXML
    private TableColumn<TableRowData, String> addressColumn, lastConnectionColumn;

    MailModel mailModel;
    MailCardModel mailCardModel;

    //TODO: AGGIUNGERE COMMENTI!!!!!!

    //TODO: far funzionare i pulsati del server

    // TODO: il client deve inviare al server le modifiche che lui ha fatto mentre era offline

    /*TODO: fare in modo che i client sullo stesso host (quindi se li avvi su stesso pc ad esempio) possano essere distinguibili:
        IDEE:
            1. ho notato che l'oggetto Socket oltre a contenere la portaLocale e l'indirizzo locale contiene anche un altra porta che sembra variaare all'interno della stessa rete
               è possibile che si possa usare quella ma bisogna informarsi
            2. l'alternativa potrebbe essere che il messaggio viene mandato a tutti su quella rete poi i client in ascolto controllano che il messaggio "sia effettivamente destinato a loro"
               ad esempio controllando che il ricevende sia uguale al localAddress, ma sarebbe da evitare per ragioni di sicurezza
            3. non mi piace dato il numero di porte che utilizza anche il client (almeno 2 per ogni client: ricezione mail, ricezione messaggi brodcast dal server),
               ma un altra idea potrebbe essere fare in modo che il client si metta su "una porta libera" (causerebbe più problemi e renderebbe il sistema poco scalabile a parer mio)
     */

    //TODO: mettere una label nel client per modificare eventualemente l'indirizzo del server (nel caso vada runnato da due pc diversi)

    //TODO: il controlo della sintassi deve dare qualche tipo di feedback qualora non andasse a buon fine

    //TODO: salvare e caricare i log in un file

    public void initModel(String localAddressMail, String serverHost) {
        mailModel = new MailModel(this);
        mailCardModel = new MailCardModel(mailModel);

        showMailPanelReceived(false);
        showMailPanelSent(false);

        textMailSent.textProperty().bind(mailModel.getTextMailSentProperty());
        addressMailSent.textProperty().bind(mailModel.getAddressMailSentProperty());
        objectMailSent.textProperty().bind(mailModel.getObjectMailSentProperty());

        textMailReceived.textProperty().bind(mailModel.getTextMailReceivedProperty());
        addressMailRecived.textProperty().bind(mailModel.getAddressMailReceivedProperty());
        objectMailRecived.textProperty().bind(mailModel.getObjectMailReceivedProperty());

        mailModel.getTextMailSendProperty().bindBidirectional(textMailSend.textProperty());
        mailModel.getAddressMailSendProperty().bindBidirectional(addressMailSend.textProperty());
        mailModel.getObjectMailSendProperty().bindBidirectional(objectMailSend.textProperty());

        mailModel.getServeHostProperty().bindBidirectional(ServerHost.textProperty());

        textLog.textProperty().bind(mailModel.getTextLogProperty());

        mailModel.getLocalAddressProperty().bindBidirectional(localAddressReceived.textProperty());
        mailModel.getLocalAddressProperty().bindBidirectional(localAddressSent.textProperty());
        mailModel.getLocalAddressProperty().bindBidirectional(localAddressSend.textProperty());
        mailModel.getLocalAddressProperty().bindBidirectional(localAddressLog.textProperty());

        localAddressSend.textProperty().set(localAddressMail);
        ServerHost.textProperty().set(serverHost);

        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        lastConnectionColumn.setCellValueFactory(new PropertyValueFactory<>("lastConnectionDateTime"));

        setConnection(mailModel.connect());

        Cancella_Tutto_Inviata.setOnAction(event -> {
            deleteMailSent();
        });
        Cancella_Tutto_Ricevuta.setOnAction(event -> {
            deleteMailReceived();
        });

        sendBtn.setOnAction(event -> {
            mailModel.sendMail();
        });
        deleteBtnSent.setOnAction(event -> {
            String idMail = mailModel.deleteActualMailSent();
            Lista_posta_inviata.getChildren().removeIf(card -> card.getId().equals(idMail));
            showMailPanelSent(false);
            setCountMailSent();
        });
        deleteBtnRecived.setOnAction(event -> {
            String idMail = mailModel.deleteActualMailReceived();
            Lista_posta_ricevuta.getChildren().removeIf(card -> card.getId().equals(idMail));
            showMailPanelReceived(false);
            setCountMailReceived();
        });
        sendBtnClear.setOnAction( event -> mailModel.sendMailClear());
        replyBtnReceived.setOnAction(event ->{
            tabPanel.getSelectionModel().select(tabSend);
            mailModel.reply();
        });
        replyAllBtnReceived.setOnAction(event ->{
            tabPanel.getSelectionModel().select(tabSend);
            mailModel.replyAll();
        });
        forwardBtnSent.setOnAction(event ->{
            tabPanel.getSelectionModel().select(tabSend);
            mailModel.forwardSent();
        });
        forwardBtnReceived.setOnAction(event ->{
            tabPanel.getSelectionModel().select(tabSend);
            mailModel.forwardReceived();
        });

        
        reconnectBtnSent.setOnAction(event ->       { clearMail(); setConnection(mailModel.reconnect());});
        reconnectBtnReceived.setOnAction(event ->   { clearMail(); setConnection(mailModel.reconnect());});
        reconnectBtnSend.setOnAction(event ->       { clearMail(); setConnection(mailModel.reconnect());});
        reconnectBtnLog.setOnAction(event ->        { clearMail(); setConnection(mailModel.reconnect());});
        connectServerBtnLog.setOnAction(event ->    { setConnection(mailModel.connect());});
        disconnectServerBtnLog.setOnAction(event -> { setConnection(mailModel.disconnect());});

        clearBackupMail.setOnAction(event ->    mailModel.clearBackupMail());
        clearBackupData.setOnAction(event ->    mailModel.clearBackupData());
        clearAllBackup.setOnAction(event ->     mailModel.clearAllBackup());
        clearBackupLog.setOnAction(event ->     mailModel.clearBackupLog());

    }

    public void setConnection(boolean connect) {
        if(connect){
            ConnectLedReceived.setVisible(true);
            ConnectLedSend.setVisible(true);
            ConnectLedSent.setVisible(true);
            ConnectLedLog.setVisible(true);

            DisconnectLedReceived.setVisible(false);
            DisconnectLedSend.setVisible(false);
            DisconnectLedSent.setVisible(false);
            DisconnectLedLog.setVisible(false);
        } else {
            ConnectLedReceived.setVisible(false);
            ConnectLedSend.setVisible(false);
            ConnectLedSent.setVisible(false);
            ConnectLedLog.setVisible(false);

            DisconnectLedReceived.setVisible(true);
            DisconnectLedSend.setVisible(true);
            DisconnectLedSent.setVisible(true);
            DisconnectLedLog.setVisible(true);

        }
    }

    public void setCountMailSent() { countMailSent.setText(String.valueOf(mailModel.getListMailSent().size())); }
    public void setCountMailReceived() { countMailReceived.setText(String.valueOf(mailModel.getListMailReceived().size())); }

    private void clearMail() {
        deleteMailSent();
        deleteMailReceived();
    }
    private void deleteMailSent(){
        mailModel.deleteMailSentList();
        Lista_posta_inviata.getChildren().clear();
        showMailPanelSent(false);
        setCountMailSent();
    }

    private void deleteMailReceived(){
        mailModel.deleteMailReceivedList();
        Lista_posta_ricevuta.getChildren().clear();
        showMailPanelReceived(false);
        setCountMailReceived();
    }

    public void clearLocalMail() {
        clearLocalMailSent();
        clearLocalMailReceived();
    }
    private void clearLocalMailSent(){
        mailModel.clearLocalMailSentList();
        Lista_posta_inviata.getChildren().clear();
        showMailPanelSent(false);
        setCountMailSent();
    }

    private void clearLocalMailReceived(){
        mailModel.clearLocalMailReceivedList();
        Lista_posta_ricevuta.getChildren().clear();
        showMailPanelReceived(false);
        setCountMailReceived();
    }


    public void showMailPanelReceived(boolean bool) {
        if(bool) {
            imgEmailReceived.setVisible(false);

            textMailReceived.setVisible(true);
            addressMailRecived.setVisible(true);
            objectMailRecived.setVisible(true);
            addressLabelReceived.setVisible(true);
            objectLabelReceived.setVisible(true);
            replyBtnReceived.setVisible(true);
            deleteBtnRecived.setVisible(true);
            replyAllBtnReceived.setVisible(true);

            forwardBtnReceived.setVisible(true);
        } else {
            imgEmailReceived.setVisible(true);

            textMailReceived.setVisible(false);
            addressMailRecived.setVisible(false);
            objectMailRecived.setVisible(false);
            addressLabelReceived.setVisible(false);
            objectLabelReceived.setVisible(false);
            replyBtnReceived.setVisible(false);
            deleteBtnRecived.setVisible(false);
            replyAllBtnReceived.setVisible(false);

            forwardBtnReceived.setVisible(false);
        }
    }
    public void showMailPanelSent(boolean bool) {
        if(bool) {
            imgEmailSent.setVisible(false);

            textMailSent.setVisible(true);
            addressMailSent.setVisible(true);
            objectMailSent.setVisible(true);
            addressLabelSent.setVisible(true);
            objectLabelSent.setVisible(true);
            deleteBtnSent.setVisible(true);
            forwardBtnSent.setVisible(true);

        } else {
            imgEmailSent.setVisible(true);

            textMailSent.setVisible(false);
            addressMailSent.setVisible(false);
            objectMailSent.setVisible(false);
            addressLabelSent.setVisible(false);
            objectLabelSent.setVisible(false);
            deleteBtnSent.setVisible(false);
            forwardBtnSent.setVisible(false);
        }
    }

    public synchronized void createCardReceived(Mail mail){
            VBox card = mailCardModel.buildCard("Mittente:", mail);
            Lista_posta_ricevuta.getChildren().add(0, card);
            setCountMailReceived();
    }
    public synchronized void createCardSent(Mail mail){
        VBox card = mailCardModel.buildCard("Destinatari:", mail);
        Lista_posta_inviata.getChildren().add(0, card);
        setCountMailSent();
    }

    public void termModel() {
        mailModel.stop();
    }


    public class MailCardModel {
        private MailModel mailModel = null;

        public MailCardModel(MailModel mailModel) {
            this.mailModel = mailModel;
        }
        public VBox buildCard(String soggetto, Mail mail) {
            VBox vbox = new VBox();
            vbox.setId(mail.getUuid());
            vbox.getStyleClass().add("class-card-posta");
            if(mail.getRead())
                vbox.getStyleClass().add("read");

            HBox hbox1 = null;

            if(soggetto.equals("Mittente:")){
                vbox.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent e) {
                        mailModel.openMailReceived(mail.getUuid());
                        showMailPanelReceived(true);
                        mailModel.setMailRead(mail.getUuid());
                        ((Node) e.getSource()).getStyleClass().add("read");
                    }
                });
                hbox1 = createHBox(soggetto, mail.getSender());
            } else if(soggetto.equals("Destinatari:")){
                vbox.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent e) {
                        mailModel.openMailSent(mail.getUuid());
                        showMailPanelSent(true);
                        //mailModel.setMailRead(mail.getUuid());
                        //((Node) e.getSource()).getStyleClass().add("read");
                    }
                });
                hbox1 = createHBox(soggetto, mail.getRecipients());
            }
            else System.err.println("ERRORE");

            HBox hbox2 = createHBox("Oggetto:", mail.getObject());
            HBox hbox3 = createHBox("Data:", mail.getDate(), "Ora", mail.getTime());

            vbox.getChildren().addAll(hbox1, hbox2, hbox3);

            return vbox;
        }
        private static HBox createHBox(String label1Text, String text1) {
            HBox hbox = new HBox();
            Label label1 = createLabel(label1Text);
            Text text = new Text(text1);

            hbox.setAlignment(Pos.CENTER_LEFT);
            hbox.getChildren().addAll(label1, text);

            return hbox;
        }
        private static HBox createHBox(String label1Text, String text1, String label2Text, String text2) {
            HBox hbox = new HBox();
            Label label1 = createLabel(label1Text);
            Text text1Node = new Text(text1);
            Label label2 = createLabel(label2Text);
            Text text2Node = new Text(text2);

            hbox.setAlignment(Pos.CENTER_LEFT);
            hbox.getChildren().addAll(label1, text1Node);
            hbox.getChildren().addAll(label2, text2Node);

            return hbox;
        }
        private static Label createLabel(String labelText) {
            Label label = new Label(labelText);
            label.setMaxHeight(Double.MAX_VALUE);
            label.setMaxWidth(Double.MAX_VALUE);
            label.setStyle("-fx-padding:10;");
            label.setUnderline(true);
            label.setFont(new Font(12.0));
            return label;
        }

    }

    public void addAllRowToTable(Map<String, String> map) {
        clearTable();
        map.forEach((key, value) -> {
            tableLastConnectInfo.getItems().add(new TableRowData(key, value));
        });
    }


    public void clearTable() {
        tableLastConnectInfo.getItems().clear();
    }

    public static class TableRowData {
        private String address;
        private String lastConnectionDateTime;

        public TableRowData(String address, String lastConnectionDateTime) {
            this.address = address;
            this.lastConnectionDateTime = lastConnectionDateTime;
        }
        public String getAddress() {
            return address;
        }
        public String getLastConnectionDateTime() {
            return lastConnectionDateTime;
        }
    }
}