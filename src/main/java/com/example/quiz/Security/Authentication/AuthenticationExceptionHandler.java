package com.example.quiz.Security.Authentication;

import com.example.quiz.AppExceptions.AppException;
import com.example.quiz.AppExceptions.ExceptionResponseEntity;
import com.example.quiz.Security.Authentication.Exceptions.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class AuthenticationExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler( value = {LoginTakenException.class,PasswordMissmatchException.class,EmailTakenException.class,
            InvalidLoginException.class,InvalidPasswordException.class})
    protected ResponseEntity<Object> handleLoginTaken(AppException exception, WebRequest request){
        return handleExceptionInternal(exception,exception.getResponseEntity(),new HttpHeaders(), HttpStatus.valueOf(409),request);
    }


}

