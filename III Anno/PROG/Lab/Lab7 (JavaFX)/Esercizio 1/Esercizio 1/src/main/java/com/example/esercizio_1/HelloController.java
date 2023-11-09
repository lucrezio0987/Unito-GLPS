package com.example.esercizio_1;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TextField;


public class HelloController {
    @FXML
    private TextField testoEsercizio;
    @FXML
    private Label verificaRisposta;
    DataModel model = new DataModel();

    @FXML
    protected void OnClickAltroEsercizio() {
        model.setEsercizio();
        testoEsercizio.textProperty().setValue(model.getTestoEsercizioProperty().getValue());

    }
    @FXML
    protected void OnClickVerifica() {

        String str;
        if(model.checkRisposta())   str="Risposta Corretta";
        else                        str="ERRORE";
        verificaRisposta.setText(str);
    }
}