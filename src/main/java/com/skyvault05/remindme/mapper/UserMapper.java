package com.skyvault05.remindme.mapper;


import com.skyvault05.remindme.domain.Friend;
import com.skyvault05.remindme.domain.ScheduleMember;
import com.skyvault05.remindme.domain.User;
import com.skyvault05.remindme.dto.UserDto;
import com.skyvault05.remindme.repository.UserRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
@NoArgsConstructor
public class UserMapper {
    @Autowired
    private UserRepository userRepository;

    public User dtoToEntity(UserDto userDto){
        User user = userRepository.findById(userDto.getId()).orElse(null);

        if(userDto.getName() != null) user.setName(userDto.getName());
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
                .email(user.getEmail())
                .picture(user.getPicture())
                .friends(userMapper.entityListToDtoList(userMapper.friendListToEntityList(user.getFriends())))
                .role(user.getRole())
                .createdDate(user.getCreatedDate())
                .modifiedDate(user.getModifiedDate())
                .status(user.getStatus())
                .build();
        return userDto;
    }

    public List<User> dtoListToEntityList(List<UserDto> list){
        List<User> entityList = new LinkedList<>();
        for(UserDto userDto : list){
            User user = userRepository.findById(userDto.getId()).orElse(null);
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
            userList.add(scheduleMember.getMember());
        }
        return userList;
    }

    public List<User> friendListToEntityList(List<Friend> friends){
        List<User> userList = new LinkedList<>();

        for(Friend friend : friends){
            userList.add(friend.getUser());
        }
        return userList;
    }
}
