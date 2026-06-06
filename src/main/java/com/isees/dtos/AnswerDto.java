package com.isees.dtos;

import lombok.Data;

@Data
public class AnswerDto {
    private String question;
    private String answer;
	private String optionA;
	private String optionB;
	private String optionC;
	private String optionD;
}
