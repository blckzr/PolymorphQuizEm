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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainQuizController {
    @FXML
    public Label testTitle;
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
    public String Mode;
    List<QuizQuestion> probset;
    int timelimit;

    public List<QuestionPaneData> loadQuestions(String filename) throws IOException {
        List<QuestionPaneData> questions = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;

        // Skip the header
        reader.readLine();

        while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");

            String questionType = data[0].replace("\"","");
            String questionText = data[1].replace("\"", "");
            String correctAnswer = data[2].replace("\"", ""); // Remove quotes
            String[] choices = data[3].split("/"); // Remove quotes and split by comma
            int points = Integer.parseInt(data[4].replace("\"", ""));

            System.out.println(points);
            // Create a QuestionData object and add to the list
            QuestionPaneData question = new QuestionPaneData(questionType, questionText, correctAnswer, choices, points);
            System.out.println(question.getQuestionText());
            questions.add(question);
        }

        reader.close();
        return questions;
    }

    public QuizInfo loadQuizInfo(String filename, int index) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;

        // Skip the header
        reader.readLine();
        int i = 0;
        // Read the first line of quiz info
        while ((line = reader.readLine()) != null){

            if(i!=index-1){
                continue;
            }
            String[] data = line.split(",");

            String title = data[1].replace("\"","");
            String mode = data[2].replace("\"","");
            String time = data[3].replace("\"","");
            String dueDate = data[4];
            int tpoints = Integer.parseInt(data[5]);

            QuizInfo quizInfo = new QuizInfo(title, mode, time, dueDate, tpoints);

            reader.close();
            return quizInfo;
        }

        reader.close();
        return null;
    }

    private void LoadProbset (List<QuestionPaneData> CSVquestions){
        for(QuestionPaneData cur_unformatted_q : CSVquestions){
            String type = cur_unformatted_q.getQuestionType();
            String answer =  cur_unformatted_q.getCorrectAnswer();
            String question = cur_unformatted_q.getQuestionText();
            String[] choices = cur_unformatted_q.getChoices();
            int points = cur_unformatted_q.getPoints();

            System.out.println(type);
            if(type.equals("Multiple Choice")){
                System.out.println(points);
                probset.add(new MultipleChoices(question,answer,choices,this,false,points));
            }else if(type.equals("True or False")){
                probset.add(new TrueOrFalse(question,answer,this, false,points));
            }else if(type.equals("Identification")){
                probset.add(new Identification(question,answer,this,false,points));
            }else if(type.equals("Enumeration")){
                choices = answer.split("/ ");
                probset.add(new Enumeration(question,choices,this, false, choices.length, points));
            }else if(type.equals("Essay")){
                choices = answer.split("/ ");
                probset.add(new Essay(question,choices,this,false, points));
            }


        }
    }

    @FXML
    private void initialize() {
        QuizInfo currentQuiz;
        List<QuestionPaneData>  questionset;
        try {
            currentQuiz = loadQuizInfo("Quizzez.csv",1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String targettitle = currentQuiz.getTitle();
        probset = new ArrayList<>();
        Mode=currentQuiz.getMode();
        totalPoints =currentQuiz.getTotalPoints();
        isDone=false;
        testTitle.setText(targettitle);
        prevButton.setVisible(false);
        timelimit=currentQuiz.getTimer();
        timerLabel.setVisible(false);

        try {
            questionset = loadQuestions(targettitle+".csv");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        LoadProbset(questionset);

        if(timelimit!=0) {
            timerController = new TimerController(timerLabel);
            timerController.startCountdown(timelimit*60);
            timerLabel.setVisible(true);
        }

        currentQuestion = probset.get(currentQuestionIndex);
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
        System.out.println(scores.get(currentQuestionIndex));
        if(currentQuestionIndex != probset.size() - 1) {
            currentQuestionIndex++;
        }else if(currentQuestionIndex == probset.size() - 1&&nextButton.getText().equals("Submit")){
            score=CalculateScore();
            System.out.println(score);
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
            currentQuestion = probset.get(currentQuestionIndex);
            currentQuestion.displayQuestion();

            if (currentQuestionIndex == probset.size() - 1) {
                nextButton.setText("Submit");
            } else {
                nextButton.setText("Next ▶");
            }
        }
    }

    @FXML
    private  void handlePrevButton(){

        currentQuestion.storeAnswerandScore();

        currentQuestionIndex--;

        currentQuestion = probset.get(currentQuestionIndex);
        currentQuestion.displayQuestion();

        if (currentQuestionIndex == 0) {
            prevButton.setVisible(false);
        }

        if (currentQuestionIndex == probset.size() - 1) {
            nextButton.setText("Submit");
        } else {
            nextButton.setText("Next ▶");
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

