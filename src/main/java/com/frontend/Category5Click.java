package com.frontend;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Category5Click extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Category5Click.fxml"));
        Scene scene = new Scene(root);
        Image icon = new Image(getClass().getResourceAsStream("icons/mainLogo.png"));
        scene.getStylesheets().add(getClass().getResource("CategoryClickStyle.css").toExternalForm());

        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.setTitle("PolymorphQuizEm - OOP Quiz - Enumeration");
        stage.show();
    }

    public static void main(String[] args) {launch(args);}
}
