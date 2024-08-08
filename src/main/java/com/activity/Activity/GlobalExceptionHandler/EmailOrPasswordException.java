package com.activity.Activity.GlobalExceptionHandler;

public class EmailOrPasswordException extends RuntimeException{
    public EmailOrPasswordException(String message) {
        super(message);
    }
}
