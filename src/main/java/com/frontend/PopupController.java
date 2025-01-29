package com.frontend;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Popup;
import javafx.scene.control.*;

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
    public void setPopup(Popup popup) {
        this.popup = popup;
    }

    @FXML
    private void handleClose() {
        popup.hide(); // Close the popup when the close button is clicked
    }

    public void gotoDashboard(ActionEvent actionEvent) {

    }

    public void handleYes(ActionEvent actionEvent) {
        double correctPer= (mainQuizController.CalculateScore()/mainQuizController.totalPoints)*100;
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