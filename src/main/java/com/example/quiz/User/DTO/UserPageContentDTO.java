package com.example.quiz.User.DTO;

import com.example.quiz.Quiz.DTO.QuizInfoDTO;
import com.example.quiz.Quiz.Quiz;

import java.util.List;

public record UserPageContentDTO(List<QuizInfoDTO> page_quizzes) {
}
