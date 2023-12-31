package com.example.prova_server;

import com.example.prova_server.controller.ServerController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ServerApplication extends Application {

    ServerController contr;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ServerApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Server di Posta Elettronica");
        stage.setScene(scene);
        stage.show();

        contr = fxmlLoader.getController();
        contr.initModel();
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