package com.frontend;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Popup;
import javafx.scene.control.*;

import java.io.IOException;

public class PopupController {

    @FXML
    public Button NoSubmitButton;
    @FXML
    public Button YesSubmitButton;
    @FXML
    public Button dashboard_button;
    @FXML
    public Label sureLabel;
    @FXML
    public  Label scoreLabel;
    @FXML
    public PieChart pieChart;
    @FXML
    public ImageView questionmark;

    private Popup popup;
    private SidebarController sidebarController;

    private MainQuizController mainQuizController;

    public void setMainQuizController(MainQuizController mainQuizController) {
        this.mainQuizController = mainQuizController;
    }

    public void initialize(){
        dashboard_button.setVisible(false);
        dashboard_button.setDisable(true);
        scoreLabel.setVisible(false);
        pieChart.setVisible(false);
    }

    public void setSidebarController(SidebarController sidebarController) {
        this.sidebarController = sidebarController;
    }

    public void setPopup(Popup popup) {
        this.popup = popup;
    }

    @FXML
    private void handleClose() {
        popup.hide(); // Close the popup when the close button is clicked
    }

    public void gotoDashboard(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Dashboard.fxml"));
            Pane dashboardView = loader.load();

            DashboardController dashboardController = loader.getController();

            if (sidebarController != null) {
                dashboardController.setUsername(sidebarController.getUsername());
            }

            sidebarController.setCenterView(dashboardView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleYes(ActionEvent actionEvent) {
        double correctPer= (mainQuizController.score/mainQuizController.totalPoints)*100;
        System.out.println(mainQuizController.score +" " + mainQuizController.totalPoints);
        double wrongPer=100-correctPer;

        dashboard_button.setVisible(true);
        dashboard_button.setDisable(false);
        scoreLabel.setVisible(true);
        pieChart.setVisible(true);
        questionmark.setVisible(false);
        sureLabel.setVisible(false);
        NoSubmitButton.setVisible(false);
        YesSubmitButton.setVisible(false);

        mainQuizController.isDone=true;
        pieChart.getData().clear();
        PieChart.Data correct = new PieChart.Data("Correct",correctPer);
        PieChart.Data wrong = new PieChart.Data("Wrong",wrongPer);
        pieChart.getData().add(correct);
        pieChart.getData().add(wrong);
        scoreLabel.setText("You score "+String.format("%.2f", correctPer)+"% out of 100%");

    }

    public void handleNo(ActionEvent actionEvent) {
        popup.hide();
    }
}