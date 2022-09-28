package com.skyvault05.remindme.utils.exceptions;

public class ScheduleNotFoundException extends RuntimeException {
    public ScheduleNotFoundException(){
        super();
    }

    public ScheduleNotFoundException(String msg) {
        super(msg);
    }
}
