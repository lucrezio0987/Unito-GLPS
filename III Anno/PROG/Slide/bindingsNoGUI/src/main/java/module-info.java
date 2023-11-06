module com.example.bindingsnogui {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.bindingsnogui to javafx.fxml;
    exports com.example.bindingsnogui;
}