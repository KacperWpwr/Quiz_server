package com.example.quiz.Security.Authentication.Exceptions;

import com.example.quiz.AppExceptions.AppException;

public class InvalidPasswordException extends AppException {
    public InvalidPasswordException() {super("Invalid password",430);}
}
