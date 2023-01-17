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
    @Column
    private Long schedule;
    @Column
    private Long member;
    @Column
    @ColumnDefault("false")
    private Boolean acceptance;
    @Builder
    ScheduleMember(Long schedule, Long member, Boolean acceptance){
        this.schedule = schedule;
        this.member = member;
        this.acceptance = acceptance;
    }

}
