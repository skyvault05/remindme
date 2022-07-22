package com.skyvault05.remindme.config.security.dto;

import com.skyvault05.remindme.domain.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user){
        this.name = user.getUserName();
        this.email = user.getUserEmail();
        this.picture = user.getUserPicture();
    }
}
