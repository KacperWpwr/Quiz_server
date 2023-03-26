package com.example.quiz.QuizHsitory.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public record QuizRecordDTO(
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
        Date date
        , String quiz_name
        , String creator_username
        , Integer question_number) {
}
