package com.skyvault05.remindme.domain;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@DynamicInsert
@DynamicUpdate
@Getter
@NoArgsConstructor
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String userEmail;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole userRole;

    @Column
    private String userPicture;

    @Column
    @ColumnDefault("1")
    private Integer status;

    @OneToOne(mappedBy = "user")
    private Friend friend;



    @Builder
    public User(String userName, String userEmail, String userPicture, UserRole userRole, Integer status){
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPicture = userPicture;
        this.userRole = userRole;
        this.status = status;
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