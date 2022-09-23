package com.skyvault05.remindme.domain;

import com.skyvault05.remindme.utils.enums.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
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
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String email;
    @Column
    private String nickname;
    @Column
    private String picture;
    @OneToMany(mappedBy = "user")
    private List<Friend> friends;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;
    @Column
    @ColumnDefault("1")
    private Integer status;

    @Builder
    public User(Long id, String name, String email, String nickname, String picture, List<Friend> friends, UserRole role, Integer status){
        this.id = id;
        this.name = name;
        this.email = email;
        this.nickname = nickname;
        this.picture = picture;
        this.friends = friends;
        this.role = role;
        this.status = status;
    }

    public User update(String name, String picture){
        this.name = name;
        this.picture = picture;
        return this;
    }

    public String getRoleKey(){
        return this.role.getKey();
    }
}