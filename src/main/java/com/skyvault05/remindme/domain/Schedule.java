package com.skyvault05.remindme.domain;

import com.skyvault05.remindme.utils.converter.UserListConverter;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
public class Schedule extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @Column
    @Convert(converter = UserListConverter.class)
    private List<User> member;

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
    public Schedule(List<User> member, String title, String description,
                    LocalDateTime startDate, LocalDateTime endTime, String intervalType,
                    String intervalValue, List<Reply> replys, Integer status){
        this.member = member;
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
