package database;

public class quiz {
    private int quiz_id;
    private int user_id;
    private int quiz_score;
    private String quiz_title;
    private String quiz_difficulty;

    public int getQuiz_id() {
        return quiz_id;
    }

    public String getQuiz_difficulty() {
        return quiz_difficulty;
    }

    public String getQuiz_title() {
        return quiz_title;
    }

    public int getUser_id() {
        return user_id;
    }

    public int getQuiz_score() {
        return quiz_score;
    }

    public void setQuiz_id(int quiz_id) {
        this.quiz_id = quiz_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setQuiz_score(int quiz_score) {
        this.quiz_score = quiz_score;
    }

    public void setQuiz_title(String quiz_title) {
        this.quiz_title = quiz_title;
    }

    public void setQuiz_difficulty(String quiz_difficulty) {
        this.quiz_difficulty = quiz_difficulty;
    }

    public quiz(int quiz_id, int user_id, int quiz_score, String quiz_title, String quiz_difficulty) {
        this.quiz_id = quiz_id;
        this.user_id = user_id;
        this.quiz_score = quiz_score;
        this.quiz_title = quiz_title;
        this.quiz_difficulty = quiz_difficulty;
    }
}
