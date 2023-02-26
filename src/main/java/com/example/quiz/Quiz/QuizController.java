package com.example.quiz.Quiz;

import com.example.quiz.Quiz.DTO.CreateQuizRequest;
import com.example.quiz.Quiz.DTO.QuizDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("api/quiz")
@RequiredArgsConstructor
public class QuizController {
    private final QuizService quiz_service;
    @PostMapping("/create")
    public QuizDTO createNewQuiz(@RequestBody CreateQuizRequest request){
        return quiz_service.createQuiz(request);
    }
}
