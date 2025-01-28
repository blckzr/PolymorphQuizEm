package com.backend;

import com.frontend.MainQuizController;

public class TrueOrFalse extends QuizQuestion {
    private final MainQuizController controller;
    private final int points;
    private final String correctAnswer;
    private Boolean isAnswered;


    public TrueOrFalse(String questionText, String correctAnswer, MainQuizController controller, Boolean isAnswered, int points) {
        super(questionText, controller);
        this.correctAnswer = correctAnswer;
        this.points = points;
        this.controller = controller;
        this.isAnswered = isAnswered;
    }

    @Override
    public void displayInputs(){
        controller.choice1.setVisible(true);
        controller.choice2.setVisible(true);
        controller.choice1.setDisable(false);
        controller.choice2.setDisable(false);

        controller.choice1.setText("True");
        controller.choice2.setText("False");
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
            controller.Choices.selectToggle(null);
        } else {
            displayInputs();

            if (controller.answers.get(controller.currentQuestionIndex).equals("True")) {
                controller.choice1.setSelected(true);
            } else if (controller.answers.get(controller.currentQuestionIndex).equals(("False"))) {
                controller.choice2.setSelected(true);
            }

        }
    }

    @Override
    public void storeAnswer() {
        if(controller.Choices.getSelectedToggle()!=null)
            isAnswered =true;

        if (controller.answers.size() - 1 < controller.currentQuestionIndex) {
            if (controller.choice1.isSelected()) {
                controller.answers.add("True");
                System.out.println("True selected");
            } else if (controller.choice2.isSelected()) {
                controller.answers.add("False");
                System.out.println("False selected");
            }
        }else{
            if (controller.choice1.isSelected()) {
                controller.answers.set(controller.currentQuestionIndex,"True");
            } else if (controller.choice2.isSelected()) {
                controller.answers.set(controller.currentQuestionIndex,"False");
            }
        }
    }
    @Override
    public void toggleNextButton(){
        controller.Choices.selectedToggleProperty().addListener((observable, oldToggle, newToggle) -> {
            // Check if any radio button is selected
            if (controller.Choices.getSelectedToggle() == null) {
                controller.nextButton.setDisable(true);
                controller.prevButton.setDisable(true);
            } else {
                controller.nextButton.setDisable(false);
                controller.prevButton.setDisable(false);
            }
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

