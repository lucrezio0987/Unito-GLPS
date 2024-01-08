package com.example.prva;

import com.example.prva.controller.ClientController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientApplication extends Application {
    ClientController contr;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("Client-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.setTitle("Client di Posta Elettronica");
        stage.setScene(scene);
        stage.getIcons().add(new Image(ClientApplication.class.getResourceAsStream("email-img.png")));
        stage.show();

        contr = fxmlLoader.getController();
        contr.initModel("client.mail@example.com", "localhost");
    }

    @Override
    public void stop() throws Exception {
        contr.termModel();
        super.stop();
    }

    public static void main(String[] args) {
        launch();
    }

}
