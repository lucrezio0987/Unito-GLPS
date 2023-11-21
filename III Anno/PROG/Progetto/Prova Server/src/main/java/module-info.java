module com.example.prova_server {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;


    opens com.example.prova_server to javafx.fxml;
    exports com.example.prova_server;
    exports com.example.prova_server.controller;
    opens com.example.prova_server.controller to javafx.fxml;
}