package com.example.prva.controller;

import com.example.prva.model.*;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class ClientController {

        @FXML
    private VBox Lista_posta_inviata, Lista_posta_ricevuta;
    @FXML
    private Button Cancella_Tutto_Ricevuta, Cancella_Tutto_Inviata;


    MailModel mailModel = new MailModel();
    MailCardModel mailCardModel = new MailCardModel();

    private Scene scene;

    public void setSccene(Scene scene) {
        this.scene = scene;
    }

    //TODO: Fare un listener per il bottone che sia generico e che possa essere passato a buildCard
    // ( ad esempio che faccia rimuova la mail che ha id = [nome bottone senza _Xbtn] )

    public void initModel() {
        mailModel.getListMailSent().forEach((mail) -> {
                    VBox card = mailCardModel.buildCard("Mittente:", mail);
                    Lista_posta_inviata.getChildren().add(card);
                    /*
                    Button Xbtn = (Button) scene.lookup("#" + mail.getUuid() + "_Xbtn");
                    if (Xbtn != null)
                        Xbtn.setOnAction(event -> {
                            mailModel.deleteMailSent(mail.getUuid());
                            System.out.println("mail rimossa: "+ mail.toString());
                        });
                     */
                });
        mailModel.getListMailReceived().forEach((mail) -> {
                    VBox card = mailCardModel.buildCard("Destinatario:",mail);
                    Lista_posta_ricevuta.getChildren().add(card);
                    /*
                    Button Xbtn = (Button) scene.lookup("#" + mail.getUuid() + "_Xbtn");
                    if (Xbtn != null)
                        Xbtn.setOnAction(event -> {
                            mailModel.deleteMailReceived(mail.getUuid());
                            System.out.println("mail rimossa: "+ mail.toString());
                        });
                     */
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
            System.out.println("MailReceivedList: Cancellata ( " + mailModel.getListMailReceived().toString() + " )\n");
        });


    }
}