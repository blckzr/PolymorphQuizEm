module com.polymorphquizem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;


    opens com.frontend to javafx.fxml;
    exports com.frontend;
}