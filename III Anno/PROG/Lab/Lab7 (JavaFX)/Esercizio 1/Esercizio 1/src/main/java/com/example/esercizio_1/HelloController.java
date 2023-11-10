package com.example.esercizio_1;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

public class HelloController {
    @FXML
    private TextField testoEsercizio, testoRisposta;
    @FXML
    private Label testoVerifica;
    @FXML
    private Button btnAltroEsercizio, btnConferma;

    DataModel model = new DataModel();;

    public void initModel() {

        btnAltroEsercizio.setOnAction(  event -> model.setEsercizio()   );
        btnConferma.setOnAction(        event -> model.checkRisposta()  );

        testoEsercizio.textProperty()   .bind( model.getTestoEsercizioProperty()    );
        testoVerifica.textProperty()    .bindBidirectional( model.getVerificaRispostaProperty()  );
        model.getRispostaProperty()     .bindBidirectional( testoRisposta.textProperty()         );

        model.setEsercizio();
    }
}