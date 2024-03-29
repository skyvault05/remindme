package com.skyvault05.remindme.config.security.dto;

import com.skyvault05.remindme.utils.enums.UserRole;
import com.skyvault05.remindme.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private Long userId;
    private String name;
    private String email;
    private String picture;
    private UserRole role;
    private Integer status;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes,
                           String nameAttributeKey,Long userId,
                           String name, String email,
                           String picture, UserRole role,
                           Integer status){
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
        this.status = status;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributesName, Map<String, Object> attributes) {
        return ofGoogle(userNameAttributesName, attributes);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes){
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public User toEntity() {
        if(role == null) role = UserRole.USER;
        return User.builder()
                .id(userId)
                .name(name)
                .email(email)
                .picture(picture)
                .role(role)
                .status(status)
                .build();
    }
}
