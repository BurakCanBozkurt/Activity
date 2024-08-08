package com.activity.Activity.GlobalExceptionHandler;

public class StudentAlreadyExistsException extends RuntimeException{
    public StudentAlreadyExistsException(String message){
        super(message);
    }
}