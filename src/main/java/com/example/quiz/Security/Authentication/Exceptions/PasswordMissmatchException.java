package com.example.quiz.Security.Authentication.Exceptions;

import com.example.quiz.AppExceptions.AppException;

public class PasswordMissmatchException extends AppException {
    public PasswordMissmatchException() {super("Given passwords are not identical",426);}
}
