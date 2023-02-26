package com.example.quiz.Quiz.Answer;

import com.example.quiz.Quiz.DTO.AnswerDTO;
import com.example.quiz.Quiz.Question.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answer_repository;

    public Answer createAnswer(AnswerDTO answer, Question new_question) {
        Answer new_answer = Answer.builder()
                .answer_text(answer.answer_text())
                .question(new_question)
                .is_correct(answer.is_correct())
                .build();
        return answer_repository.save(new_answer);

    }
}
