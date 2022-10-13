package com.skyvault05.remindme.service;

import com.skyvault05.remindme.config.security.dto.SessionUser;
import com.skyvault05.remindme.domain.Friend;
import com.skyvault05.remindme.domain.User;
import com.skyvault05.remindme.dto.UserDto;
import com.skyvault05.remindme.repository.FriendRepository;
import com.skyvault05.remindme.utils.exceptions.FriendDuplicationException;
import com.skyvault05.remindme.utils.mapper.UserMapper;
import com.skyvault05.remindme.repository.UserRepository;
import com.skyvault05.remindme.utils.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final FriendRepository friendRepository;

    public UserDto findUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("No Such User"));
        UserDto userDto = userMapper.entityToDto(user);

        return userDto;
    }

    public UserDto addFriend(HttpSession session, String nickname) {
        User friendUser = userRepository.findByNickname(nickname).orElseThrow(() -> new UserNotFoundException("해당 유저를 찾을 수 없습니다."));
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");
        if(friendRepository.findFriendByUserAndFriend(sessionUser.getId(), friendUser.getId()).orElse(null) != null){
            throw new FriendDuplicationException("이미 추가된 친구입니다.");
        }
        Friend friend = Friend
                .builder()
                .user(sessionUser.getId())
                .friend(friendUser.getId())
                .build();

        friendRepository.save(friend);
        log.info("유저: " + friend.getUser() + ", 친구추가: " + friend.getFriend());

        User newUser = userRepository.findById(sessionUser.getId()).orElseThrow(() -> new UserNotFoundException("해당 유저를 찾을 수 없습니다."));
        UserDto newUserDto = userMapper.entityToDto(newUser);
        return newUserDto;
    }

    public UserDto setNickname(String nickname, HttpSession session) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");
        User user = userRepository.findById(sessionUser.getId()).orElseThrow(() -> new UserNotFoundException("해당 유저를 찾을 수 없습니다."));
        user.setNickname(nickname);
        userRepository.save(user);

        UserDto userDto = userMapper.entityToDto(user);

        log.info("유저: " + user.getId() + ", 닉네임설정: " + nickname);
        return userDto;
    }

    public UserDto deleteFriend(HttpSession session, String nickname) {
        User friendUser = userRepository.findByNickname(nickname).orElseThrow(() -> new UserNotFoundException("해당 유저를 찾을 수 없습니다."));
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");
        Friend friend = friendRepository.findFriendByUserAndFriend(sessionUser.getId(), friendUser.getId()).orElse(null);
        if(friend == null) {
            User user = userRepository.findById(sessionUser.getId()).orElseThrow(() -> new UserNotFoundException("해당 유저를 찾을 수 없습니다."));
            return userMapper.entityToDto(user);
        }

        friendRepository.delete(friend);


        User user = userRepository.findById(sessionUser.getId()).orElseThrow(() -> new UserNotFoundException("해당 유저를 찾을 수 없습니다."));

        log.info("유저: " + friend.getUser() + ", 친구삭제: " + friend.getFriend());
        return userMapper.entityToDto(user);
    }
}