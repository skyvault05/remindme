package com.skyvault05.remindme.domain;

import com.skyvault05.remindme.utils.FriendListConverter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@NoArgsConstructor
public class Friend extends BaseTimeEntity implements Serializable {
    @Id
    @OneToOne
    @JoinColumn(name = "userId")
    @MapsId
    private User user;

    @Column
    @Convert(converter = FriendListConverter.class)
    private List<User> friends;
}
