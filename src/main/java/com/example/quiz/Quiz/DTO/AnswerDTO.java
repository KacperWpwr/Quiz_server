package com.example.quiz.Quiz.DTO;

import lombok.Builder;

@Builder
public record AnswerDTO(String answer_text, Boolean is_correct) {
}
