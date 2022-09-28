package com.skyvault05.remindme.utils.exceptions;

public class UserNotMatchedException extends RuntimeException {
    public UserNotMatchedException(){
        super();
    }

    public UserNotMatchedException(String msg){
        super(msg);
    }
}
