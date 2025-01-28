package com.frontend;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;

public class SidebarController {

    @FXML
    private ColumnConstraints GRID1;

    @FXML
    private GridPane GRID2;

    @FXML
    private Label H2;

    @FXML
    private Label L1;

    @FXML
    private Pane PANE1;

    @FXML
    private RowConstraints R0;

    @FXML
    private Pane dbBump;

    @FXML
    private Pane dbPane;

    @FXML
    private Pane historyBump;

    @FXML
    private Pane historyPane;

    @FXML
    private BorderPane mainPane;

    @FXML
    private Pane quizBump;

    @FXML
    private Pane quizPane;

    @FXML
    private Pane settingsBump;

    @FXML
    private Pane settingsPane;

    @FXML
    private Pane trackingBump;

    @FXML
    private Pane trackingPane;

    @FXML
    private Label usernameLbl;

    @FXML
    void switchToDashboard(MouseEvent event) {

    }

    @FXML
    void switchToHistory(MouseEvent event) {

    }

    @FXML
    void switchToQuiz(MouseEvent event) {

    }

    @FXML
    void switchToSettings(MouseEvent event) {

    }

    @FXML
    void switchToTracking(MouseEvent event) {

    }

}
