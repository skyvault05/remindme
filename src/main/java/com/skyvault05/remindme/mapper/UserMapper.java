package com.skyvault05.remindme.mapper;

import com.skyvault05.remindme.domain.User;
import com.skyvault05.remindme.dto.UserDto;
import com.skyvault05.remindme.dto.UserInListDto;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User userDtoToUser(UserDto userDTO){
        User user = User.builder()
                .userId(userDTO.getUserId())
                .userName(userDTO.getUserName())
                .userEmail(userDTO.getUserEmail())
                .userPicture(userDTO.getUserPicture())
                .userFriend(userDTO.getUserFriend())
                .userRole(userDTO.getUserRole())
                .userStatus(userDTO.getUserStatus())
                .build();

        return user;
    }

    public UserDto userToUserDto(User user){
        UserDto userDto = UserDto.builder()
                .userId(user.getUserId())
                .userName(user.getUserName())
                .userEmail(user.getUserEmail())
                .userPicture(user.getUserPicture())
                .userFriend(user.getUserFriend())
                .userRole(user.getUserRole())
                .createdDate(user.getCreatedDate())
                .modifiedDate(user.getModifiedDate())
                .build();
        return userDto;
    }

    public UserInListDto userToUserInListDto(User user){
        UserInListDto userInListDto = UserInListDto.builder()
                .userId(user.getUserId())
                .userName(user.getUserName())
                .userEmail(user.getUserEmail())
                .userPicture(user.getUserPicture())
                .build();
        return userInListDto;
    }
}
