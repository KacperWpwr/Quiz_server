package com.example.quiz.Security.Authentication;

import com.example.quiz.Security.Authentication.Exceptions.EmailTakenException;
import com.example.quiz.Security.Authentication.Exceptions.LoginTakenException;
import com.example.quiz.Security.Authentication.Exceptions.PasswordMissmatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class AuthenticationExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler( value = {LoginTakenException.class})
    protected ResponseEntity<Object> handleLoginTaken(LoginTakenException exception, WebRequest request){
        String body="Given login already exists";
        return handleExceptionInternal(exception,body,new HttpHeaders(), HttpStatus.valueOf(425),request);
    }

    @ExceptionHandler(value = {PasswordMissmatchException.class})
    protected ResponseEntity<Object> handlePassworMissmatch(PasswordMissmatchException exception, WebRequest request){
        String body="Given password's are not identical";
        return handleExceptionInternal(exception,body,new HttpHeaders(), HttpStatus.valueOf(426),request);
    }

    @ExceptionHandler(value = {EmailTakenException.class})
    protected ResponseEntity<Object> handleEmailTaken(EmailTakenException exception, WebRequest request){
        String body="Email is already taken";
        return handleExceptionInternal(exception,body,new HttpHeaders(), HttpStatus.valueOf(427),request);
    }
}
