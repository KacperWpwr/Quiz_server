package com.example.quiz.Security.Authentication.Exceptions;

import com.example.quiz.AppExceptions.AppException;

public class LoginTakenException extends AppException {
    public LoginTakenException() {super("Given login already exists",425);}
}
