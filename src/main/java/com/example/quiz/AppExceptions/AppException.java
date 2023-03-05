package com.example.quiz.AppExceptions;

public abstract class AppException extends RuntimeException {
    private String message;
    private Integer code;

    public AppException(String message, Integer code) {
        super();
        this.message = message;
        this.code = code;
    }
    public ExceptionResponseEntity getResponseEntity(){
        return ExceptionResponseEntity.builder().message(message).code(code).build();
    }
}
