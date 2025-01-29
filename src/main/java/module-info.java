module com.polymorphquizem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jdk.compiler;
    requires java.desktop;


    opens com.frontend to javafx.fxml;
    exports com.frontend;
    exports database;
    opens database to javafx.fxml;
}