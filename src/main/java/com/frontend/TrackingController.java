package com.frontend;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class TrackingController implements Initializable {
    @FXML
    private Label month, year;

    @FXML
    private FlowPane calendar;

    @FXML
    private TextField taskTitleField;

    @FXML
    private DatePicker taskDatePicker;

    @FXML
    private VBox taskList;

    ZonedDateTime dateFocus;
    ZonedDateTime today;
    Map<LocalDate, Integer> taskCounts = new HashMap<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dateFocus = ZonedDateTime.now();
        today = ZonedDateTime.now();
        drawCalendar();
    }

    @FXML
    void backOneMonth(ActionEvent event) {
        dateFocus = dateFocus.minusMonths(1);
        calendar.getChildren().clear();
        drawCalendar();
    }

    @FXML
    void forwardOneMonth(ActionEvent event) {
        dateFocus = dateFocus.plusMonths(1);
        calendar.getChildren().clear();
        drawCalendar();
    }

    private void drawCalendar() {
        month.setText(dateFocus.getMonth().toString());
        year.setText(String.valueOf(dateFocus.getYear()));

        // Calculate offsets and days
        ZonedDateTime firstDayOfMonth = dateFocus.withDayOfMonth(1);
        int daysInMonth = dateFocus.toLocalDate().lengthOfMonth();
        int dayOfWeek = firstDayOfMonth.getDayOfWeek().getValue(); // 1 = Monday, 7 = Sunday
        int offset = (dayOfWeek % 7);

        // Add empty cells for offset
        for (int i = 0; i < offset; i++) {
            calendar.getChildren().add(createDayCell("", false));
        }

        // Add day cells
        for (int day = 1; day <= daysInMonth; day++) {
            boolean isToday = today.getYear() == dateFocus.getYear()
                    && today.getMonthValue() == dateFocus.getMonthValue()
                    && today.getDayOfMonth() == day;
            calendar.getChildren().add(createDayCell(String.valueOf(day), isToday));
        }

        // Align children in the FlowPane
        calendar.setAlignment(Pos.TOP_LEFT);
        calendar.setHgap(15);
        calendar.setVgap(7);
    }

    private StackPane createDayCell(String day, boolean isToday) {
        StackPane stackPane = new StackPane();
        stackPane.setPrefSize(60, 50);

        Circle background = new Circle(25);
        background.setFill(isToday ? Paint.valueOf("#5c155e") : Paint.valueOf("#fcf0ff"));

        Text text = new Text(day);
        text.setFill(isToday ? Paint.valueOf("#fcf0ff") : Color.BLACK);
        text.setStyle("-fx-font-size: 16;");

        stackPane.getChildren().addAll(background, text);

        if (!day.isEmpty()) {
            LocalDate date = LocalDate.of(dateFocus.getYear(), dateFocus.getMonthValue(), Integer.parseInt(day));
            if (taskCounts.containsKey(date)) {
                Circle taskIndicator = new Circle(5, Paint.valueOf("#5c155e"));
                StackPane.setAlignment(taskIndicator, Pos.BOTTOM_CENTER);
                stackPane.getChildren().add(taskIndicator);
            }
        }

        stackPane.setOnMouseClicked(event -> {
            if (!day.isEmpty()) {
                LocalDate clickedDate = LocalDate.of(dateFocus.getYear(), dateFocus.getMonthValue(), Integer.parseInt(day));
                showTasksForDate(clickedDate);
            }
        });

        return stackPane;
    }
    
    // Show panel when clicking on a date
    private void showTasksForDate(LocalDate date) {
        Dialog<Void> taskDialog = new Dialog<>();
        taskDialog.setTitle("Tasks for " + date);

        VBox taskContainer = new VBox(10);
        taskContainer.setAlignment(Pos.CENTER);
        taskContainer.setStyle("-fx-padding: 15px; -fx-background-color: #fcf0ff; -fx-border-color: #5c155e; -fx-border-width: 2px;");

        Label titleLabel = new Label("Tasks for " + date);
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #5c155e;");

        ListView<String> taskListView = new ListView<>();
        taskListView.setStyle("-fx-background-color: #f5e0f4; -fx-border-color: #5c155e; -fx-border-width: 1px;");
        taskListView.setPrefHeight(5 * 24);

        for (CheckBox task : taskList.getChildren().filtered(node -> node instanceof CheckBox).toArray(new CheckBox[0])) {
            if (task.getText().contains(date.toString())) {
                taskListView.getItems().add(task.getText().replace(" - " + date, "")); // Remove date from text
            }
        }

        if (taskListView.getItems().isEmpty()) {
            taskListView.getItems().add("No tasks scheduled.");
        }

        taskContainer.getChildren().addAll(titleLabel, taskListView);
        taskDialog.getDialogPane().setContent(taskContainer);
        taskDialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        taskDialog.showAndWait();
    }

    @FXML // Add task
    void addTask(ActionEvent event) {
        String title = taskTitleField.getText();
        LocalDate date = taskDatePicker.getValue();

        if (title.isEmpty() || date == null) {
            showAlert("Please fill in all fields.");
            return;
        }

        addTaskToList(title, date);
        markCalendar(date);
        taskTitleField.clear();
        taskDatePicker.setValue(null);
    }

    // Append added task to the list pane
    private void addTaskToList(String title, LocalDate date) {
        CheckBox taskCheckBox = new CheckBox(title + " - " + date);
        taskCheckBox.setStyle("-fx-font-size: 15px; -fx-text-fill: #4f1e69;");

        // Remove the task if checked
        taskCheckBox.setOnAction(event -> {
            if (taskCheckBox.isSelected()) {
                new Thread(() -> {
                    try {
                        Thread.sleep(1500); // Wait 1.5 seconds
                        javafx.application.Platform.runLater(() -> removeTask(taskCheckBox, date));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        });

        taskList.getChildren().add(taskCheckBox);
    }

    // Remove task from list and calendar
    private void removeTask(CheckBox taskCheckBox, LocalDate date) {
        taskList.getChildren().remove(taskCheckBox);

        // Update task count
        taskCounts.put(date, taskCounts.getOrDefault(date, 1) - 1);
        if (taskCounts.get(date) <= 0) {
            taskCounts.remove(date);
        }

        // Redraw calendar to update task indicators
        calendar.getChildren().clear();
        drawCalendar();
    }

    private void markCalendar(LocalDate date) {
        taskCounts.put(date, taskCounts.getOrDefault(date, 0) + 1);
        calendar.getChildren().clear();
        drawCalendar();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void clearFields(ActionEvent event) {
        taskTitleField.setText(null);
        taskDatePicker.setValue(null);
    }
}
