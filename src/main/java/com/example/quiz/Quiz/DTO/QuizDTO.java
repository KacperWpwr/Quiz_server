package com.example.quiz.Quiz.DTO;

import lombok.Builder;

import java.util.List;

@Builder
public record QuizDTO(String name, List<QuestionDTO> questions) {
}
