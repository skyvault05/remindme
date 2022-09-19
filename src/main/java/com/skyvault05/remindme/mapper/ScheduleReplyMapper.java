package com.skyvault05.remindme.mapper;

import com.skyvault05.remindme.domain.Schedule;
import com.skyvault05.remindme.domain.ScheduleReply;
import com.skyvault05.remindme.dto.ScheduleReplyDto;
import com.skyvault05.remindme.dto.UserDto;
import com.skyvault05.remindme.repository.ScheduleReplyRepository;
import com.skyvault05.remindme.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
public class ScheduleReplyMapper {
    private final ScheduleReplyRepository scheduleReplyRepository;
    private final ScheduleRepository scheduleRepository;
    private final ScheduleReplyMapper scheduleReplyMapper;
    private final UserMapper userMapper;

    public ScheduleReply dtoToEntity(ScheduleReplyDto scheduleReplyDto){
        Schedule schedule = scheduleRepository.findById(scheduleReplyDto.getSchedule()).orElse(null);
        ScheduleReply scheduleReply = ScheduleReply
                .builder()
                .id(scheduleReplyDto.getId())
                .user(userMapper.dtoToEntity(scheduleReplyDto.getUser()))
                .schedule(schedule)
                .description(scheduleReplyDto.getDescription())
                .build();
        return scheduleReply;
    }

    public ScheduleReplyDto entityToDto(ScheduleReply scheduleReply){
        UserDto scheduleReplyUser = userMapper.entityToDto(scheduleReply.getUser());
        return ScheduleReplyDto
                .builder()
                .id(scheduleReply.getId())
                .user(scheduleReplyUser)
                .schedule(scheduleReply.getSchedule().getId())
                .description(scheduleReply.getDescription())
                .createdDate(scheduleReply.getCreatedDate())
                .modifiedDate(scheduleReply.getModifiedDate())
                .status(scheduleReply.getStatus())
                .build();
    }

    public List<ScheduleReplyDto> entityListToDtoList(List<ScheduleReply> list){
        List<ScheduleReplyDto> newList = new LinkedList<>();

        for(ScheduleReply scheduleReply : list){
            ScheduleReplyDto scheduleReplyDto = scheduleReplyMapper.entityToDto(scheduleReply);
            newList.add(scheduleReplyDto);
        }

        return newList;
    }

    public List<ScheduleReply> dtoListToEntityList(List<ScheduleReplyDto> scheduleReplyDto){
        List<ScheduleReply> newList = new LinkedList<>();

        for(ScheduleReplyDto dto : scheduleReplyDto){
            ScheduleReply scheduleReply = scheduleReplyMapper.dtoToEntity(dto);
            newList.add(scheduleReply);
        }

        return newList;
    }
}
