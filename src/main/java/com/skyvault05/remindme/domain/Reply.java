package com.skyvault05.remindme.domain;

import javax.persistence.*;

@Entity
public class Reply extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long replyId;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User replyUser;

    @ManyToOne
    @JoinColumn(name = "scheduleId")
    private Schedule schedule;

    @Column
    private String description;

    @Column
    private Integer status;
}
