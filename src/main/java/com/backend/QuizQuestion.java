package com.backend;

import  com.frontend.MainQuizController;

public abstract class QuizQuestion {
    private final MainQuizController controller;
    protected String question;
    protected String correctAnswer;


    public QuizQuestion(String question, String correctAnswer, MainQuizController controller) {
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.controller = controller;
    }

    public abstract void storeAnswer();
    public abstract void displayQuestion();
    public abstract  void toggleNextButton();
    public abstract boolean checkAnswer();
}

