package com.example.quiz.Security.Authentication.Exceptions;

import com.example.quiz.AppExceptions.AppException;

public class EmailTakenException extends AppException {
    public EmailTakenException() {super("Email is already taken",427);}
}
