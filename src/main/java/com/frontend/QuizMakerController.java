package com.frontend;

import com.backend.QuestionPaneData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class QuizMakerController {
    @FXML
    VBox QuestionVbox;
    @FXML
    RadioButton reviewerMode;
    @FXML
    RadioButton profMode;
    @FXML
    ToggleGroup Modes;
    @FXML
    Label DueLabel;
    @FXML
    DatePicker datePicker;
    @FXML
    Label setTimeLabel;
    @FXML
    Label minsLabel;
    @FXML
    TextField timerTF;
    @FXML
    Button saveButton;
    @FXML
    Label totalPointsLabel;
    @FXML
    Button BackButton;
    @FXML
    TextField TitleTextField;
    @FXML
    CheckBox TimerCheckbox;
    @FXML
    ChoiceBox<String> difficultyChoice;

    private SidebarController sidebarController;

    private final String[] difficulty = {"Easy", "Average", "Hard"};

    private List<Parent> questionPanes = new ArrayList<>();
    private int nextIndex = 0;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        difficultyChoice.getItems().addAll(difficulty);
    }

    @FXML
    private void initialize() {
        saveButton.setOnAction(event -> saveQuiz());

        DueLabel.setDisable(true);
        datePicker.setDisable(true);
        DueLabel.setVisible(false);
        datePicker.setVisible(false);
        setTimeLabel.setDisable(true);
        timerTF.setDisable(true);
        minsLabel.setDisable(true);
        setTimeLabel.setVisible(false);
        timerTF.setVisible(false);
        minsLabel.setVisible(false);

        Integer[] monthsOption = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
    }

    @FXML
    private void handleReviewMode() {
        DueLabel.setDisable(true);
        datePicker.setDisable(true);
        datePicker.setVisible(false);
        DueLabel.setVisible(false);
    }

    @FXML
    private void handleProfMode() {
        DueLabel.setDisable(false);
        datePicker.setDisable(false);
        datePicker.setVisible(true);
        DueLabel.setVisible(true);
    }


    @FXML
    private void addQuestion() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("QuestionPane.fxml"));
        Parent questionPane = loader.load();  // Load the question pane

        QuestionPaneController controller = loader.getController();

        // Set the controller as the userData of the Parent (questionPane)
        questionPane.setUserData(controller);

        if (!QuestionVbox.getChildren().contains(questionPane)) {
            controller.setIndex(nextIndex);
            controller.setRemoveListener(() -> removeQuestionPane(questionPane));

            // Add the new question pane
            QuestionVbox.getChildren().add(questionPane);
            nextIndex++; // Increment the index
        }

        updateTotalPoints();

    }

    @FXML
    private void removeQuestionPane(Parent questionPane) {
        QuestionVbox.getChildren().remove(questionPane);
        questionPanes.remove(questionPane);
        reassignIndices();
        updateTotalPoints();
    }

    public void handleCheckbox(ActionEvent actionEvent) {
        setTimeLabel.setDisable(false);
        timerTF.setDisable(false);
        minsLabel.setDisable(false);
        setTimeLabel.setVisible(true);
        timerTF.setVisible(true);
        minsLabel.setVisible(true);
    }

    private void reassignIndices() {

        for (int i = 0; i < QuestionVbox.getChildren().size(); i++) {
            Parent questionPane = (Parent) QuestionVbox.getChildren().get(i);
            // Ensure the controller is correctly retrieved from the HBox
            QuestionPaneController controller = (QuestionPaneController) questionPane.getUserData();
            if (controller != null) {
                controller.setIndex(i);
            }
        }
    }

    private List<QuestionPaneData> questions = new ArrayList<>();

    public void gatherAllQuestions() {
        questions.clear(); // Clear the list to avoid duplicates

        // Iterate through each child in QuestionVbox (which are question panes)
        for (Node questionPane : QuestionVbox.getChildren()) {
            QuestionPaneController controller = (QuestionPaneController) questionPane.getUserData();

            if (controller != null) {
                // Get data from the controller
                String questionText = controller.getQuestionText();
                String questionType = controller.getQuestionType();
                String correctAnswer = controller.getCorrectAnswer();
                String[] choices = controller.getChoices();
                int points = controller.getPoints();

                // Create a new Question object and add it to the list
                QuestionPaneData question = new QuestionPaneData(questionType, questionText, correctAnswer, choices, points);
                questions.add(question);
            } else {
                System.out.println("Controller is null for a question pane");
            }
        }

        // For debugging, print out the questions
        displayQuestions();
        try {
            saveQuestionsToCSV();
            saveQuizMetadataToCSV();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    int totalPoints;

    private void calculateTotalPoints() {
        totalPoints = 0;

        // Iterate through each question pane and add its points
        for (Node questionPane : QuestionVbox.getChildren()) {
            // Get the controller for each question pane
            QuestionPaneController controller = (QuestionPaneController) questionPane.getUserData();

            if (controller != null) {
                // Add the points of the current question to the total
                totalPoints += controller.getPoints();  // Assuming getPoints() gives the points for this question
            }
        }

    }

    public void updateTotalPoints() {
        calculateTotalPoints();
        totalPointsLabel.setText(totalPoints+" pts");  // Update label with total points
    }

    public void displayQuestions() {
        for (QuestionPaneData q : questions) {
            System.out.println("Type: " + q.getQuestionType());
            System.out.println("Question: " + q.getQuestionText());
            System.out.println("Correct Answer: " + q.getCorrectAnswer());
            if(q.getChoices()!=null) {
                System.out.print("Choices: ");
                for (String choice : q.getChoices()) {
                    System.out.print(choice + " ");
                }
            }
            System.out.println("\nPoints: " + q.getPoints());
            System.out.println();
        }
    }

    public void saveQuestionsToCSV() throws IOException {
        String filename = TitleTextField.getText().trim();

        if (filename.isEmpty()) {
            System.out.println("Filename is empty. Please provide a valid filename.");
            return;
        }

        if (!filename.endsWith(".csv")) {
            filename += ".csv";
        }

        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));

        writer.write("QuestionType,QuestionText,CorrectAnswer,Choices,Points\n");

        if (questions.isEmpty()) {
            System.out.println("No questions to save!");
            writer.close();
            return;
        }


        for (QuestionPaneData question : questions) {

            String questionType = question.getQuestionType();
            String questionText = question.getQuestionText();
            String correctAnswer = question.getCorrectAnswer();
            String[] choices = question.getChoices();
            int points = question.getPoints();


            correctAnswer = "\"" + correctAnswer + "\"";

            String joinedChoices;

            if (choices != null && choices.length > 0) {
                joinedChoices = String.join("/", choices);
                joinedChoices = "\""+joinedChoices+"\"";
            } else {

                joinedChoices = "";
            }
            System.out.println(joinedChoices);

            writer.write(String.format("\"%s\",\"%s\",%s,%s,%d\n",
                    questionType,
                    questionText,
                    correctAnswer,
                    joinedChoices,
                    points));


            System.out.println("Writing to CSV: " + String.format("\"%s\",\"%s\",%s,%s,%d\n",
                    questionType,
                    questionText,
                    correctAnswer,
                    joinedChoices,
                    points));
        }

        writer.close();

        System.out.println("Quiz saved to CSV successfully!");
    }

    public void saveQuizMetadataToCSV() throws IOException {

        String metadataFilename = TitleTextField.getText().trim() + ".csv"; // Use quiz title or any other logic for naming

        BufferedWriter writer = new BufferedWriter(new FileWriter("Quizzez.csv", true)); // 'true' to append to the file

        String timer;
        String selectedMode;
        if(profMode.isSelected()){
            selectedMode = "Professional";
        }else{
            selectedMode = "Review";
        }

        if(TimerCheckbox.isSelected()){
            timer= timerTF.getText();
        }else{
            timer= "";
        }

        LocalDate duedate = datePicker.getValue();



        writer.write(String.format("\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",%d\n",
                metadataFilename,
                TitleTextField.getText().trim(),
                selectedMode,
                timer,
                duedate,
                totalPoints
        ));

        // Close the writer
        writer.close();

        System.out.println("Quiz metadata saved to CSV successfully!");
    }

    public void saveQuiz() {
        updateTotalPoints();
        gatherAllQuestions();
        updateTotalPoints();
    }

    public void setSidebarController(SidebarController sidebarController) {
        this.sidebarController = sidebarController;
    }

    public void handleBackButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("QuizCategories.fxml"));
            Pane quizCategoriesView = loader.load();

            QuizCategoriesController quizCategoriesController = loader.getController();

            quizCategoriesController.setSidebarController(sidebarController);

            sidebarController.setCenterView(quizCategoriesView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
