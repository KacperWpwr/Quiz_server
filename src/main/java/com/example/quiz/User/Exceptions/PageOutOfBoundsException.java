package com.example.quiz.User.Exceptions;

public class PageOutOfBoundsException extends UserException{
    public PageOutOfBoundsException() {
        super("page number is out of bounds", 431);
    }
}
