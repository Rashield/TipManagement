package com.example.TipsManagement.Exception;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String message){
        super (message);
    }
}
