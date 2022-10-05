package com.skyvault05.remindme.utils.exceptions;

public class DuplicationException extends RuntimeException{
    public DuplicationException(){
        super();
    }

    public DuplicationException(String msg){
        super(msg);
    }
}
