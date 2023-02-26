package com.example.quiz.Quiz.DTO;

import lombok.Builder;

import java.util.List;

@Builder
public record QuestionDTO(String question_text, List<AnswerDTO> answers) {
}
