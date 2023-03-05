package com.example.quiz.Security.Authentication.Exceptions;

import com.example.quiz.AppExceptions.AppException;

public class InvalidLoginException extends AppException {
    public InvalidLoginException() {super("Invalid login",429);}
}
