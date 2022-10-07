package com.skyvault05.remindme.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.skyvault05.remindme.utils.enums.ScheduleIntervalType;
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
    private Long id;
    @Column
    private Long user;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "schedule")
    private List<ScheduleMember> members;
    @Column
    @NotNull
    private String title;
    @Column
    private String description;
    @Column
    private String thumbnail;
    @Column
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private LocalDateTime startDate;
    @Column
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private LocalDateTime endDate;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ScheduleIntervalType intervalType;
    @Column
    private String intervalValue;
    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ScheduleReply> scheduleReplies;
    @Column
    @ColumnDefault("1")
    private Integer status;

    @Column
    @ColumnDefault("false")
    private Boolean isDeleted;


    @Builder
    public Schedule(Long id, Long user, String title,
                    String thumbnail, String description, LocalDateTime startDate,
                    LocalDateTime endDate, ScheduleIntervalType intervalType,
                    String intervalValue, Integer status){
        this.id = id;
        this.user = user;
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
        this.startDate = startDate;
        this.endDate = endDate;
        this.intervalType = intervalType;
        this.intervalValue = intervalValue;
        this.status = status;
    }
}
