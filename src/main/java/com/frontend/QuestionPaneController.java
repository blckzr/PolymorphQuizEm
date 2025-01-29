package com.frontend;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class QuestionPaneController {

    @FXML
    VBox primary_panel;
    @FXML
    VBox secondary_panel;
    @FXML
    public AnchorPane question_pane;
    @FXML
    public ComboBox<String> QuestionType;
    @FXML
    public Label answerLabel;
    @FXML
    public Label pointsLabel;
    @FXML
    public Label questionLabel;
    @FXML
    public Label option1Label;
    @FXML
    public Label option2Label;
    @FXML
    public Label option3Label;
    @FXML
    public Label option4Label;
    @FXML
    public TextField option1TF;
    @FXML
    public TextField option2TF;
    @FXML
    public TextField option3TF;
    @FXML
    public TextField option4TF;
    @FXML
    public TextField correctAnswer_field;
    @FXML
    public TextField QuestionTF;
    @FXML
    public TextField pointsField;
    @FXML
    public RadioButton true_rb;
    @FXML
    public RadioButton false_rb;
    @FXML
    public ToggleGroup TOF;

    String selectedOption;

    @FXML
    private void initialize() {
        String[] qTypes = {"Multiple Choice", "Identification", "Enumeration", "True or False", "Essay"};

        for (String option : qTypes)
            QuestionType.getItems().add(option);


        for (javafx.scene.Node node : secondary_panel.getChildren()) {
            node.setVisible(false);
        }

        removeButton.setVisible(true);
        removeButton.setDisable(false);
        QuestionType.setVisible(true);
        QuestionType.setDisable(false);
        questionLabel.setVisible(false);
        questionLabel.setDisable(true);
        QuestionTF.setVisible(false);
        QuestionTF.setDisable(true);
        pointsLabel.setVisible(false);
        pointsLabel.setDisable(true);
        pointsField.setVisible(false);
        pointsField.setDisable(true);
        true_rb.setVisible(false);
        false_rb.setVisible(false);
        correctAnswer_field.setVisible(false);
        answerLabel.setVisible(false);


        QuestionType.setOnAction(event -> {
            selectedOption = QuestionType.getValue();
            handleComboBoxSelection(selectedOption);
        });
    }

    @FXML
    private Button removeButton;  // The "X" button

    private Runnable removeListener;

    private int index;  // Store the index of the question

    // This method sets the index for the question pane
    public void setIndex(int index) {
        this.index = index;
    }

    // This method retrieves the index
    public int getIndex() {
        return this.index;
    }

    public void setRemoveListener(Runnable removeListener) {
        this.removeListener = removeListener;
        removeButton.setOnAction(event -> removeListener.run());
    }


    public void handleComboBoxSelection(String selectedOption) {

        if (selectedOption != null) {

            questionLabel.setVisible(true);
            questionLabel.setDisable(false);
            QuestionTF.setVisible(true);
            QuestionTF.setDisable(false);
            pointsLabel.setVisible(true);
            pointsLabel.setDisable(false);
            pointsField.setVisible(true);
            pointsField.setDisable(false);
            answerLabel.setVisible(true);

            for (javafx.scene.Node node : secondary_panel.getChildren()) {
                node.setVisible(selectedOption.equals("Multiple Choice"));
            }
            true_rb.setVisible(selectedOption.equals("True or False"));
            false_rb.setVisible(selectedOption.equals("True or False"));
            true_rb.setDisable(!selectedOption.equals("True or False"));
            false_rb.setDisable(!selectedOption.equals("True or False"));
            correctAnswer_field.setVisible(!selectedOption.equals("True or False"));
            correctAnswer_field.setVisible(!selectedOption.equals("True or False"));
            correctAnswer_field.setDisable(selectedOption.equals("True or False"));
            correctAnswer_field.setDisable(selectedOption.equals("True or False"));

        }
    }

    public String getQuestionText() {
        return QuestionTF.getText();
    }

    public int getPoints() {
        try {
            return Integer.parseInt(pointsField.getText());
        } catch (NumberFormatException e) {
            return 0; // Return 0 if the points field is empty or invalid
        }
    }

    public String getCorrectAnswer() {
        if(selectedOption.equals("True or False")){
            if(true_rb.isSelected()){
                return "True";
            }else{
                return "False";
            }
        }
        return correctAnswer_field.getText();  // Return null if no option is selected
    }

    public String[] getChoices() {
        if(selectedOption.equals("Multiple Choice")) {
            return new String[]{option1TF.getText(), option2TF.getText(), option3TF.getText(), option4TF.getText()};
        }else{
            return null;
        }
    }

    public String getQuestionType() {
        return selectedOption;
    }
}