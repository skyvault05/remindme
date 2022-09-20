package com.skyvault05.remindme.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
public class ScheduleMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @ManyToOne
    @JoinColumn(name = "schedule")
    private Schedule schedule;

    @ManyToOne
    @JoinColumn(name = "member")
    private User member;

    @Builder
    ScheduleMember(Schedule schedule, User member){
        this.schedule = schedule;
        this.member = member;
    }

}