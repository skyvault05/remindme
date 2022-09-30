package com.skyvault05.remindme.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor
public class Friend extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Long user;
    @Column
    private Long friend;
    @Builder
    public Friend(Long id, Long user, Long friend){
        this.id = id;
        this.user = user;
        this.friend = friend;
    }
}
