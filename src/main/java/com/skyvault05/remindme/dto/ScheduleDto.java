package com.skyvault05.remindme.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
@ToString
public class ScheduleDto {
    private Long scheduleId;
    private User scheduleUser;
    private List<UserInListDto> scheduleMember;
    private String scheduleTitle;
    private String scheduleImage;
    private String scheduleDescription;
    private ScheduleIntervalType scheduleIntervalType;
    private String scheduleIntervalValue;
    private List<ScheduleReply> scheduleReply;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private LocalDateTime scheduleEndDate;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private LocalDateTime scheduleStartDate;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private LocalDateTime createdDate;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private LocalDateTime modifiedDate;
    private Integer scheduleStatus;
}
