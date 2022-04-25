package com.sso.keycloak.exception;

public class CustomException extends RuntimeException{
    private int statusCode;

    private String exception;

    public CustomException(int statusCode , String exception){
        this.exception = exception;
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }
}
