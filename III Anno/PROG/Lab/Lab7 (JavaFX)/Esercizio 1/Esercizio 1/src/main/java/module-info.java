module com.example.esercizio_1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.esercizio_1 to javafx.fxml;
    exports com.example.esercizio_1;
}