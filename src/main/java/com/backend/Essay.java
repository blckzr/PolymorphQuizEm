package com.backend;

import com.frontend.MainQuizController;

public class Essay extends QuizQuestion {
    private final MainQuizController controller;
    private final int points;
    private final String[] keywords;
    private Boolean isAnswered;


    public Essay(String questionText, String[] keywords, MainQuizController controller, Boolean isAnswered, int points) {
        super(questionText, controller);
        this.keywords =  keywords;
        this.points = points;
        this.controller = controller;
        this.isAnswered = isAnswered;
    }

    @Override
    public void displayInputs(){
        controller.essay_field.setDisable(false);
        controller.essay_field.setEditable(true);
        controller.essay_field.setVisible(true);

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
            controller.essay_field.setText("");
            controller.nextButton.setDisable(true);
            controller.prevButton.setDisable(true);
            toggleNextButton();
        } else {
            displayInputs();
            controller.essay_field.setText(controller.answers.get(controller.currentQuestionIndex));
        }
    }

    @Override
    public void storeAnswer() {
        if(controller.Choices.getSelectedToggle()!=null)
            isAnswered =true;

        if (controller.answers.size() - 1 < controller.currentQuestionIndex) {
            controller.answers.add(controller.essay_field.getText());
        }else{
            controller.answers.set(controller.currentQuestionIndex,controller.essay_field.getText());
        }
    }

    @Override
    public void toggleNextButton(){
        controller.essay_field.textProperty().addListener((observable, oldValue, newValue) -> {
            controller.nextButton.setDisable(newValue.isEmpty());
            controller.prevButton.setDisable(newValue.isEmpty());
        });
    }

    @Override
    public double checkAnswer(){
        int pointPerAns= points/keywords.length;
        int score = 0;

        String answer = controller.answers.get(controller.currentQuestionIndex);

        answer = answer.toLowerCase();

        for (String keyword : keywords) {
            int index = -1;
            keyword = keyword.toLowerCase();
            index = answer.indexOf(keyword);
            if (index != -1) {
                score += pointPerAns;
            }
        }

        return score;
    }
}


