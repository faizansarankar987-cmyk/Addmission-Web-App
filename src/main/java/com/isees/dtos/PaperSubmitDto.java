package com.isees.dtos;

import java.util.List;

public class PaperSubmitDto {

    private String studentName;
    private String email;
    private List<AnswerDto> answers;

    public String getStudentName() {
        return studentName;
    }
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public List<AnswerDto> getAnswers() {
        return answers;
    }
    public void setAnswers(List<AnswerDto> answers) {
        this.answers = answers;
    }

    // ✅ Inner class
    public static class AnswerDto {
        private Long questionId;
        private String selectedOption;

        public Long getQuestionId() {
            return questionId;
        }
        public void setQuestionId(Long questionId) {
            this.questionId = questionId;
        }

        public String getSelectedOption() {
            return selectedOption;
        }
        public void setSelectedOption(String selectedOption) {
            this.selectedOption = selectedOption;
        }
    }
}
