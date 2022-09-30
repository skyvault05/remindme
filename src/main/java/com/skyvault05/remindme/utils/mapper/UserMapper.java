package com.skyvault05.remindme.utils.mapper;


import com.skyvault05.remindme.domain.Friend;
import com.skyvault05.remindme.domain.ScheduleMember;
import com.skyvault05.remindme.domain.User;
import com.skyvault05.remindme.dto.SimpleUserDto;
import com.skyvault05.remindme.dto.UserDto;
import com.skyvault05.remindme.repository.UserRepository;
import com.skyvault05.remindme.utils.exceptions.UserNotFoundException;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@Component
@NoArgsConstructor
public class UserMapper {
    @Autowired
    private UserRepository userRepository;

    public User dtoToEntity(UserDto userDto){
        User user = userRepository.findById(userDto.getId()).orElseThrow(() -> new UserNotFoundException("해당 유저를 찾을 수 없습니다."));

        if(userDto.getName() != null) user.setName(userDto.getName());
        if(userDto.getNickname() != null) user.setNickname(userDto.getNickname());
        if(userDto.getEmail() != null)user.setEmail(userDto.getEmail());
        if(userDto.getPicture() != null)user.setPicture(userDto.getPicture());
        if(userDto.getRole() != null)user.setRole(userDto.getRole());
        if(userDto.getStatus() != null)user.setStatus(userDto.getStatus());

        return user;
    }

    public UserDto entityToDto(User user){
        UserMapper userMapper = new UserMapper();
        UserDto userDto = UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .picture(user.getPicture())
                .friends(userMapper.entityListToSimpleDtoList(userMapper.friendListToEntityList(user.getFriends())))
                .role(user.getRole())
                .createdDate(user.getCreatedDate())
                .modifiedDate(user.getModifiedDate())
                .status(user.getStatus())
                .build();
        return userDto;
    }

    public SimpleUserDto entityToSimpleDto(User user){
        return SimpleUserDto
                .builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .picture(user.getPicture())
                .status(user.getStatus())
                .build();
    }

    public List<User> dtoListToEntityList(List<UserDto> list){
        List<User> entityList = new LinkedList<>();
        for(UserDto userDto : list){
            User user = userRepository.findById(userDto.getId()).orElseThrow(() -> new UserNotFoundException("해당 유저를 찾을 수 없습니다."));
            entityList.add(user);
        }
        return entityList;
    }
    public List<UserDto> entityListToDtoList(List<User> list){
        if(list == null)return null;

        List<UserDto> dtoList = new LinkedList<>();

        for(User user : list){
            UserDto userDto = UserDto
                    .builder()
                    .id(user.getId())
                    .name(user.getName())
                    .nickname(user.getNickname())
                    .email(user.getEmail())
                    .picture(user.getPicture())
                    .role(user.getRole())
                    .createdDate(user.getCreatedDate())
                    .modifiedDate(user.getModifiedDate())
                    .status(user.getStatus())
                    .build();

            dtoList.add(userDto);
        }
        return dtoList;
    }

    public List<User> memberListToEntityList(List<ScheduleMember> memberList){
        if(memberList == null)return null;

        List<User> userList = new LinkedList<>();

        for(ScheduleMember scheduleMember : memberList){
            User member = userRepository.findById(scheduleMember.getMember()).orElseThrow(() -> new UserNotFoundException("해당 유저를 찾을 수 없습니다"));
            userList.add(member);
        }
        return userList;
    }

    public List<User> friendListToEntityList(List<Friend> friends){
        if(friends == null)return null;
        List<User> userList = new LinkedList<>();

        for(Friend friend : friends){
            User friendEntity = userRepository.findById(friend.getUser()).orElseThrow(() -> new UserNotFoundException("해당 친구를 찾을 수 없습니다."));
            userList.add(friendEntity);
        }
        return userList;
    }

    public List<SimpleUserDto> entityListToSimpleDtoList(List<User> list) {
        List<SimpleUserDto> simpleUserDtos = new LinkedList<>();

        for(User user : list){
            SimpleUserDto simpleUserDto = SimpleUserDto
                    .builder()
                    .id(user.getId())
                    .nickname(user.getNickname())
                    .picture(user.getPicture())
                    .status(user.getStatus())
                    .build();

            simpleUserDtos.add(simpleUserDto);
        }

        return simpleUserDtos;
    }
}
