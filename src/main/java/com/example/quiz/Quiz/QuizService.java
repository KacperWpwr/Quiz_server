package com.example.quiz.Quiz;

import com.example.quiz.Quiz.DTO.CreateQuizRequest;
import com.example.quiz.Quiz.DTO.QuestionDTO;
import com.example.quiz.Quiz.DTO.QuizDTO;
import com.example.quiz.Quiz.Question.Question;
import com.example.quiz.Quiz.Question.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class QuizService {
    private final QuizRepository quiz_repository;
    private final QuestionService question_service;

    public QuizDTO createQuiz(CreateQuizRequest request){
        Quiz quiz = Quiz.builder()
                .quiz_name(request.quiz_name())
                .questions(new ArrayList<>())
                .creator(null)
                .build();
        quiz = quiz_repository.save(quiz);

        ArrayList<Question> questions = new ArrayList<>();

        for(QuestionDTO question: request.questions()){
            Question new_question = question_service.createQuestion(question, quiz);
            questions.add(new_question);
        }
        quiz.setQuestions(questions);
        return quiz_repository.save(quiz).createDTO();

    }
}
