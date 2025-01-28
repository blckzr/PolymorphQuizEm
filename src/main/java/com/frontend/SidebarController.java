package com.frontend;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class SidebarController {
    @FXML
    private BorderPane mainPane;

    private String username;

    public void setUsername(String username) {
        this.username = username;
        SceneLoader object = new SceneLoader();
        Pane view = object.getPage("Dashboard");
        setDefaultPage(view);
    }

    public void setDefaultPage(Pane view) {
        mainPane.setCenter(view);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Dashboard.fxml"));
            Pane dashboardView = loader.load();

            DashboardController dashboardController = loader.getController();
            dashboardController.changeUsername(username);
            mainPane.setCenter(dashboardView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void switchToDashboard(javafx.scene.input.MouseEvent event) {
        SceneLoader object = new SceneLoader();
        Pane view = object.getPage("Dashboard");
        mainPane.setCenter(view);
    }
}
