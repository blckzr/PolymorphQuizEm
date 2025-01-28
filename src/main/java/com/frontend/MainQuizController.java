package com.frontend;

import com.backend.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

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

    private TimerController timerController;
    public boolean isDone;
    private QuizQuestion currentQuestion;
    private Stage stage;
    public QuizQuestion[]  questionset;

    @FXML
    private void initialize() {
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
                        false,4
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

    // Sample questions and choices

    public ArrayList<String> answers =  new ArrayList<>();

    @FXML
    private void handleNextButton() {

        currentQuestion.storeAnswer();

        currentQuestionIndex++;

        if(currentQuestionIndex!=0){
            prevButton.setVisible(true);
        }

        if(nextButton.getText().equals("Submit")){
            timerController.stopCountdown();
            stage.setFullScreen(false);
            isDone = true;
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

        currentQuestion.storeAnswer();

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

    public void setStage(Stage stage){
        this.stage=stage;
    }

}

