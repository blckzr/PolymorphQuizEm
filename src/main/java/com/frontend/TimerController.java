package com.frontend;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class TimerController {

    private IntegerProperty timeSeconds = new SimpleIntegerProperty(0); // Time in seconds
    private Timeline timeline; // Timeline to update the countdown every second
    private Label timerLabel; // Label to display the countdown

    // Constructor accepts a Label to update
    public TimerController(Label timerLabel) {
        this.timerLabel = timerLabel;
        initialize(); // Initialize the timeline
    }

    // Initialize the Timeline to update every second
    private void initialize() {
        timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> updateCountdown())
        );
        timeline.setCycleCount(Timeline.INDEFINITE); // Infinite cycle to run until manually stopped
    }

    // Method to start the countdown timer
    public void startCountdown(int startTime) {
        timeSeconds.set(startTime); // Set the starting time
        updateLabel(); // Update the label immediately
        timeline.play(); // Start the timer
    }

    // Method to stop the countdown timer
    public void stopCountdown() {
        timeline.stop(); // Stop the timer
    }

    // Update the countdown value and label
    private void updateCountdown() {
        if (timeSeconds.get() > 0) {
            timeSeconds.set(timeSeconds.get() - 1); // Decrement the time by 1 second
            updateLabel(); // Update the label
        } else {
            timeline.stop(); // Stop the timer when the countdown reaches 0
        }
    }

    // Method to update the label with the formatted time (mm:ss)
    private void updateLabel() {
        int minutes = timeSeconds.get() / 60; // Convert seconds to minutes
        int seconds = timeSeconds.get() % 60; // Remaining seconds
        timerLabel.setText(String.format("%02d:%02d", minutes, seconds)); // Update the label
    }
}