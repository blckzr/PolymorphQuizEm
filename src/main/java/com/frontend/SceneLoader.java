package com.frontend;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.net.URL;

public class SceneLoader {
    private Pane view;

    public Pane getPage(String page) {
        try {
            URL filePath = getClass().getResource(page + ".fxml");
            if(filePath == null) {
                throw new java.io.FileNotFoundException("Page does not exist.");
            }
            view = new FXMLLoader().load(filePath);
        } catch (Exception e) {
            System.out.println("Page " + page + " cannot be loaded.");
        }
        return view;
    }
}
