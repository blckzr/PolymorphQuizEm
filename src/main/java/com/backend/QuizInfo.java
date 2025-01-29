package com.backend;

public class QuizInfo {
    private String title;
    private String mode;
    private String time;
    private String dueDate;
    private int totalPoints;

    public QuizInfo(String title, String mode, String time, String dueDate, int totalPoints){
        this.title =  title;
        this.mode= mode;
        this.time= time;
        this.dueDate=dueDate;
        this.totalPoints = totalPoints;
    }

    public int getTimer(){
        return Integer.parseInt(time);
    }

    public String getMode(){
        return mode;
    }

    public String getTitle(){
        return title;
    }

    public int getTotalPoints(){
        return totalPoints;
    }
}