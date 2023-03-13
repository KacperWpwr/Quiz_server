package com.example.quiz.User.Exceptions;

import com.example.quiz.AppExceptions.AppException;

public abstract class UserException extends AppException {
    public UserException(String message, Integer code) {
        super(message, code);
    }
}
