package com.frontend;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
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

        // Highlight days with tasks
        if (!day.isEmpty()) {
            LocalDate date = LocalDate.of(dateFocus.getYear(), dateFocus.getMonthValue(), Integer.parseInt(day));
            if (taskCounts.containsKey(date)) {
                Circle taskIndicator = new Circle(5, Color.RED);
                StackPane.setAlignment(taskIndicator, Pos.BOTTOM_CENTER);
                stackPane.getChildren().add(taskIndicator);
            }
        }

        // Click to show tasks
        stackPane.setOnMouseClicked(event -> {
            if (!day.isEmpty()) {
                LocalDate clickedDate = LocalDate.of(dateFocus.getYear(), dateFocus.getMonthValue(), Integer.parseInt(day));
                showTasksForDate(clickedDate);
            }
        });

        return stackPane;
    }

    private void showTasksForDate(LocalDate date) {
        taskList.getChildren().clear(); // Clear previous tasks

        for (CheckBox task : taskList.getChildren().filtered(node -> node instanceof CheckBox).toArray(new CheckBox[0])) {
            if (task.getText().contains(date.toString())) {
                taskList.getChildren().add(task);
            }
        }
    }

    @FXML
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

    private void addTaskToList(String title, LocalDate date) {
        CheckBox taskCheckBox = new CheckBox(title + " - " + date);
        taskCheckBox.setStyle("-fx-font-size: 15px; -fx-text-fill: #4f1e69;");
        taskList.getChildren().add(taskCheckBox);
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
