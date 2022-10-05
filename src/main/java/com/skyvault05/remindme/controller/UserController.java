package com.skyvault05.remindme.controller;

import com.skyvault05.remindme.domain.User;
import com.skyvault05.remindme.dto.UserDto;
import com.skyvault05.remindme.repository.UserRepository;
import com.skyvault05.remindme.service.UserService;
import com.skyvault05.remindme.utils.exceptions.UserNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(path = "${api}/user")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    private final UserService userService;

    @Operation(summary = "id로 유저 찾기.")
    @PostMapping("/findUser/{id}")
    public UserDto findUser(@PathVariable Long id){
        UserDto userDto = userService.findUser(id);

        return userDto;
    }

    @Operation(summary = "친구 추가.")
    @PostMapping("/addFriend/{nickname}")
    public UserDto addFriend(@PathVariable String nickname, HttpSession session){
        UserDto newUserDto = userService.addFriend(session, nickname);

        return newUserDto;
    }

    @Operation(summary = "친구 삭제하기.")
    @PostMapping("/deleteFriend/{nickname}")
    public UserDto deleteFriend(@PathVariable String nickname, HttpSession session){
        UserDto newUserDto = userService.deleteFriend(session, nickname);

        return newUserDto;
    }

    @PostMapping("/setNickname/{nickname}")
    public UserDto setNickname(@PathVariable String nickname, HttpSession session){
        UserDto userDto = userService.setNickname(nickname, session);

        return userDto;
    }
}
