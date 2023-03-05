package com.example.quiz.Quiz;

import com.example.quiz.Quiz.DTO.CreateQuizRequest;
import com.example.quiz.Quiz.DTO.QuizDisplayDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("api/quiz")
@RequiredArgsConstructor
public class QuizController {
    private final QuizService quiz_service;
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public QuizDisplayDTO createNewQuiz(@RequestBody CreateQuizRequest request){
        return quiz_service.createQuiz(request);
    }
    @GetMapping("/get/id/{id}")
    public QuizDisplayDTO getQuizById(@PathVariable Long id){
        return quiz_service.getQuizById(id);
    }
}
