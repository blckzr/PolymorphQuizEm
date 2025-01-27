package com.frontend;

import com.backend.MultipleChoices;
import com.backend.QuizQuestion;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
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

    private TimerController timerController;
    private boolean isDone=false;
    private QuizQuestion currentQuestion;
    private Stage stage;
    public QuizQuestion[]  questionset;

    @FXML
    private void initialize() {
        prevButton.setVisible(false);
        timerController = new TimerController(timerLabel);
        timerController.startCountdown(300);
        frame.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ALT||event.getCode() == KeyCode.TAB) {
                event.consume();  // Consume the event to stop fullscreen exit
            }
        });
        String[] options = {"Red", "Green", "Blue", "Yellow"};
        questionset = new QuizQuestion[]{new MultipleChoices(
                "What is the capital of France?",
                "Paris",
                new String[]{"Paris", "London", "Rome", "Berlin"},
                this,
                false
        ),

                new MultipleChoices(
                        "Which planet is known as the Red Planet?",
                        "Mars",
                        new String[]{"Venus","Mars","Jupiter","Saturn"},
                        this,
                        false
                ),

                new MultipleChoices(
                        "Who wrote the play 'Romeo and Juliet'?",
                        "William Shakespeare",
                        new String[]{"Charles Dickens","Mark Twain","William Shakespeare","Jane Austen"},
                        this,
                        false
                ),

                new MultipleChoices(
                        "What is the largest ocean on Earth?",
                        "Pacific Ocean",
                        new String[]{"Atlantic Ocean","Indian Ocean","Arctic Ocean","Pacific Ocean"},
                        this,
                        false
                ),

                new MultipleChoices(
                        "What is the main ingredient in guacamole?",
                        "Avocado",
                        new String[]{"Tomato","Avocado","Onion","Garlic"},
                        this,
                        false
                )};
        currentQuestion = questionset[0];
        currentQuestion.displayQuestion();
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

