package com.skyvault05.remindme.service;

import com.skyvault05.remindme.domain.User;
import com.skyvault05.remindme.dto.UserDto;
import com.skyvault05.remindme.utils.mapper.UserMapper;
import com.skyvault05.remindme.repository.UserRepository;
import com.skyvault05.remindme.utils.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    public UserDto findUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("No Such User"));
        UserDto userDto = userMapper.entityToDto(user);

        return userDto;
    }
}
