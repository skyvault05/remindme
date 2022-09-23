package com.skyvault05.remindme.config.security.dto;

import com.skyvault05.remindme.domain.User;
import com.skyvault05.remindme.utils.enums.UserRole;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private Long id;
    private String name;
    private String email;
    private String picture;
    private UserRole userRole;
    private Integer userStatus;

    public SessionUser(User user){
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
        this.userRole = user.getRole();
        this.userStatus = user.getStatus();
    }
}
