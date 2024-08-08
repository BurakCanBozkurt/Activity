package com.activity.Activity.GlobalExceptionHandler;

public class StudentNotFoundException extends RuntimeException{
    public StudentNotFoundException(String message){
        super(message);
    }
}
