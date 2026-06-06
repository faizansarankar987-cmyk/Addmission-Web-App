package com.isees.dtos;



import java.util.List;

import lombok.Data;
@Data
public class ExamResultDto {

    private String studentName;
    private String email;
    private int score;
    private int totalQuestions;
    private String per;
    private List<AnswerInfo> answers;

    public static class AnswerInfo {
        private String questionText;
        private String selectedOption;
        private String correctAnswer;

        // getters & setters
        public String getQuestionText() { return questionText; }
        public void setQuestionText(String questionText) { this.questionText = questionText; }

        public String getSelectedOption() { return selectedOption; }
        public void setSelectedOption(String selectedOption) { this.selectedOption = selectedOption; }

        public String getCorrectAnswer() { return correctAnswer; }
        public void setCorrectAnswer(String correctAnswer) { this.correctAnswer = correctAnswer; }
    }

    // getters & setters
    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }

    public int getTotalQuestions() { return totalQuestions; }
    public void setTotalQuestions(int totalQuestions) { this.totalQuestions = totalQuestions; }

    public List<AnswerInfo> getAnswers() { return answers; }
    public void setAnswers(List<AnswerInfo> answers) { this.answers = answers; }
}
