package com.backend;

import com.frontend.MainQuizController;

public class Enumeration extends QuizQuestion {

        private final MainQuizController controller;
        private final int points;
        private final String[] correctAnswers;
        private final int numOfAcceptedAns;
        private Boolean isAnswered;


    public Enumeration (String questionText, String[] correctAnswers, MainQuizController controller, Boolean isAnswered, int numOfAcceptedAns, int points) {
            super(questionText, controller);
            this.correctAnswers =  correctAnswers;
            this.points = points;
            this.controller = controller;
            this.isAnswered = isAnswered;
            this.numOfAcceptedAns = numOfAcceptedAns;
        }

        public void enumLimiter(){

            controller.essay_field.textProperty().addListener((observable, oldValue, newValue) -> {
                long count = newValue.chars().filter(ch -> ch == ',').count();

                // If the character appears more than the limit, revert to the old value
                if (count > numOfAcceptedAns-1) {
                    controller.essay_field.setText(oldValue);
                }
            });
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
                enumLimiter();
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
            int pointPerAns= points/ correctAnswers.length;
            int score = 0;

            String answer = controller.answers.get(controller.currentQuestionIndex);
            answer = answer.toLowerCase();

            String[] answersDivided = answer.split(",");

            for (String s : answersDivided) {
                for (int j = 0; j < correctAnswers.length; j++) {
                    if (s.equalsIgnoreCase(correctAnswers[j])) {
                        score += pointPerAns;
                        correctAnswers[j] = ".....";
                    }
                }
            }

            return score;
        }



}
