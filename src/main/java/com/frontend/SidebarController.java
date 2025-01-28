package com.frontend;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class SidebarController {
    @FXML
    private BorderPane mainPane;
    @FXML
    private Pane dbPane, quizPane, historyPane, trackingPane, dbBump, quizBump, historyBump, trackingBump;
    @FXML
    private Label usernameLbl;

    private Stage stage;
    private String username;

    public void setUsername(String username) {
        this.username = username;
        usernameLbl.setText(username);
        SceneLoader object = new SceneLoader();
        Pane view = object.getPage("Dashboard");
        setDefaultPage(view);
    }

    public void setDefaultPage(Pane view) {
        mainPane.setCenter(view);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Dashboard.fxml"));
            Pane dashboardView = loader.load();

            dbBump.setVisible(true);
            dbPane.setStyle("-fx-background-color: #c951c9;");

            DashboardController dashboardController = loader.getController();
            dashboardController.setUsername(username);
            mainPane.setCenter(dashboardView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void resetAllPanes() {
        dbPane.setStyle("-fx-background-color: transparent;");
        quizPane.setStyle("-fx-background-color: transparent;");
        historyPane.setStyle("-fx-background-color: transparent;");
        trackingPane.setStyle("-fx-background-color: transparent;");

        dbBump.setVisible(false);
        quizBump.setVisible(false);
        historyBump.setVisible(false);
        trackingBump.setVisible(false);
    }

    @FXML
    private void switchToDashboard(javafx.scene.input.MouseEvent event) {
        resetAllPanes();
        dbBump.setVisible(true);
        dbPane.setStyle("-fx-background-color: #c951c9;");

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Dashboard.fxml"));
            Pane dashboardView = loader.load();

            DashboardController dashboardController = loader.getController();
            dashboardController.setUsername(username);

            mainPane.setCenter(dashboardView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void switchToQuiz(javafx.scene.input.MouseEvent event) {
        resetAllPanes();
        quizBump.setVisible(true);
        quizPane.setStyle("-fx-background-color: #c951c9;");

//        SceneLoader object = new SceneLoader();
//        Pane view = object.getPage("Dashboard");
//        mainPane.setCenter(view);
    }
    @FXML
    private void switchToHistory(javafx.scene.input.MouseEvent event) {
        resetAllPanes();
        historyBump.setVisible(true);
        historyPane.setStyle("-fx-background-color: #c951c9;");

//        SceneLoader object = new SceneLoader();
//        Pane view = object.getPage("Dashboard");
//        mainPane.setCenter(view);
    }
    @FXML
    private void switchToTracking(javafx.scene.input.MouseEvent event) {
        resetAllPanes();
        trackingBump.setVisible(true);
        trackingPane.setStyle("-fx-background-color: #c951c9;");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Tracking.fxml"));
        SceneLoader object = new SceneLoader();
        Pane view = object.getPage("Tracking");
        mainPane.setCenter(view);
    }
    @FXML
    private void switchToSettings(javafx.scene.input.MouseEvent event) {
        resetAllPanes();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Settings.fxml"));
        SceneLoader object = new SceneLoader();
        Pane view = object.getPage("Settings");
        mainPane.setCenter(view);
    }
    @FXML
    private void logout(javafx.scene.input.MouseEvent event) throws IOException {
        Parent root;
        Scene scene;

        root = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
