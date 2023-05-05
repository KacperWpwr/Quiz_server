package com.example.quiz.QuizHsitory;

import com.example.quiz.Quiz.Quiz;
import com.example.quiz.Quiz.QuizService;
import com.example.quiz.QuizHsitory.DTO.AddQuizRecordRequest;
import com.example.quiz.Security.Authentication.Exceptions.InvalidLoginException;
import com.example.quiz.User.User;
import com.example.quiz.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuizRecordService {
    private final UserService user_service;
    private final QuizRecordRepository quiz_record_repository;
    private final QuizService quiz_service;
    public boolean addQuizRecordToUser(AddQuizRecordRequest request){
        User user = user_service.loadUserByUsername(request.username());
        if(user == null) throw new InvalidLoginException();
        Quiz quiz = quiz_service.getQuizById(request.quiz_id());
        QuizRecord new_record = QuizRecord.builder()
                .date(request.date())
                .user(user)
                .quiz(quiz)
                .build();
        new_record = quiz_record_repository.save(new_record);
        user.addQuizRecord(new_record);
        user_service.saveUser(user);
        return true;
    }

}
