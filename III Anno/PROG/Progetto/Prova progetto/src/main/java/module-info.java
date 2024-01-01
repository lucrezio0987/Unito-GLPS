module com.example.prva {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires org.apache.commons.csv;

    opens com.example.prva to javafx.fxml;
    exports com.example.prva;
    exports com.example.prva.controller;
    opens com.example.prva.controller to javafx.fxml;

    opens com.example.prva.model to com.google.gson;

}