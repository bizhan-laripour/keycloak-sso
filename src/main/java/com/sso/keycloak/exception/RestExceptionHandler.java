package com.sso.keycloak.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<Object> exception(Exception exception) {
        CustomException customException = new CustomException(600 , exception.getMessage());
        return new ResponseEntity<>(customException, HttpStatus.BAD_REQUEST);
    }
}
