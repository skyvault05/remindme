package com.skyvault05.remindme.controller.exceptionHandler;

import com.skyvault05.remindme.utils.exceptions.TestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
@Slf4j
public class ExceptionHandlingController {

    @ResponseStatus(HttpStatus.GATEWAY_TIMEOUT)
    @ExceptionHandler(TestException.class)
    public void handleError(HttpServletRequest req, Exception ex) {
        log.error("Request: " + req.getRequestURL() + " raised " + ex);

//        ModelAndView mav = new ModelAndView();
//        mav.addObject("exception", ex);
//        System.out.println(ex.getMessage());
//        mav.addObject("url", req.getRequestURL());
//        System.out.println(req.getRequestURL());
//        mav.setViewName("error");d
//
//        return mav;
    }
}
