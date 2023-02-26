package com.example.quiz.Quiz.Question;

import com.example.quiz.Quiz.Answer.Answer;
import com.example.quiz.Quiz.DTO.AnswerDTO;
import com.example.quiz.Quiz.DTO.QuestionDTO;
import com.example.quiz.Quiz.Quiz;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Builder
@Getter@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    @SequenceGenerator(
            name = "question_sequence",
            sequenceName = "question_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "question_sequence",
            strategy = GenerationType.SEQUENCE
    )
    @Id
    private Long id;
    private String question_text;
    @OneToMany(mappedBy = "question")
    private List<Answer> answers;
    @ManyToOne
    private Quiz quiz;

    public QuestionDTO createDTO() {
        List<AnswerDTO> answerDTOs = new ArrayList<>();
        answers.forEach(answer->{
            answerDTOs.add(answer.createDTO());
        });
        return QuestionDTO.builder().answers(answerDTOs).question_text(question_text).build();
    }
}
