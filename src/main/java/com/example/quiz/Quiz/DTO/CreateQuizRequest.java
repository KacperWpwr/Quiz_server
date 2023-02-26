package com.example.quiz.Quiz.DTO;

import lombok.Builder;

import java.util.List;

@Builder
public record CreateQuizRequest(String username, String quiz_name, List<QuestionDTO> questions) {
}
