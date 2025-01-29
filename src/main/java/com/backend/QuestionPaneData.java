package com.backend;

public class QuestionPaneData {
    private String questionType;   // Type of question (e.g., multiple choice, true/false, etc.)
    private String questionText;   // The question itself
    private String correctAnswer;  // The correct answer
    private String[] choices;      // Array of choices for multiple-choice questions
    private int points;            // Points for this question

    // Constructor
    public QuestionPaneData(String questionType, String questionText, String correctAnswer, String[] choices, int points) {
        this.questionType = questionType;
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
        this.choices = choices;
        this.points = points;
    }

    // Getters and Setters
    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String[] getChoices() {
        return choices;
    }

    public void setChoices(String[] choices) {
        this.choices = choices;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}

