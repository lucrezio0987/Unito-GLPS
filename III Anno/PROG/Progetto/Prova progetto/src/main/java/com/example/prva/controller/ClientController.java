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
    private Button forwardBtnSent, forwardBtnReceived,sendBtnClear;
    @FXML
    private TextField localAddressReceived, localAddressSent, localAddressSend;

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
    private Button deleteBtnSent, replyBtnReceived, deleteBtnRecived, sendBtn;
    @FXML
    private ImageView imgEmailReceived, imgEmailSent;
    @FXML
    private Label addressLabelSent, objectLabelSent, addressLabelReceived, objectLabelReceived;
    @FXML
    private Label countMailSent, countMailReceived;


    MailModel mailModel;
    MailCardModel mailCardModel;

    //TODO: Cambiare sfumatura delle card per simulare la mail letta

    //TODO: AGGIUNGERE COMMENTI!!!!!!

    //TODO: Aggiungere l'opzione di poter inoltrare una mail


    public void initModel(String localAddressMail) {
        mailModel = new MailModel();
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

        mailModel.getLocalAddressProperty().bindBidirectional(localAddressReceived.textProperty());
        mailModel.getLocalAddressProperty().bindBidirectional(localAddressSent.textProperty());
        mailModel.getLocalAddressProperty().bindBidirectional(localAddressSend.textProperty());

        localAddressSend.textProperty().set(localAddressMail);

        setCountMailSent();
        mailModel.getListMailSent().forEach((mail) -> {
                    VBox card = mailCardModel.buildCard("Destinatari:", mail);
                    Lista_posta_inviata.getChildren().add(card);
                });

        setCountMailReceived();
        mailModel.getListMailReceived().forEach((mail) -> {
                    VBox card = mailCardModel.buildCard("Mittente:",mail);
                    Lista_posta_ricevuta.getChildren().add(card);
                });

        Cancella_Tutto_Inviata.setOnAction(event -> {
            System.out.println("MailReceivedList: Prima della cancellazione ( " + mailModel.getListMailReceived().toString() + " )");
            mailModel.deleteMailSentList();
            Lista_posta_inviata.getChildren().clear();
            showMailPanelSent(false);
            setCountMailSent();
            System.out.println("MailSentList: Cancellata ( " + mailModel.getListMailSent().toString() + " )\n");
        });

        Cancella_Tutto_Ricevuta.setOnAction(event -> {
            System.out.println("MailReceivedList: Prima della cancellazione ( " + mailModel.getListMailReceived().toString() + " )");
            mailModel.deleteMailReceivedList();
            Lista_posta_ricevuta.getChildren().clear();
            showMailPanelReceived(false);
            setCountMailReceived();
            System.out.println("MailReceivedList: Cancellata ( " + mailModel.getListMailReceived().toString() + " )\n");
        });

        sendBtn.setOnAction(event -> {
            Mail mail = mailModel.sendMail();
            VBox card = mailCardModel.buildCard("Destinatari:",mail);
            Lista_posta_inviata.getChildren().add(card);
            setCountMailSent();
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

        sendBtnClear.setOnAction( event -> { mailModel.sendMailClear(); });

        replyBtnReceived.setDisable(true);
    }

    private void setCountMailSent() { countMailSent.setText(String.valueOf(mailModel.getListMailSent().size())); }
    private void setCountMailReceived() { countMailReceived.setText(String.valueOf(mailModel.getListMailReceived().size())); }

    private void showMailPanelReceived(boolean bool) {
        if(bool) {
            imgEmailReceived.setVisible(false);

            textMailReceived.setVisible(true);
            addressMailRecived.setVisible(true);
            objectMailRecived.setVisible(true);
            addressLabelReceived.setVisible(true);
            objectLabelReceived.setVisible(true);
            replyBtnReceived.setVisible(true);
            deleteBtnRecived.setVisible(true);

            forwardBtnReceived.setVisible(false);
        } else {
            imgEmailReceived.setVisible(true);

            textMailReceived.setVisible(false);
            addressMailRecived.setVisible(false);
            objectMailRecived.setVisible(false);
            addressLabelReceived.setVisible(false);
            objectLabelReceived.setVisible(false);
            replyBtnReceived.setVisible(false);
            deleteBtnRecived.setVisible(false);

            forwardBtnReceived.setVisible(false);
        }
    }
    private void showMailPanelSent(boolean bool) {
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

    public class MailCardModel {

        private final MailModel mailModel;

        public MailCardModel(MailModel mailModel) { this.mailModel = mailModel;}

        public VBox buildCard(String soggetto, Mail mail) {
            VBox vbox = new VBox();
            vbox.getStyleClass().add("class-card-posta");
            vbox.setId(mail.getUuid());
            HBox hbox1 = null;

            if(soggetto.equals("Mittente:")){
                vbox.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent e) {
                        mailModel.openMailReceived(mail.getUuid());
                        showMailPanelReceived(true);
                    }
                });
                hbox1 = createHBox(soggetto, mail.getSender());
            } else if(soggetto.equals("Destinatari:")){
                vbox.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent e) {
                        mailModel.openMailSent(mail.getUuid());
                        showMailPanelSent(true);}
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