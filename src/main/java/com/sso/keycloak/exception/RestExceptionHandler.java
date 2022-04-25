package com.sso.keycloak.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(value = CustomException.class)
    public ResponseEntity<Object> exception(CustomException exception) {
        ExceptionResult exceptionResult = new ExceptionResult(exception.getException() , exception.getStatusCode() , new Date());
        return new ResponseEntity<>(exceptionResult, HttpStatus.BAD_REQUEST);
    }
}
