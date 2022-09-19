package com.skyvault05.remindme.mapper;

import com.skyvault05.remindme.domain.ScheduleMember;
import com.skyvault05.remindme.domain.User;
import com.skyvault05.remindme.dto.UserDto;
import com.skyvault05.remindme.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserMapper {
//    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public User dtoToEntity(UserDto userDTO){
        User user = User.builder()
                .id(userDTO.getId())
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .picture(userDTO.getPicture())
//                .friends(userMapper.dtoListToEntityList(userDTO.getFriends()))
                .role(userDTO.getRole())
                .status(userDTO.getStatus())
                .build();

        return user;
    }

    public UserDto entityToDto(User user){
        UserDto userDto = UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .picture(user.getPicture())
//                .friends(userMapper.entityListToDtoList(user.getFriends()))
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

    public List<User> memberListToUserList(List<ScheduleMember> memberList){
        List<User> userList = new LinkedList<>();

        for(ScheduleMember scheduleMember : memberList){
            userList.add(scheduleMember.getMember());
        }
        return userList;
    }
}
