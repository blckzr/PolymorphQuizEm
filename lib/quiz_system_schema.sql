CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    user_account VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL
);

CREATE TABLE quiz (
    quiz_id INT AUTO_INCREMENT PRIMARY KEY,
    quiz_user_score INT NOT NULL,
    user_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE quiz_question (
    question_id INT AUTO_INCREMENT PRIMARY KEY,
    quiz_id INT NOT NULL,
    question_answer VARCHAR(255) NOT NULL,
    question VARCHAR(255) NOT NULL,
    FOREIGN KEY (quiz_id) REFERENCES quiz(quiz_id)
);

CREATE TABLE quiz_category (
    question_id INT NOT NULL,
    category VARCHAR(255) NOT NULL,
    PRIMARY KEY (question_id, category),
    FOREIGN KEY (question_id) REFERENCES quiz_question(question_id)
);

CREATE TABLE quiz_choice (
    question_id INT NOT NULL,
    correct_answer VARCHAR(255) NOT NULL,
    question_answer VARCHAR(255) NOT NULL,
    question_choices VARCHAR(255) NOT NULL,
    PRIMARY KEY (question_id, question_answer),
    FOREIGN KEY (question_id) REFERENCES quiz_question(question_id)
);
