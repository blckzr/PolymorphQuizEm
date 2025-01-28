package com.frontend;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QuizMakerController {
    @FXML
    VBox QuestionVbox;
    @FXML
    RadioButton reviewerMode;
    @FXML
    RadioButton profMode;
    @FXML
    ToggleGroup Modes;
    @FXML
    Label DueLabel;
    @FXML
    DatePicker datePicker;
    @FXML
    Label setTimeLabel;
    @FXML
    Label minsLabel;
    @FXML
    TextField timerTF;

    private List<Parent> questionPanes = new ArrayList<>();
    private int nextIndex = 0;

    @FXML
    private void initialize() {
        DueLabel.setDisable(true);
        datePicker.setDisable(true);
        DueLabel.setVisible(false);
        datePicker.setVisible(false);
        setTimeLabel.setDisable(true);
        timerTF.setDisable(true);
        minsLabel.setDisable(true);
        setTimeLabel.setVisible(false);
        timerTF.setVisible(false);
        minsLabel.setVisible(false);

        Integer[] monthsOption = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
    }

    @FXML
    private void handleReviewMode() {
        DueLabel.setDisable(true);
        datePicker.setDisable(true);
        datePicker.setVisible(false);
        DueLabel.setVisible(false);
    }

    @FXML
    private void handleProfMode() {
        DueLabel.setDisable(false);
        datePicker.setDisable(false);
        datePicker.setVisible(true);
        DueLabel.setVisible(true);
    }


    @FXML
    private void addQuestion() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("QuestionPane.fxml"));
        Parent questionPane = loader.load();  // Load the question pane

        QuestionPaneController controller = loader.getController();
        if (!QuestionVbox.getChildren().contains(questionPane)) {
            controller.setIndex(nextIndex);
            controller.setRemoveListener(() -> removeQuestionPane(questionPane));

            // Add the new question pane
            QuestionVbox.getChildren().add(questionPane);
            nextIndex++; // Increment the index
        }

    }

    @FXML
    private void removeQuestionPane(Parent questionPane) {
        // Remove the question pane from the container
        QuestionVbox.getChildren().remove(questionPane);
        questionPanes.remove(questionPane);
        reassignIndices();
    }

    public void handleCheckbox(ActionEvent actionEvent) {
        setTimeLabel.setDisable(false);
        timerTF.setDisable(false);
        minsLabel.setDisable(false);
        setTimeLabel.setVisible(true);
        timerTF.setVisible(true);
        minsLabel.setVisible(true);
    }

    private void reassignIndices() {

        for (int i = 0; i < QuestionVbox.getChildren().size(); i++) {
            Parent questionPane = (Parent) QuestionVbox.getChildren().get(i);
            // Ensure the controller is correctly retrieved from the HBox
            QuestionPaneController controller = (QuestionPaneController) questionPane.getUserData();
            if (controller != null) {
                controller.setIndex(i);
            }
        }
    }
}
