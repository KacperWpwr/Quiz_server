package com.example.quiz.QuizHsitory;


import com.example.quiz.Quiz.Quiz;
import com.example.quiz.QuizHsitory.DTO.QuizRecordDTO;
import com.example.quiz.User.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Objects;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuizRecord {
    @SequenceGenerator(
            name = "quiz_record_sequence",
            sequenceName = "quiz_record_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "quiz_record_sequence",
            strategy = GenerationType.SEQUENCE
    )
    @Id
    private Long id;
    @ManyToOne
    private User user;
    @ManyToOne
    private Quiz quiz;
    private Date date;

    public QuizRecordDTO getDTO(){
        return new QuizRecordDTO(date, quiz.getQuiz_name());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuizRecord that = (QuizRecord) o;
        return user.getLogin().equals(that.user.getLogin()) && quiz.getId().equals(that.quiz.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(user.hashCode(), quiz.getId());
    }
}
