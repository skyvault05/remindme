package com.skyvault05.remindme.domain;

import com.skyvault05.remindme.dto.UserInListDto;
import com.skyvault05.remindme.utils.converter.UserListConverter;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String userEmail;

    @Column
    private String userPicture;

    @Column
    @Convert(converter = UserListConverter.class)
    private List<UserInListDto> userFriend;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole userRole;

    @Column
    @ColumnDefault("1")
    private Integer userStatus;

    @Builder
    public User(Long userId, String userName, String userEmail, String userPicture, List<UserInListDto> userFriend, UserRole userRole, Integer userStatus){
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPicture = userPicture;
        this.userFriend = userFriend == null ? new LinkedList<>() : userFriend;
        this.userRole = userRole;
        this.userStatus = userStatus;
    }

    public User update(String name, String picture){
        this.userName = name;
        this.userPicture = picture;
        return this;
    }

    public String getRoleKey(){
        return this.userRole.getKey();
    }
}