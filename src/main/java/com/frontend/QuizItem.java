package com.frontend;

public class QuizItem {
    private String title;
    private String difficulty;
    private String duration;
    private String itemCount;
    private String imagePath;

    public QuizItem(String title, String difficulty, String duration, String itemCount, String imagePath) {
        this.title = title;
        this.difficulty = difficulty;
        this.duration = duration;
        this.itemCount = itemCount;
        this.imagePath = imagePath;
    }

    public String getTitle() {
        return title;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getDuration() {
        return duration;
    }

    public String getItemCount() {
        return itemCount;
    }

    public String getImagePath() {
        return imagePath;
    }
}