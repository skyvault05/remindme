package com.skyvault05.remindme.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.skyvault05.remindme.dto.UserInListDto;
import com.skyvault05.remindme.utils.converter.ReplyListConverter;
import com.skyvault05.remindme.utils.converter.UserListConverter;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Entity
@DynamicInsert
@DynamicUpdate
@Setter
@Getter
@NoArgsConstructor
public class Schedule extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User scheduleUser;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "schedule")
    private List<ScheduleMember> scheduleMember;

    @Column
    @NotNull
    private String scheduleTitle;

    @Column
    private String scheduleDescription;

    @Column
    private String scheduleImage;

    @Column
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private LocalDateTime scheduleStartDate;

    @Column
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private LocalDateTime scheduleEndDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ScheduleIntervalType scheduleIntervalType;

    @Column
    private String scheduleIntervalValue;

    @OneToMany(mappedBy = "schedule")
    private List<ScheduleReply> scheduleReply;

    @Column
    @ColumnDefault("1")
    private Integer scheduleStatus;


    @Builder
    public Schedule(User scheduleUser, String scheduleTitle, String scheduleImage,
                    String scheduleDescription, LocalDateTime scheduleStartDate,
                    LocalDateTime scheduleEndDate, ScheduleIntervalType scheduleIntervalType,
                    String scheduleIntervalValue, Integer scheduleStatus){
        this.scheduleUser = scheduleUser;
        this.scheduleTitle = scheduleTitle;
        this.scheduleImage = scheduleImage;
        this.scheduleDescription = scheduleDescription;
        this.scheduleStartDate = scheduleStartDate;
        this.scheduleEndDate = scheduleEndDate;
        this.scheduleIntervalType = scheduleIntervalType;
        this.scheduleIntervalValue = scheduleIntervalValue;
        this.scheduleStatus = scheduleStatus;
    }
}
