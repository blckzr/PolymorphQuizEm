package com.frontend;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SidebarTemplate extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SidebarTemplate.fxml"));
        Parent root = loader.load();

        SidebarController controller = loader.getController();

        SceneLoader object = new SceneLoader();
        Pane view = object.getPage("Dashboard");
        controller.setDefaultPage(view);

        Scene scene = new Scene(root);
        Image icon = new Image(getClass().getResourceAsStream("icons/mainLogo.png"));
        scene.getStylesheets().add(getClass().getResource("SidebarTemplateStyle.css").toExternalForm());

        stage.setResizable(false);
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.setTitle("PolymorphQuizEm");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
