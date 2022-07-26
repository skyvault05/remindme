package com.skyvault05.remindme.domain;

import com.skyvault05.remindme.utils.converter.UserListConverter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

@Entity
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
public class Friend extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "userId")
    private User user;

    @Column
    @Convert(converter = UserListConverter.class)
    private List<User> friends;

    @Builder
    public Friend(User user, List<User> friends) {
        this.user = user;
        this.friends = friends;
    }
}
