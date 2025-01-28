package com.backend;

import com.frontend.MainQuizController;

public class Identification extends QuizQuestion {
    private final MainQuizController controller;
    private final int points;
    private final String correctAnswer;
    private Boolean isAnswered;


    public Identification(String questionText, String correctAnswer, MainQuizController controller, Boolean isAnswered, int points) {
        super(questionText, controller);
        this.correctAnswer = correctAnswer;
        this.points = points;
        this.controller = controller;
        this.isAnswered = isAnswered;
    }

    @Override
    public void displayInputs(){
        controller.identification_field.setDisable(false);
        controller.identification_field.setEditable(true);
        controller.identification_field.setVisible(true);

        controller.text_label.setVisible(true);
        controller.text_label.setDisable(false);
    }

    @Override
    public void displayQuestion() {
        for (javafx.scene.Node node : controller.answerPane.getChildren()) {
            node.setVisible(false);
            node.setDisable(true);
        }

        controller.questionNoLabel.setText("Question No."+(controller.currentQuestionIndex+1));
        controller.ptsLabel.setText("PTS: "+points+"pt/s");
        controller.questionLabel.setText(question);

        if (!isAnswered) {
            displayInputs();
            controller.identification_field.setText("");
            controller.nextButton.setDisable(true);
            controller.prevButton.setDisable(true);
            toggleNextButton();
        } else {
            displayInputs();
            controller.identification_field.setText(controller.answers.get(controller.currentQuestionIndex));
        }
    }

    @Override
    public void storeAnswer() {
        if(controller.Choices.getSelectedToggle()!=null)
            isAnswered =true;

        if (controller.answers.size() - 1 < controller.currentQuestionIndex) {
            controller.answers.add(controller.identification_field.getText());
        }else{
            controller.answers.set(controller.currentQuestionIndex,controller.identification_field.getText());
        }
    }

    @Override
    public void toggleNextButton(){
        controller.identification_field.textProperty().addListener((observable, oldValue, newValue) -> {
            controller.nextButton.setDisable(newValue.isEmpty());
            controller.prevButton.setDisable(newValue.isEmpty());
        });
    }

    @Override
    public double checkAnswer(){
        if(controller.answers.get(controller.currentQuestionIndex).equals(correctAnswer)){
            return points;
        }
        return 0;
    }
}

