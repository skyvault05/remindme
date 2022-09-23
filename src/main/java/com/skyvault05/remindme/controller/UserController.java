package com.skyvault05.remindme.controller;

import com.skyvault05.remindme.domain.User;
import com.skyvault05.remindme.dto.UserDto;
import com.skyvault05.remindme.repository.UserRepository;
import com.skyvault05.remindme.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "${api}/user")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    private final UserService userService;

    @PostMapping("/addFriend/{userName}")
    public User addFriend(User user, @PathVariable String userName){
        User newFriend = userRepository.findByName(userName).orElse(null);
        System.out.println(newFriend.getEmail());
        return null;
    }

    @PostMapping("/findUser/{id}")
    public UserDto findUser(@PathVariable Long id){
        UserDto userDto = userService.findUser(id);

        return userDto;
    }
}
