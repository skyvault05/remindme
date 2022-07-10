package com.skyvault05.remindme.controller;

import com.skyvault05.remindme.config.security.dto.OAuthAttributes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class TestController {
    @GetMapping("/test")
    public String hello(){
        return "hello";
    }

    @GetMapping("/session")
    public String getSession(HttpServletRequest req){
        HttpSession session = req.getSession();
        System.out.println(session.getId()+":"+session.getAttributeNames()+":"+session.getAttribute("OAuthAttributes"));
        return session.getId();
    }

    @GetMapping("/login")
    public String login(){
        return "/oauth2/authorization/google";
    }
}
