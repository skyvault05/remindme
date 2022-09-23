package com.skyvault05.remindme.utils.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(){
        super();
    }

    public UserNotFoundException(String msg){
        super(msg);
    }
    public UserNotFoundException(Long id){
        super();

        String msg = id + "번 유저가 존재하지 않습니다.";
        log.warn(msg);
    }
}
