package com.example.quiz.QuizHsitory;

import com.example.quiz.QuizHsitory.DTO.AddQuizRecordRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/quiz-history")
@RequiredArgsConstructor
public class QuizRecordController {
    private final QuizRecordService quiz_record_service;
    @PostMapping("/add")
    public ResponseEntity<Boolean> addQuizTOHistory(@RequestBody AddQuizRecordRequest request){
        return new ResponseEntity<>(quiz_record_service.addQuizRecordToUser(request), HttpStatusCode.valueOf(200));
    }
}
