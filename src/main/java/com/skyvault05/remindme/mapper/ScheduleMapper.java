package com.skyvault05.remindme.mapper;

import com.skyvault05.remindme.domain.Schedule;
import com.skyvault05.remindme.domain.ScheduleReply;
import com.skyvault05.remindme.dto.ScheduleDto;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ScheduleMapper {
    private final ScheduleReplyMapper scheduleReplyMapper;
    public Schedule scheduleDtoToSchedule(ScheduleDto scheduleDto){
        List<ScheduleReply> scheduleReplies = scheduleReplyMapper.DtoListToEntityList(scheduleDto.getScheduleReplyDto());
        Schedule schedule = Schedule.builder()
                .scheduleTitle(scheduleDto.getScheduleTitle())
                .scheduleDescription(scheduleDto.getScheduleDescription())
                .scheduleImage(scheduleDto.getScheduleImage())
                .scheduleIntervalType(scheduleDto.getScheduleIntervalType())
                .scheduleIntervalValue(scheduleDto.getScheduleIntervalValue())
                .scheduleStartDate(scheduleDto.getScheduleStartDate())
                .scheduleEndDate(scheduleDto.getScheduleEndDate())
                .scheduleUser(scheduleDto.getScheduleUser())
                .scheduleStatus(scheduleDto.getScheduleStatus())
                .build();
        return schedule;
    }
}
