package com.example.quiz.Quiz.DTO;

import lombok.Builder;

import java.util.List;

@Builder
public record QuizDisplayDTO(Long id,String name, List<QuestionDTO> questions) {
}
