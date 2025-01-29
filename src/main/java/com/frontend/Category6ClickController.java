package com.frontend;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class Category6ClickController {

    @FXML
    private Button startButton;

    @FXML
    private void handleStartQuiz(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("quiz_interface.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Quiz Page");
            stage.show();

            ((Stage) startButton.getScene().getWindow()).close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBackButtonClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("QuizCategories.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Take a Quiz");
            stage.show();

            ((Stage) startButton.getScene().getWindow()).close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
