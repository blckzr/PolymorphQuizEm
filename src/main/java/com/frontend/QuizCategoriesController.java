package com.frontend;

import com.frontend.QuizItem;
import com.frontend.QuizItemController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class QuizCategoriesController implements Initializable {

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private GridPane gridPane;

    private SidebarController sidebarController;
    private List<QuizItem> quizzes = new ArrayList<>();

    private List<QuizItem> getSampleData() {
        List<QuizItem> quizzes = new ArrayList<>();

        quizzes.add(new QuizItem("OOP Quiz - Identification", "AVERAGE", "10 minutes", "5 items", "/com/frontend/icons/small1.png"));
        quizzes.add(new QuizItem("OOP Quiz - True or False", "EASY", "10 minutes", "5 items", "/com/frontend/icons/small2.png"));
        quizzes.add(new QuizItem("OOP Quiz - Multiple Choice", "EASY", "10 minutes", "5 items", "/com/frontend/icons/small3.png"));
        quizzes.add(new QuizItem("OOP Quiz - Fill in the Blank", "AVERAGE", "10 minutes", "5 items", "/com/frontend/icons/small4.png"));
        quizzes.add(new QuizItem("OOP Quiz - Enumeration", "AVERAGE", "10 minutes", "5 items", "/com/frontend/icons/small5.png"));
        quizzes.add(new QuizItem("OOP Quiz - Essay", "HARD", "30 minutes", "5 items", "/com/frontend/icons/small6.png"));

        return quizzes;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        quizzes.addAll(getSampleData());
        int column = 0;
        int row = 1;

        try {
            for (int i = 0; i < quizzes.size(); i++) {
                QuizItem quiz = quizzes.get(i);

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("QuizItem.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                QuizItemController itemController = fxmlLoader.getController();
                itemController.setQuizTitle(quiz.getTitle());
                itemController.setDifficulty(quiz.getDifficulty());
                itemController.setDuration(quiz.getDuration());
                itemController.setItemCount(quiz.getItemCount());
                itemController.setQuizImage(quiz.getImagePath());

                int finalI = i;
                anchorPane.setOnMouseClicked(event -> {
                    try {
                        loadQuizScene(finalI);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                if (column == 4) {
                    column = 0;
                    row++;
                }

                gridPane.add(anchorPane, column++, row);
                gridPane.setMinWidth(Region.USE_COMPUTED_SIZE);
                gridPane.setPrefWidth(Region.USE_COMPUTED_SIZE);
                gridPane.setMaxWidth(Region.USE_PREF_SIZE);

                gridPane.setMinHeight(Region.USE_COMPUTED_SIZE);
                gridPane.setPrefHeight(Region.USE_COMPUTED_SIZE);
                gridPane.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setSidebarController(SidebarController sidebarController) {
        this.sidebarController = sidebarController;
    }

    public void switchToCreate(ActionEvent event) {
        if (sidebarController != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("QuizMaker.fxml"));
                Pane quizMakerView = loader.load();

                QuizMakerController quizMakerController = loader.getController();

                quizMakerController.setSidebarController(sidebarController);

                sidebarController.setCenterView(quizMakerView);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadQuizScene(int quizIndex) throws IOException {
        String fxmlFile = switch (quizIndex) {
            case 0 -> "CategoryClick.fxml";
            case 1 -> "Category2Click.fxml";
            case 2 -> "Category3Click.fxml";
            case 3 -> "Category4Click.fxml";
            case 4 -> "Category5Click.fxml";
            default -> "Category6Click.fxml";
        };
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = loader.load();

        Stage stage = (Stage) gridPane.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}