package com.example.quiz.QuizHsitory.DTO;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public record AddQuizRecordRequest(@JsonAlias("username") String username, Long quiz_id,
                                   @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
                                   Date date) {
}
