package com.example.quiz.Quiz.Question;

import com.example.quiz.Quiz.Answer.Answer;
import com.example.quiz.Quiz.Answer.AnswerService;
import com.example.quiz.Quiz.DTO.AnswerDTO;
import com.example.quiz.Quiz.DTO.QuestionDTO;
import com.example.quiz.Quiz.Quiz;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository question_repository;
    private final AnswerService answer_service;

    public Question createQuestion(QuestionDTO question, Quiz quiz) {
        Question new_question = Question.builder()
                .question_text(question.question_text())
                .quiz(quiz)
                .answers(new ArrayList<>())
                .build();
        new_question = question_repository.save(new_question);

        ArrayList<Answer> answers = new ArrayList<>();

        for (AnswerDTO answer: question.answers()){
            answers.add(answer_service.createAnswer(answer,new_question));
        }
        new_question.setAnswers(answers);
        return question_repository.save(new_question);

    }
}
