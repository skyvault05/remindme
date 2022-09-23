package com.skyvault05.remindme.utils.exceptions;

public class ValidationException extends RuntimeException{
    public ValidationException(){
        super();
    }

    public ValidationException(String msg){
        super(msg);
    }
}
