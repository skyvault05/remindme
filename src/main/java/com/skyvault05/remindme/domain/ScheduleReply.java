package com.skyvault05.remindme.domain;

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
    private Long scheduleReplyId;
    @ManyToOne
    @JoinColumn(name = "userId")
    private User scheduleReplyUser;
    @ManyToOne
    @JoinColumn(name = "scheduleId")
    private Schedule schedule;
    @Column
    private String scheduleReplyDescription;
    @Column
    @ColumnDefault("1")
    private Integer scheduleReplyStatus;

    @Builder
    public ScheduleReply (User scheduleReplyUser, Schedule schedule,
                          String scheduleReplyDescription, Integer scheduleReplyStatus){
        this.scheduleReplyUser = scheduleReplyUser;
        this.schedule = schedule;
        this.scheduleReplyDescription = scheduleReplyDescription;
        this.scheduleReplyStatus = scheduleReplyStatus;
    }
}
