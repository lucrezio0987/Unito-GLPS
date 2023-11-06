module com.example.javafxml0 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.javafxml0 to javafx.fxml;
    exports com.example.javafxml0;
}