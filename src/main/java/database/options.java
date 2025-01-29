package database;

public class options {
    private int question_id;
    private int answer_id;
    private String answer_text;
    private boolean isCorrect;

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public int getAnswer_id() {
        return answer_id;
    }

    public void setAnswer_id(int answer_id) {
        this.answer_id = answer_id;
    }

    public String getAnswer_text() {
        return answer_text;
    }

    public void setAnswer_text(String answer_text) {
        this.answer_text = answer_text;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    public options(int question_id, int answer_id, String answer_text, boolean isCorrect) {
        this.question_id = question_id;
        this.answer_id = answer_id;
        this.answer_text = answer_text;
        this.isCorrect = isCorrect;
    }
}
