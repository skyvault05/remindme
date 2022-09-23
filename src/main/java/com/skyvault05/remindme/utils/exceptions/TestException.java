package com.skyvault05.remindme.utils.exceptions;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No Reason Just Test")
public class TestException extends RuntimeException{
    public TestException(){ super(); }

    public TestException(String msg){
        super(msg);
    }

}
