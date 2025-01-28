
package com.backend;

import com.frontend.MainQuizController;

public class MultipleChoices extends QuizQuestion {
    private final MainQuizController controller;
    private final int points;
    private final String correctAnswer;
    private Boolean isAnswered;
    private final String[] options;

    public MultipleChoices(String questionText, String correctAnswer, String[] options, MainQuizController controller, Boolean isAnswered, int points) {
        super(questionText, controller);
        this.correctAnswer = correctAnswer;
        this.points = points;
        this.options = options;
        this.controller = controller;
        this.isAnswered = isAnswered;
    }

    @Override
    public void displayInputs(){
        controller.choice1.setVisible(true);
        controller.choice2.setVisible(true);
        controller.choice3.setVisible(true);
        controller.choice4.setVisible(true);
        controller.choice1.setDisable(false);
        controller.choice2.setDisable(false);
        controller.choice3.setDisable(false);
        controller.choice4.setDisable(false);

        controller.choice1.setText(options[0]);
        controller.choice2.setText(options[1]);
        controller.choice3.setText(options[2]);
        controller.choice4.setText(options[3]);
    }

    @Override
    public void displayQuestion() {
        for (javafx.scene.Node node : controller.answerPane.getChildren()) {
            node.setVisible(false);
            node.setDisable(true); // Hide each child node
        }
        controller.questionNoLabel.setText("Question No."+(controller.currentQuestionIndex+1));
        controller.ptsLabel.setText("PTS: "+points+"pt/s");
        controller.questionLabel.setText(question);
        

        if (!isAnswered) {

            displayInputs();

            controller.Choices.selectToggle(null);

        } else {

            displayInputs();

            if (controller.answers.get(controller.currentQuestionIndex).equals(options[0])) {
                controller.choice1.setSelected(true);
            } else if (controller.answers.get(controller.currentQuestionIndex).equals((options[1]))) {
                controller.choice2.setSelected(true);
            } else if (controller.answers.get(controller.currentQuestionIndex).equals((options[2]))) {
                controller.choice3.setSelected(true);
            } else if (controller.answers.get(controller.currentQuestionIndex).equals((options[3]))) {
                controller.choice4.setSelected(true);
            }

        }
    }

    @Override
    public void storeAnswer() {
        if(controller.Choices.getSelectedToggle()!=null)
            isAnswered =true;

        if (controller.answers.size() - 1 < controller.currentQuestionIndex) {
            if (controller.choice1.isSelected()) {
                controller.answers.add(options[0]);
                System.out.println("Choice 1 selected");
            } else if (controller.choice2.isSelected()) {
                controller.answers.add(options[1]);
                System.out.println("Choice 2 selected");
            } else if (controller.choice3.isSelected()) {
                controller.answers.add(options[2]);
                System.out.println("Choice 3 selected");
            } else if (controller.choice4.isSelected()) {
                controller.answers.add(options[3]);
                System.out.println("Choice 4 selected");
            }
        }else{
            if (controller.choice1.isSelected()) {
                controller.answers.set(controller.currentQuestionIndex,options[0]);
            } else if (controller.choice2.isSelected()) {
                controller.answers.set(controller.currentQuestionIndex,options[1]);
            } else if (controller.choice3.isSelected()) {
                controller.answers.set(controller.currentQuestionIndex,options[2]);
            } else if (controller.choice4.isSelected()) {
                controller.answers.set(controller.currentQuestionIndex,options[3]);
            }
        }
    }
    @Override
    public void toggleNextButton(){
        controller.Choices.selectedToggleProperty().addListener((observable, oldToggle, newToggle) -> {
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
