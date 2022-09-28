package com.skyvault05.remindme.utils.exceptions;

public class ScheduleReplyNotFoundException extends RuntimeException {
    public ScheduleReplyNotFoundException(){
        super();
    }

    public ScheduleReplyNotFoundException(String msg){
        super(msg);
    }
}
