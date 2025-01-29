package com.frontend;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;


public class QuizItemController {
    @FXML
    private Label difficultyLabel;

    @FXML
    private Label durationLabel;

    @FXML
    private Label itemCountLabel;

    @FXML
    private Label quizTitleLabel;

    @FXML
    private GridPane gridPane;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private ImageView quizImageView;

    public void setQuizTitle(String title) {
        quizTitleLabel.setText(title);
    }

    public void setDifficulty(String difficulty) {
        difficultyLabel.setText(difficulty);
    }

    public void setDuration(String duration) {
        durationLabel.setText(duration);
    }

    public void setItemCount(String itemCount) {
        itemCountLabel.setText(itemCount);
    }

    public void setQuizImage(String imagePath) {
        try {
            Image image = new Image(getClass().getResource(imagePath).toExternalForm());
            quizImageView.setImage(image);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
