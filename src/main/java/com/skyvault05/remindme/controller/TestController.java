package com.skyvault05.remindme.controller;

import com.skyvault05.remindme.domain.User;
import com.skyvault05.remindme.dto.UserDto;
import com.skyvault05.remindme.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class TestController {
    @Autowired
    private UserRepository userRepository;

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

    @PostMapping("/v1/insertusertest")
    public UserDto insertUserTest(UserDto userDTO){
        System.out.println(userDTO.getUserId()+":"+userDTO.getUserEmail()+":"+ userDTO.getUserName());
        return userDTO;
    }

    @PostMapping("/v1/usertest")
    public User userTest(UserDto userDTO){
        System.out.println(userDTO.getUserId()+":"+userDTO.getUserEmail()+":"+ userDTO.getUserName());
        User user = userRepository.findById(3L).orElse(null);
        return user;
    }

    @PostMapping("/v1/gettest")
    public User gettingUser(@RequestBody User user){
//        System.out.println(user.getUserId()+":"+user.getUserEmail()+":"+ user.getUserName());
        System.out.println(user.getUserId());
        return user;
    }
}
