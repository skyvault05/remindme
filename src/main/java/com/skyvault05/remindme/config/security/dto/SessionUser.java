package com.skyvault05.remindme.config.security.dto;

import com.skyvault05.remindme.domain.User;
import com.skyvault05.remindme.domain.UserRole;
import com.skyvault05.remindme.dto.UserInListDto;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;

@Getter
public class SessionUser implements Serializable {
    private Long userId;
    private String name;
    private String email;
    private String picture;
    private List<UserInListDto> friend;
    private UserRole userRole;
    private Integer userStatus;

    public SessionUser(User user){
        this.userId = user.getUserId();
        this.name = user.getUserName();
        this.email = user.getUserEmail();
        this.picture = user.getUserPicture();
        this.friend = user.getUserFriend();
        this.userRole = user.getUserRole();
        this.userStatus = user.getUserStatus();
    }
}
