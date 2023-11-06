module com.example.javafx00 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.javafx00 to javafx.fxml;
    exports com.example.javafx00;
}