package com.example.quiz.Rating;


import com.example.quiz.Quiz.Quiz;
import com.example.quiz.Rating.DTO.RatingDisplayDTO;
import com.example.quiz.User.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Getter@Setter
@NoArgsConstructor
public class Rating {
    @SequenceGenerator(
            sequenceName = "rating_sequence",
            name = "rating_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "rating_sequence",
            strategy = GenerationType.SEQUENCE
    )
    @Id
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Quiz quiz;

    private Integer rating;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rating rating = (Rating) o;
        return Objects.equals(quiz.getId(), rating.quiz.getId()) && Objects.equals(user, rating.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(quiz.getId(), user.hashCode());
    }
    public RatingDisplayDTO getDisplayDTO(){
        return RatingDisplayDTO.builder()
                .rating(rating)
                .username(user.getUsername())
                .quiz_name(quiz.getQuiz_name())
                .build();
    }
}
