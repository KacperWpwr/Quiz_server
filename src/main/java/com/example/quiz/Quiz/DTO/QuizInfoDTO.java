package com.example.quiz.Quiz.DTO;

import lombok.Builder;

@Builder
public record QuizInfoDTO(String creator_username, Long id, String name, Integer question_amount,Double rating) {
}
