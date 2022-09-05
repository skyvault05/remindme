package com.skyvault05.remindme.mapper;

import com.skyvault05.remindme.domain.Schedule;
import com.skyvault05.remindme.dto.ScheduleDto;

public class ScheduleMapper {
    public static Schedule scheduleDtoToSchedule(ScheduleDto scheduleDto){
        Schedule schedule = Schedule.builder()
                .scheduleTitle(scheduleDto.getScheduleTitle())
                .scheduleDescription(scheduleDto.getScheduleDescription())
                .scheduleImage(scheduleDto.getScheduleImage())
                .scheduleIntervalType(scheduleDto.getScheduleIntervalType())
                .scheduleIntervalValue(scheduleDto.getScheduleIntervalValue())
                .scheduleStartDate(scheduleDto.getScheduleStartDate())
                .scheduleEndDate(scheduleDto.getScheduleEndDate())
                .scheduleUser(scheduleDto.getScheduleUser())
                .scheduleMember(scheduleDto.getScheduleMember())
                .scheduleReply(scheduleDto.getScheduleReply())
                .scheduleStatus(scheduleDto.getScheduleStatus())
                .build();
        return schedule;
    }
}
