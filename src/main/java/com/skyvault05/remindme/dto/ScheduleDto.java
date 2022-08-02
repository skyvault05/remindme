package com.skyvault05.remindme.dto;

import com.skyvault05.remindme.domain.BaseTimeEntity;
import com.skyvault05.remindme.domain.ScheduleIntervalType;
import com.skyvault05.remindme.domain.ScheduleReply;
import com.skyvault05.remindme.domain.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleDto {
    private Long scheduleId;
    private User scheduleUser;
    private List<User> scheduleMember;
    private String scheduleTitle;
    private String scheduleDescription;
    private ScheduleIntervalType scheduleIntervalType;
    private String scheduleIntervalValue;
    private List<ScheduleReply> scheduleReply;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private Integer scheduleStatus;
}
