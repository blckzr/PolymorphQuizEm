package com.frontend;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.net.URL;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    @FXML
    private PieChart pieChart;
    
    @FXML
    private Label welcomeLbl, usernameLbl, quizTakenLbl, month, year, upcomingDaysLbl;

    @FXML
    private FlowPane calendar;

    ZonedDateTime dateFocus;
    ZonedDateTime today;

    public void setUsername(String username) {
        welcomeLbl.setText("Hey, " + username + "!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Correct Answers", 54),
                        new PieChart.Data("Partially Correct", 30),
                        new PieChart.Data("Wrong Answers", 26)
                );

        // Set the display labels dynamically
        pieChartData.forEach(data -> {
            data.nameProperty().bind(
                    Bindings.concat(
                            data.getName(), " : ", data.pieValueProperty().asString("%.0f")
                    )
            );
        });

        pieChart.setData(pieChartData);
        pieChart.setLegendSide(Side.RIGHT);
        pieChart.getStylesheets().add(getClass().getResource("DashboardStyle.css").toExternalForm());

        dateFocus = ZonedDateTime.now();
        today = ZonedDateTime.now();
        drawCalendar();
    }

    public void changeNoOfQuizTaken() {
        // Back End here
        quizTakenLbl.setText("1");
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

    private void drawCalendar(){
        month.setText(dateFocus.getMonth().toString());
        year.setText(String.valueOf(dateFocus.getYear()));

        ZonedDateTime firstDayOfMonth = dateFocus.withDayOfMonth(1);
        int daysInMonth = dateFocus.toLocalDate().lengthOfMonth();
        int dayOfWeek = firstDayOfMonth.getDayOfWeek().getValue(); // 1 = Monday, 7 = Sunday

        int offset = (dayOfWeek % 7);

        for (int i = 0; i < offset; i++) {
            calendar.getChildren().add(createDayCell("", false));
        }

        for (int day = 1; day <= daysInMonth; day++) {
            boolean isToday = today.getYear() == dateFocus.getYear()
                    && today.getMonthValue() == dateFocus.getMonthValue()
                    && today.getDayOfMonth() == day;
            calendar.getChildren().add(createDayCell(String.valueOf(day), isToday));
        }
    }

    private StackPane createDayCell(String day, boolean isToday) {
        StackPane stackPane = new StackPane();
        stackPane.setPrefSize(47, 40);

        // Rectangle for the background
        Circle background = new Circle(50, 50.0, 15.0);
        background.setFill(isToday ? Paint.valueOf("#5c155e") : Paint.valueOf("#fcf0ff"));

        // Text for the day number
        Text text = new Text(day);
        text.setFill(isToday ? Paint.valueOf("#fcf0ff") : Color.BLACK);
        text.setStyle("-fx-font-size: 14;");

        stackPane.getChildren().addAll(background, text);

        // Change here if add click on a day
        stackPane.setOnMouseClicked(event -> {
            System.out.println("Clicked on day: " + day);
        });

        return stackPane;
    }

}
