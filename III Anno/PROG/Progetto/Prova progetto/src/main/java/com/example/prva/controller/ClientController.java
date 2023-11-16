package com.example.prva.controller;

import com.example.prva.model.*;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class ClientController {

    @FXML
    private VBox Lista_posta_inviata, Lista_posta_ricevuta;

    MailModel model = new MailModel();
    MailCardModel mailCardModel = new MailCardModel();

    public void initModel() {
        Lista_posta_inviata.getChildren().add(mailCardModel.buildCard("Mittente:", "prova_aggiunta_1@gmail.com", "Oggetto Mail","01/01/2024","00:00"));

        Lista_posta_ricevuta.getChildren().add(mailCardModel.buildCard("Destinatario:", "prova_aggiunta_1@gmail.com", "Oggetto Mail","01/01/2024","00:00"));
        Lista_posta_ricevuta.getChildren().add(mailCardModel.buildCard("Destinatario:", "prova_aggiunta_2@gmail.com", "Oggetto Mail","01/01/2024","00:00"));

    }
}