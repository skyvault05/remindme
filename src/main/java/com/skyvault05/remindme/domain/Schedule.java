package com.skyvault05.remindme.domain;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
public class Schedule extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany
    @JoinColumn(name = "userId")
    private List<User> members;

    @Column
    @NotNull
    private String title;

    @Column
    private String description;

    @Column
    private LocalDateTime startDate;

    @Column
    private LocalDateTime endTime;

    @Column
    private String intervalType;

    @Column
    private String intervalValue;

    @OneToMany
    @JoinColumn(name = "replyId")
    private List<Reply> replys;

    @Column
    private Integer status;

    @Builder
    public Schedule(List<User> members, String title, String description,
                    LocalDateTime startDate, LocalDateTime endTime, String intervalType,
                    String intervalValue, List<Reply> replys, Integer status){
        this.members = members;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endTime = endTime;
        this.intervalType = intervalType;
        this.intervalValue = intervalValue;
        this.replys = replys;
        this.status = status;
    }
}
