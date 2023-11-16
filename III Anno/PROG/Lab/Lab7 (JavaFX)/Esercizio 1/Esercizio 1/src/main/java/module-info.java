module com.example.esercizio_1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.esercizio_1 to javafx.fxml;
    exports com.example.esercizio_1;
    exports com.example.esercizio_1.controller;
    opens com.example.esercizio_1.controller to javafx.fxml;
    exports com.example.esercizio_1.model;
    opens com.example.esercizio_1.model to javafx.fxml;
}