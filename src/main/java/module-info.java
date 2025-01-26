module com.polymorphquizem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.frontend to javafx.fxml;
    exports com.frontend;
}