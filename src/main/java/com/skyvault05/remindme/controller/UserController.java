package com.skyvault05.remindme.controller;

import com.skyvault05.remindme.domain.User;
import com.skyvault05.remindme.dto.UserDto;
import com.skyvault05.remindme.repository.UserRepository;
import com.skyvault05.remindme.service.UserService;
import com.skyvault05.remindme.utils.exceptions.UserNotFoundException;
//import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(path = "${api}/user")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    private final UserService userService;

    @Operation(summary = "현재 로그인한 유저 정보 받아오기.")
    @GetMapping("/getMyInfo")
    public UserDto getMyInfo(HttpSession session){
        System.out.println("getmyinfo");
        UserDto userDto = userService.getMyInfo(session);

        return userDto;
    }

    @Operation(summary = "id로 유저 정보 찾기.")
    @PostMapping("/findUser/{id}")
    public UserDto findUser(@PathVariable Long id){
        UserDto userDto = userService.findUser(id);

        return userDto;
    }

    @Operation(summary = "nickname으로 유저 정보 찾기.")
    @GetMapping("/findUserByNickname/{nickname}")
    public UserDto findUserByNickname(@PathVariable String nickname){
        UserDto userDto = userService.findUserByNickname(nickname);

        return userDto;
    }

    @Operation(summary = "nickname으로 친구 추가.")
    @PostMapping("/addFriend/{nickname}")
    public UserDto addFriend(@PathVariable String nickname, HttpSession session){
        UserDto newUserDto = userService.addFriend(session, nickname);

        return newUserDto;
    }

    @Operation(summary = "nickname으로 친구 삭제하기.")
    @DeleteMapping("/deleteFriend/{nickname}")
    public UserDto deleteFriend(@PathVariable String nickname, HttpSession session){
        UserDto newUserDto = userService.deleteFriend(session, nickname);

        return newUserDto;
    }

    @Operation(summary = "nickname 변경하기.")
    @PostMapping("/setNickname/{nickname}")
    public UserDto setNickname(@PathVariable String nickname, HttpSession session){
        UserDto userDto = userService.setNickname(nickname, session);

        return userDto;
    }
}
