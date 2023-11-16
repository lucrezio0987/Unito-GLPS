package com.example.esercizio_1;

import com.example.esercizio_1.controller.HelloController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(
                                    HelloApplication.class.getResource("hello-view.fxml")
                                );

        Scene scene = new Scene(fxmlLoader.load());         // pare che la Load vada fatta prima di
              stage.setTitle("Hello!");                     // fare la getController su fxmlLoader
              stage.setScene(scene);
              stage.show();

        HelloController contr = fxmlLoader.getController();
                        contr.initModel();
    }


    public static void main(String[] args) {
        launch();
    }
}