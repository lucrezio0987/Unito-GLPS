module com.example.bindingsnogui2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.bindingsnogui2 to javafx.fxml;
    exports com.example.bindingsnogui2;
}