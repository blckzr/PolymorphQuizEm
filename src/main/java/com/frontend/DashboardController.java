package com.frontend;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class DashboardController {
    @FXML
    private Pane panel;

    @FXML
    private void panelClicked() {
        System.out.println("test");
    }
}
