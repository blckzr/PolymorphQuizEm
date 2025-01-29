package com.frontend;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class QuizMaker extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("QuizMaker.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Image icon = new Image(getClass().getResourceAsStream("icons/mainLogo.png"));
        scene.getStylesheets().add(getClass().getResource("QuizMaker.css").toExternalForm());
        QuizMakerController controller = loader.getController();

        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.setTitle("PolymorphQuizEm - Quiz Maker");
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}

