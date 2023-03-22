package com.example.quiz.User.DTO;

import com.example.quiz.Quiz.DTO.QuizInfoDTO;

import java.util.List;

public record UserQuizDTO(List<QuizInfoDTO> quiz_list) {
}
