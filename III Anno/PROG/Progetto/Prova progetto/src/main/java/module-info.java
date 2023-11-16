module com.example.prva {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.prva to javafx.fxml;
    exports com.example.prva;
    exports com.example.prva.controller;
    opens com.example.prva.controller to javafx.fxml;
}