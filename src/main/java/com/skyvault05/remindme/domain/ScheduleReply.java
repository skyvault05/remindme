package com.skyvault05.remindme.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@DynamicInsert
@DynamicUpdate
@Setter
@Getter
@NoArgsConstructor
public class ScheduleReply extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Long user;
    @Column
    private Long schedule;
    @Column
    private String description;
    @Column
    @ColumnDefault("1")
    private Integer status;

    @Builder
    public ScheduleReply (Long id, Long user, Long schedule,
                          String description, Integer status){
        this.id = id;
        this.user = user;
        this.schedule = schedule;
        this.description = description;
        this.status = status;
    }
}
