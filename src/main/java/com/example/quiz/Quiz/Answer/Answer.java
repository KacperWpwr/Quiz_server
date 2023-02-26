package com.example.quiz.Quiz.Answer;

import com.example.quiz.Quiz.DTO.AnswerDTO;
import com.example.quiz.Quiz.Question.Question;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;

@Entity
@Table
@Builder
@Getter@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Answer {
    @SequenceGenerator(
            name = "answer_sequence",
            sequenceName = "answer_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "answer_sequence",
            strategy = GenerationType.SEQUENCE
    )
    @Id
    private Long id;
    private String answer_text;
    private Boolean is_correct;
    @ManyToOne
    private Question question;

    public AnswerDTO createDTO() {
        return AnswerDTO.builder().answer_text(answer_text).is_correct(is_correct).build();
    }
}
