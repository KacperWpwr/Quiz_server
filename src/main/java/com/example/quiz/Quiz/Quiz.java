package com.example.quiz.Quiz;

import com.example.quiz.Quiz.DTO.QuestionDTO;
import com.example.quiz.Quiz.DTO.QuizDisplayDTO;
import com.example.quiz.Quiz.DTO.QuizInfoDTO;
import com.example.quiz.Quiz.Question.Question;
import com.example.quiz.User.User;
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

    public QuizDisplayDTO createDisplayDTO() {
        List<QuestionDTO> questionDTOs = new ArrayList<>();
        questions.forEach(question->{
            questionDTOs.add(question.createDTO());
        });

        return QuizDisplayDTO.builder().name(quiz_name).questions(questionDTOs).build();

    }
    public QuizInfoDTO createInfoDTO(){
        return QuizInfoDTO.builder()
                .creator_username(creator.getUsername())
                .quiz_name(quiz_name)
                .id(id)
                .question_amount(questions.size())
                .build();
    }
}
