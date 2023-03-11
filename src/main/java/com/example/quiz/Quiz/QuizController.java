package com.example.quiz.Quiz;

import com.example.quiz.Quiz.DTO.CreateQuizRequest;
import com.example.quiz.Quiz.DTO.QuizDisplayDTO;
import com.example.quiz.Quiz.DTO.QuizInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/search/strict/{name}")
    public ResponseEntity<List<QuizInfoDTO>> getQuizByNameStrict(@PathVariable String name){
        return new ResponseEntity<>(quiz_service.strictNameSearch(name), HttpStatusCode.valueOf(200));
    }
    @GetMapping("/search/advanced/{name}")
    public ResponseEntity<List<QuizInfoDTO>> getQuizByNameAdvanced(@PathVariable String name){
        return new ResponseEntity<>(quiz_service.advancedSearch(name),HttpStatusCode.valueOf(200));
    }

}
