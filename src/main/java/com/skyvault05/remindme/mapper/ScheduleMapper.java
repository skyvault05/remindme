package com.skyvault05.remindme.mapper;

import com.skyvault05.remindme.domain.Schedule;
import com.skyvault05.remindme.domain.ScheduleReply;
import com.skyvault05.remindme.domain.User;
import com.skyvault05.remindme.dto.ScheduleDto;
import com.skyvault05.remindme.dto.UserDto;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ScheduleMapper {
    private final ScheduleReplyMapper scheduleReplyMapper;
    private final UserMapper userMapper;
    public Schedule dtoToEntity(ScheduleDto scheduleDto){
        List<ScheduleReply> scheduleReplies = scheduleReplyMapper.dtoListToEntityList(scheduleDto.getScheduleReplies());
        User user = userMapper.dtoToEntity(scheduleDto.getUser());
        Schedule schedule = Schedule.builder()
                .id(scheduleDto.getId())
                .user(user)
                .title(scheduleDto.getTitle())
                .description(scheduleDto.getDescription())
                .thumbnail(scheduleDto.getThumbnail())
                .intervalType(scheduleDto.getIntervalType())
                .intervalValue(scheduleDto.getIntervalValue())
                .startDate(scheduleDto.getStartDate())
                .endDate(scheduleDto.getEndDate())
                .status(scheduleDto.getStatus())
                .build();
        return schedule;
    }

    public ScheduleDto entityToDto(Schedule schedule){
        UserDto userDto = userMapper.entityToDto(schedule.getUser());
        List<UserDto> members = userMapper.entityListToDtoList(userMapper.memberListToUserList(schedule.getMembers()));
        ScheduleDto scheduleDto = ScheduleDto
                .builder()
                .id(schedule.getId())
                .user(userDto)
                .members(members)
                .title(schedule.getTitle())
                .description(schedule.getDescription())
                .thumbnail(schedule.getThumbnail())
                .intervalType(schedule.getIntervalType())
                .intervalValue(schedule.getIntervalValue())
                .scheduleReplies(scheduleReplyMapper.entityListToDtoList(schedule.getScheduleReplies()))
                .startDate(schedule.getStartDate())
                .endDate(schedule.getEndDate())
                .status(schedule.getStatus())
                .build();
        return scheduleDto;
    }
}
