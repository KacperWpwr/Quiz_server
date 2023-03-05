package com.example.quiz.AppExceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
@AllArgsConstructor
@Builder
public class ExceptionResponseEntity {
    private String message;
    private Integer code;
}
