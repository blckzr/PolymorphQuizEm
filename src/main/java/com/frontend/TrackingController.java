package com.frontend;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.net.URL;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;

public class TrackingController implements Initializable {
    @FXML
    private Label month, year;

    @FXML
    private FlowPane calendar;

    ZonedDateTime dateFocus;
    ZonedDateTime today;

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
        calendar.setHgap(10);
        calendar.setVgap(5);
    }

    private StackPane createDayCell(String day, boolean isToday) {
        StackPane stackPane = new StackPane();
        stackPane.setPrefSize(65, 50); // Adjusted for calendar dimensions

        // Background Circle
        Circle background = new Circle(25);
        background.setFill(isToday ? Paint.valueOf("#5c155e") : Paint.valueOf("#fcf0ff"));

        // Text for the day
        Text text = new Text(day);
        text.setFill(isToday ? Paint.valueOf("#fcf0ff") : Color.BLACK);
        text.setStyle("-fx-font-size: 16;");

        stackPane.getChildren().addAll(background, text);

        // Add click event
        stackPane.setOnMouseClicked(event -> {
            if (!day.isEmpty()) {
                System.out.println("Clicked on day: " + day);
            }
        });

        return stackPane;
    }
}
