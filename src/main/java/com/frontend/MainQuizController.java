package com.frontend;

import com.backend.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;


public class MainQuizController {
    @FXML
    public Label questionLabel;
    @FXML
    public Label questionNoLabel;
    @FXML
    public RadioButton choice1;
    @FXML
    public RadioButton choice2;
    @FXML
    public RadioButton choice3;
    @FXML
    public RadioButton choice4;
    @FXML
    public Button nextButton;
    @FXML
    public Label timerLabel;
    @FXML
    public ToggleGroup Choices;
    @FXML
    public Button prevButton;
    @FXML
    public VBox frame;
    @FXML
    public Pane answerPane;
    @FXML
    public Label ptsLabel;
    @FXML
    public Label text_label;
    @FXML
    public TextField identification_field;
    @FXML
    public TextArea essay_field;
    @FXML
    public StackPane stackpane;

    private TimerController timerController;
    public boolean isDone;
    private QuizQuestion currentQuestion;
    private Stage stage;
    public QuizQuestion[]  questionset;
    public int totalPoints;
    public double score=0;

    @FXML
    private void initialize() {
        totalPoints =14;
        isDone=false;
        prevButton.setVisible(false);
        timerController = new TimerController(timerLabel);
        timerController.startCountdown(300);
        String[] options = {"Red", "Green", "Blue", "Yellow"};
        questionset = new QuizQuestion[]{new MultipleChoices(
                "What is the capital of France?",
                "Paris",
                new String[]{"Paris", "London", "Rome", "Berlin"},
                this,
                false,
                1
        ),

                new MultipleChoices(
                        "Which planet is known as the Red Planet?",
                        "Mars",
                        new String[]{"Venus","Mars","Jupiter","Saturn"},
                        this,
                        false,2
                ),

                new TrueOrFalse(
                        "Did William Shakespeare wrote the play 'Romeo and Juliet'?",
                        "True",
                        this,
                        false,1
                ),

                new Identification(
                        "Who is the Hero of Mactan?",
                        "Lapu-lapu",
                        this,
                        false,1
                ),

                new MultipleChoices(
                        "What is the main ingredient in guacamole?",
                        "Avocado",
                        new String[]{"Tomato","Avocado","Onion","Garlic"},
                        this,
                        false,1
                ),
                new Essay(
                        "Write an essay about guacamole?",
                        new String[]{"Tomato","Avocado","Onion","Garlic"},
                        this,
                        false,4
                ),

                new Enumeration(
                        "Enumerate 4 colors of the rainbow:",
                        new String[]{"Red","Orange","yellow","green","Blue","Purple","Indigo"},
                        this,
                        false,4,4
                )};
        currentQuestion = questionset[0];
        currentQuestion.displayQuestion();
        nextButton.setDisable(true);
        currentQuestion.toggleNextButton();
    }

    public int currentQuestionIndex = 0;

    public ArrayList<String> answers =  new ArrayList<>();
    public ArrayList<Double> scores =  new ArrayList<>();

    @FXML
    private void handleNextButton() {

        currentQuestion.storeAnswerandScore();

        if(currentQuestionIndex != questionset.length - 1) {
            currentQuestionIndex++;
        }else if(currentQuestionIndex == questionset.length - 1&&nextButton.getText().equals("Submit")){
            try {
                showPopup();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if(isDone){
                timerController.stopCountdown();
                nextButton.setDisable(true);
                prevButton.setDisable(true);
            }
        }

        if(currentQuestionIndex!=0){
            prevButton.setVisible(true);

        }


        if(!isDone) {
            currentQuestion = questionset[currentQuestionIndex];
            currentQuestion.displayQuestion();

            if (currentQuestionIndex == questionset.length - 1) {
                nextButton.setText("Submit");
            } else {
                nextButton.setText("Next ▶");
            }
        }
    }

    @FXML
    private  void handlePrevButton(){

        currentQuestion.storeAnswerandScore();

        if(Choices.getSelectedToggle()!=null) {
            currentQuestionIndex--;

            if (currentQuestionIndex == 0) {
                prevButton.setVisible(false);
            }

            if (currentQuestionIndex == questionset.length - 1) {
                nextButton.setText("Submit");
            } else {
                nextButton.setText("Next ▶");
            }

            currentQuestion = questionset[currentQuestionIndex];
            currentQuestion.displayQuestion();
        }
    }

    public void showPopup() throws IOException {
        // Load the Popup FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Popup.fxml"));
        Parent popupContent = loader.load();

        // Get the controller of the popup
        PopupController popupController = loader.getController();

        popupController.setMainQuizController(this);
        // Create the popup and set the content
        Popup popup = new Popup();
        popup.getContent().add(popupContent);

        // Set the controller's popup reference to close it
        popupController.setPopup(popup);

        // Show the popup on the screen
        popup.show(stackpane.getScene().getWindow());

    }

    public void setStage(Stage stage){
        this.stage=stage;
    }

    public double CalculateScore(){
        for(int i=0;i<scores.size();i++){
            score+=scores.get(i);
        }

        return score;
    }

}

