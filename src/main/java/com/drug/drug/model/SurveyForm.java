package com.drug.drug.model;

import java.util.List;

public class SurveyForm {
    private String name;
    private String type;
    private boolean active;
    private boolean publicDisplay;
    private List<Question> questions;

    public SurveyForm() {}

    // Getter & Setter cho SurveyForm
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }
    public boolean isPublicDisplay() {
        return publicDisplay;
    }
    public void setPublicDisplay(boolean publicDisplay) {
        this.publicDisplay = publicDisplay;
    }
    public List<Question> getQuestions() {
        return questions;
    }
    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    // Inner class cho câu hỏi
    public static class Question {
        private String content;
        private String optionA;
        private String optionB;
        private String optionC;
        private String optionD;
        private String correctAnswer;

        public Question() {}

        // Getter & Setter cho Question
        public String getContent() {
            return content;
        }
        public void setContent(String content) {
            this.content = content;
        }
        public String getOptionA() {
            return optionA;
        }
        public void setOptionA(String optionA) {
            this.optionA = optionA;
        }
        public String getOptionB() {
            return optionB;
        }
        public void setOptionB(String optionB) {
            this.optionB = optionB;
        }
        public String getOptionC() {
            return optionC;
        }
        public void setOptionC(String optionC) {
            this.optionC = optionC;
        }
        public String getOptionD() {
            return optionD;
        }
        public void setOptionD(String optionD) {
            this.optionD = optionD;
        }
        public String getCorrectAnswer() {
            return correctAnswer;
        }
        public void setCorrectAnswer(String correctAnswer) {
            this.correctAnswer = correctAnswer;
        }
    }
}
