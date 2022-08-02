package com.skyvault05.remindme.domain;

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

    @Column
    @Convert(converter = UserListConverter.class)
    private List<User> scheduleMember;

    @Column
    @NotNull
    private String scheduleTitle;

    @Column
    private String scheduleDescription;

    @Column
    private LocalDateTime scheduleStartDate;

    @Column
    private LocalDateTime scheduleEndDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ScheduleIntervalType scheduleIntervalType;

    @Column
    private String scheduleIntervalValue;

    @OneToMany
    @JoinColumn(name = "shceduleReplyId")
    private List<ScheduleReply> scheduleReply;

    @Column
    @ColumnDefault("1")
    private Integer scheduleStatus;

    @Builder
    public Schedule(User scheduleUser, List<User> scheduleMember, String scheduleTitle, String scheduleDescription,
                    LocalDateTime scheduleStartDate, LocalDateTime scheduleEndDate, ScheduleIntervalType scheduleIntervalType,
                    String scheduleIntervalValue, List<ScheduleReply> scheduleReply, Integer scheduleStatus){
        this.scheduleUser = scheduleUser;
        this.scheduleMember = scheduleMember == null ? new LinkedList<>() : scheduleMember;
        this.scheduleTitle = scheduleTitle;
        this.scheduleDescription = scheduleDescription;
        this.scheduleStartDate = scheduleStartDate;
        this.scheduleEndDate = scheduleEndDate;
        this.scheduleIntervalType = scheduleIntervalType;
        this.scheduleIntervalValue = scheduleIntervalValue;
        this.scheduleReply = scheduleReply == null ? new LinkedList<>() : scheduleReply;
        this.scheduleStatus = scheduleStatus;
    }
}
