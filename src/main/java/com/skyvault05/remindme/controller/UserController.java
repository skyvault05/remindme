package com.skyvault05.remindme.controller;

import com.skyvault05.remindme.domain.User;
import com.skyvault05.remindme.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/v1/addFriend/{userName}")
    public User addFriend(User user, @PathVariable String userName){
        User newFriend = userRepository.findByName(userName).orElse(null);
        System.out.println(newFriend.getEmail());
        return null;
    }
}
