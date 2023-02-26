package com.example.quiz.Quiz.DTO;

import lombok.Builder;

@Builder
public record QuizInfoDTO(String creator_username, Long id, String quiz_name, Integer question_amount) {
}
