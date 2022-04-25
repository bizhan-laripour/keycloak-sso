package com.sso.keycloak.exception;

import java.util.Date;

public class ExceptionResult {

    private String message;

    private int statusCode;

    private Date date;

    public ExceptionResult(String message , int statusCode , Date date){
        this.message = message;
        this.statusCode = statusCode;
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
