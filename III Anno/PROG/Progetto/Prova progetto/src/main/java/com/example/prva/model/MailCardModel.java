package com.example.prva.model;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class MailCardModel {

        public VBox buildCard(String soggetto, Mail mail, MailModel mailModel) {
        VBox vbox = new VBox();
        vbox.setPrefHeight(85.0);
        vbox.setPrefWidth(278.0);
        vbox.getStyleClass().add("class-card-posta");
        vbox.setId(mail.getUuid());
        //Button buttonX = createButtonX();
        //buttonX.setId(mail.getUuid() + "_Xbtn");
        //HBox.setMargin(buttonX, new Insets(0, 0, 0, 20));
        if(soggetto.equals("Mittente:")){
            vbox.setOnMouseClicked(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent e) {
                    mailModel.openMailReceived(mail.getUuid());
                }
            });
            //buttonX.setOnAction(event -> mailModel.deleteMailReceived(mail.getUuid()));
        }
        if(soggetto.equals("Destinatario:")){
            vbox.setOnMouseClicked(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent e) { mailModel.openMailSent(mail.getUuid());}
            });
            //buttonX.setOnAction(event -> mailModel.deleteMailSent(mail.getUuid()));
        }

        HBox hbox1 = createHBox(soggetto, mail.getAddress());
        HBox hbox2 = createHBox("Oggetto:", mail.getObject());
        HBox hbox3 = createHBox("Data:", mail.getDate(), "Ora", mail.getTime());

        vbox.getChildren().addAll(hbox1, hbox2, hbox3/*, buttonX*/);

        return vbox;
    }
    private static HBox createHBox(String label1Text, String text1) {   // Crea l'HBox
        HBox hbox = new HBox();
        Label label1 = createLabel(label1Text);
        Text text = new Text(text1);

        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.getChildren().addAll(label1, text);

        return hbox;
    }

    private static HBox createHBox(String label1Text, String text1, String label2Text, String text2) { // Crea l'HBox
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

    private static Label createLabel(String labelText) {        // Crea la Label con il testo specificato
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
