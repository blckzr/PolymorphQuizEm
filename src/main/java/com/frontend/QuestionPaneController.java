package com.frontend;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;

public class QuestionPaneController {

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

    @FXML
    private void initialize(){
        String[] qTypes = {"Multiple Choice","Identification","Enumeration","True or False","Essay"};

        for(String option: qTypes)
            QuestionType.getItems().add(option);

        for (javafx.scene.Node node : question_pane.getChildren()) {
            node.setVisible(false);
            node.setDisable(true); // Hide each child node
        }

        QuestionType.setVisible(true);
        QuestionType.setDisable(false);

        QuestionType.setOnAction(event -> {
            String selectedOption = QuestionType.getValue();
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

    private void displayPrimary(){
        QuestionType.setDisable(false);
        QuestionType.setVisible(true);
        questionLabel.setVisible(true);
        QuestionTF.setVisible(true);
        answerLabel.setVisible(true);
        pointsLabel.setVisible(true);
        pointsField.setVisible(true);
        removeButton.setVisible(true);
        questionLabel.setDisable(false);
        QuestionTF.setDisable(false);
        answerLabel.setDisable(false);
        pointsLabel.setDisable(false);
        pointsField.setDisable(false);
        removeButton.setDisable(false);
        option1Label.setVisible(true);
        option2Label.setVisible(true);
        option3Label.setVisible(true);
        option4Label.setVisible(true);
        option1Label.setDisable(false);
        option2Label.setDisable(false);
        option3Label.setDisable(false);
        option4Label.setDisable(false);
        option1TF.setVisible(true);
        option2TF.setVisible(true);
        option3TF.setVisible(true);
        option4TF.setVisible(true);
        correctAnswer_field.setVisible(true);
        correctAnswer_field.setDisable(false);
        true_rb.setVisible(true);;
        false_rb.setVisible(true);
    }

    public void DisableAll(){
        for (javafx.scene.Node node : question_pane.getChildren()) {
            node.setDisable(true); // Hide each child node
        }
    }

    public void handleComboBoxSelection(String selectedOption) {
        DisableAll();
        displayPrimary();
        if (selectedOption != null) {
            if(selectedOption.equals("True or False")){
                correctAnswer_field.setVisible(false);
                true_rb.setDisable(false);
                false_rb.setDisable(false);
            }else if(selectedOption.equals("Multiple Choice")) {
                correctAnswer_field.setVisible(true);
                correctAnswer_field.setDisable(false);
                option1TF.setDisable(false);
                option2TF.setDisable(false);
                option3TF.setDisable(false);
                option4TF.setDisable(false);
                true_rb.setVisible(false);
                false_rb.setVisible(false);
            }else{
                correctAnswer_field.setVisible(true);
                correctAnswer_field.setDisable(false);
                true_rb.setVisible(false);
                false_rb.setVisible(false);
            }
        }
    }
}