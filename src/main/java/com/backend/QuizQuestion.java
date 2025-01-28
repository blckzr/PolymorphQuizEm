package com.backend;

import  com.frontend.MainQuizController;

public abstract class QuizQuestion {
    private final MainQuizController controller;
    protected String question;


    public QuizQuestion(String question, MainQuizController controller) {
        this.question = question;
        this.controller = controller;
    }

    public abstract  void displayInputs();
    public abstract void storeAnswer();
    public abstract void displayQuestion();
    public abstract  void toggleNextButton();
    public abstract double checkAnswer();
}

