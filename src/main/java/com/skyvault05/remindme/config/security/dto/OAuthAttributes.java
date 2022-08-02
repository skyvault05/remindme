package com.skyvault05.remindme.config.security.dto;

import com.skyvault05.remindme.domain.UserRole;
import com.skyvault05.remindme.domain.User;
import com.skyvault05.remindme.dto.UserInListDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private Long userId;
    private String name;
    private String email;
    private String picture;
    private List<UserInListDto> userFriend;
    private UserRole userRole;
    private Integer userStatus;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes,
                           String nameAttributeKey,Long userId,
                           String name, String email,
                           String picture, List<UserInListDto> friend,
                           UserRole userRole, Integer userStatus){
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.userFriend = friend;
        this.userRole = userRole;
        this.userStatus = userStatus;
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
        if(userRole == null) userRole = UserRole.USER;
        return User.builder()
                .userId(userId)
                .userName(name)
                .userEmail(email)
                .userPicture(picture)
                .userFriend(userFriend)
                .userRole(userRole)
                .userStatus(userStatus)
                .build();
    }
}
