package com.frontend;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    @FXML
    private PieChart pieChart;

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
    }

}
