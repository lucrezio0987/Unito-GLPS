module com.example.javafxml1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.javafxml1 to javafx.fxml;
    exports com.example.javafxml1;
}