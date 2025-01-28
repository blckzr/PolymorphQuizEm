package com.frontend;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

import javax.swing.*;

public class Quiz extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("quiz_interface.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Image icon = new Image(getClass().getResourceAsStream("icons/mainLogo.png"));
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        MainQuizController controller = loader.getController();

        stage.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    System.out.println("Window is in focus");
                } else {
                    System.out.println("Window is out of focus");
                    if(!controller.isDone){
                    JOptionPane.showMessageDialog(null,"Out of Focus Detected","WARNING",JOptionPane.WARNING_MESSAGE);
                    stage.close();}
                }
            }
        });

        stage.setFullScreen(true);;
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.setTitle("PolymorphQuizEm - Quiz");
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        stage.show();

        controller.setStage(stage);

    }

    public static void main(String[] args) {
        launch(args);
    }
}
