package com.example.quiz.Rating;


import com.example.quiz.Quiz.Quiz;
import com.example.quiz.Quiz.QuizService;
import com.example.quiz.Rating.DTO.AddRatingRequest;
import com.example.quiz.Security.Authentication.Exceptions.InvalidLoginException;
import com.example.quiz.User.User;
import com.example.quiz.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RatingService {
    private final RatingRepository rating_repository;
    private final QuizService quiz_service;
    private final UserService user_service;

    @Transactional
    public void addRatingToQuiz(AddRatingRequest request){
        Quiz quiz = quiz_service.getQuizById(request.quiz_id());
        if(quiz==null) throw new RuntimeException();
        User user = user_service.loadUserByUsername(request.username());
        if(user == null) throw new InvalidLoginException();
        Rating rating = new Rating();
        rating.setRating(request.rating());
        rating.setQuiz(quiz);
        rating.setUser(user);

        rating = rating_repository.save(rating);

        quiz.addRating(rating);
        quiz = quiz_service.saveQuiz(quiz);


        User creator = quiz.getCreator();
        creator.calcRating();
        user_service.saveUser(creator);



        user.addRating(rating);
        user_service.saveUser(user);

    }
}
