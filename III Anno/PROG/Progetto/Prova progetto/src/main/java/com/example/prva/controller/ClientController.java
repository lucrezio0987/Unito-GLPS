package com.example.prva.controller;

import com.example.prva.model.*;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ClientController {

    @FXML
    private VBox Lista_posta_inviata, Lista_posta_ricevuta;
    @FXML
    private Button Cancella_Tutto_Ricevuta, Cancella_Tutto_Inviata;

    @FXML
    private TextArea textMailSent, textMailReceived, textMailSend;
    @FXML
    private TextField addressMailSent, addressMailRecived, addressMailSend;
    @FXML
    private TextField objectMailSent, objectMailRecived, objectMailSend;
    @FXML
    private Button deleteBtnSent, replyBtnReceived, deleateRecived, sendBtn;
    @FXML
    private ImageView imgEmail;
    @FXML
    private Label addressLabelSent, objectLabelSent, addressLabelReceived, objectLabelReceived;

    MailModel mailModel;
    MailCardModel mailCardModel;

    //TODO: Aggiungere un logo all'apertura della finestra nella sezione "vuota" di posta inviata e ricevuta

    //TODO: Cambiare sfumatura delle card per simulare la mail letta

    //TODO: AGGIUNGERE COMMENTI!!!!!!

    //TODO: Nei metodi di cancellazione della lista completa, deve anche farlo nel server!

    //TODO: Aggiungere possibilità di rispondere e inviare una mail a più destinatari

    //TODO: Aggiungere l'opzione di poter inoltrare una mail

    //TODO: Il cancella tutto deve far apparire il logo

    //TODO: Gestire il contatore delle mail


    public void initModel() {
        mailModel = new MailModel();
        mailCardModel = new MailCardModel(mailModel);

        showMailPanelReceived(false);

        textMailSent.textProperty().bind(mailModel.getTextMailSentProperty());
        addressMailSent.textProperty().bind(mailModel.getAddressMailSentProperty());
        objectMailSent.textProperty().bind(mailModel.getObjectMailSentProperty());

        textMailReceived.textProperty().bind(mailModel.getTextMailReceivedProperty());
        addressMailRecived.textProperty().bind(mailModel.getAddressMailReceivedProperty());
        objectMailRecived.textProperty().bind(mailModel.getObjectMailReceivedProperty());

        mailModel.getTextMailSendProperty().bindBidirectional(textMailSend.textProperty());
        mailModel.getAddressMailSendProperty().bindBidirectional(addressMailSend.textProperty());
        mailModel.getObjectMailSendProperty().bindBidirectional(objectMailSend.textProperty());

        mailModel.getListMailSent().forEach((mail) -> {
                    VBox card = mailCardModel.buildCard("Destinatario:", mail);
                    Lista_posta_inviata.getChildren().add(card);
                });

        mailModel.getListMailReceived().forEach((mail) -> {
                    VBox card = mailCardModel.buildCard("Mittente:",mail);
                    Lista_posta_ricevuta.getChildren().add(card);
                });

        Cancella_Tutto_Inviata.setOnAction(event -> {
            System.out.println("MailReceivedList: Prima della cancellazione ( " + mailModel.getListMailReceived().toString() + " )");
            mailModel.deleteMailSentList();
            Lista_posta_inviata.getChildren().clear();
            System.out.println("MailSentList: Cancellata ( " + mailModel.getListMailSent().toString() + " )\n");
        });

        Cancella_Tutto_Ricevuta.setOnAction(event -> {
            System.out.println("MailReceivedList: Prima della cancellazione ( " + mailModel.getListMailReceived().toString() + " )");
            mailModel.deleteMailReceivedList();
            Lista_posta_ricevuta.getChildren().clear();
            showMailPanelReceived(false);
            System.out.println("MailReceivedList: Cancellata ( " + mailModel.getListMailReceived().toString() + " )\n");
        });

        sendBtn.setOnAction(event -> {
            Mail mail = mailModel.sendMail();
            VBox card = mailCardModel.buildCard("Destinatario:",mail);
            Lista_posta_inviata.getChildren().add(card);
        });

        deleteBtnSent.setOnAction(event -> {
            String idMail = mailModel.deleteActualMailSent();
            Lista_posta_inviata.getChildren().removeIf(card -> card.getId().equals(idMail));
        });

        deleateRecived.setOnAction(event -> {
            String idMail = mailModel.deleteActualMailReceived();
            Lista_posta_ricevuta.getChildren().removeIf(card -> card.getId().equals(idMail));
            showMailPanelReceived(false);
        });

        replyBtnReceived.setDisable(true);
    }

    private void showMailPanelReceived(boolean bool) {
        if(bool) {
            imgEmail.setVisible(false);

            textMailReceived.setVisible(true);
            addressMailRecived.setVisible(true);
            objectMailRecived.setVisible(true);

            addressLabelSent.setVisible(true);
            objectLabelSent.setVisible(true);
            addressLabelReceived.setVisible(true);
            objectLabelReceived.setVisible(true);
            replyBtnReceived.setVisible(true);
            deleateRecived.setVisible(true);
        } else {
            imgEmail.setVisible(true);

            textMailReceived.setVisible(false);
            addressMailRecived.setVisible(false);
            objectMailRecived.setVisible(false);

            addressLabelSent.setVisible(false);
            objectLabelSent.setVisible(false);
            addressLabelReceived.setVisible(false);
            objectLabelReceived.setVisible(false);
            replyBtnReceived.setVisible(false);
            deleateRecived.setVisible(false);
        }
    }

    public class MailCardModel {

        private final MailModel mailModel;

        public MailCardModel(MailModel mailModel) { this.mailModel = mailModel;}

        public VBox buildCard(String soggetto, Mail mail) {
            VBox vbox = new VBox();
            vbox.getStyleClass().add("class-card-posta");
            vbox.setId(mail.getUuid());

            if(soggetto.equals("Mittente:")){
                vbox.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent e) {
                        mailModel.openMailReceived(mail.getUuid());
                        showMailPanelReceived(true);
                    }
                });
            }
            if(soggetto.equals("Destinatario:")){
                vbox.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent e) { mailModel.openMailSent(mail.getUuid());}
                });
            }

            HBox hbox1 = createHBox(soggetto, mail.getAddress());
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

        private static Button createButtonX() {
            // Crea il pulsante "X"
            Button button = new Button("X");
            button.setPrefHeight(28.0);
            button.setPrefWidth(66.0);
            button.setMnemonicParsing(false);

            return button;
        }
    }
}