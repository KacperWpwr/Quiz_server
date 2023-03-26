package com.example.quiz.Quiz;

import com.example.quiz.Quiz.DTO.QuestionDTO;
import com.example.quiz.Quiz.DTO.QuizDisplayDTO;
import com.example.quiz.Quiz.DTO.QuizInfoDTO;
import com.example.quiz.Quiz.Question.Question;
import com.example.quiz.Rating.DTO.RatingDisplayDTO;
import com.example.quiz.Rating.Rating;
import com.example.quiz.User.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table
@Builder
@Getter@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Quiz {
    @SequenceGenerator(
            name = "quiz_sequence",
            sequenceName = "quiz_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "quiz_sequence",
            strategy = GenerationType.SEQUENCE
    )
    @Id private Long id;
    private String quiz_name;
    @OneToMany(mappedBy = "quiz")
    private List<Question> questions;
    @ManyToOne
    private User creator;

    @OneToMany(mappedBy = "quiz")
    private Set<Rating> ratings;

    @Builder.Default
    private Double overall_rating=0.0;

    public QuizDisplayDTO createDisplayDTO() {
        List<QuestionDTO> questionDTOs = questions.stream().map(Question::createDTO).toList();


        return QuizDisplayDTO.builder().name(quiz_name).creator_username(creator.getUsername()).id(id).questions(questionDTOs).build();

    }
    public QuizInfoDTO createInfoDTO(){
        return QuizInfoDTO.builder()
                .creator_username(creator.getUsername())
                .name(quiz_name)
                .id(id)
                .question_amount(questions.size())
                .rating(overall_rating)
                .build();
    }
    public void addRating(Rating rating){
        ratings.add(rating);
        calcOverallRating();
    }
    public void calcOverallRating(){
        Double sum =0.0;
        for(Rating rating: ratings){
            sum+=rating.getRating();
        }
        if(sum>0){
            overall_rating = sum/ratings.size();
        }else{
            overall_rating =0.0;
        }
    }
    public List<RatingDisplayDTO> getQuizRatings(){
        return ratings.stream().map(Rating::getDisplayDTO).toList();
    }
}
