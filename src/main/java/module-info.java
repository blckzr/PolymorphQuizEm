module com.polymorphquizem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;
    requires jdk.compiler;


    opens com.frontend to javafx.fxml;
    exports com.frontend;
    exports database;
    opens database to javafx.fxml;
}