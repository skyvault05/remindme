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
    private final ScheduleMapper scheduleMapper;

    public ScheduleReply dtoToEntity(ScheduleReplyDto scheduleReplyDto){
        ScheduleReply scheduleReply = scheduleReplyRepository.findById(scheduleReplyDto.getId()).orElse(new ScheduleReply());

        if(scheduleReplyDto.getUser() != null) scheduleReply.setUser(userMapper.dtoToEntity(scheduleReplyDto.getUser()));
        if(scheduleReplyDto.getSchedule() != null) scheduleReply.setSchedule(scheduleMapper.dtoToEntity(scheduleReplyDto.getSchedule()));
        if(scheduleReplyDto.getDescription() != null) scheduleReply.setDescription(scheduleReplyDto.getDescription());
        if(scheduleReplyDto.getStatus() != null) scheduleReply.setStatus(scheduleReplyDto.getStatus());

        return scheduleReply;
    }

    public ScheduleReplyDto entityToDto(ScheduleReply scheduleReply){
        UserDto userDto = userMapper.entityToDto(scheduleReply.getUser());

        return ScheduleReplyDto
                .builder()
                .id(scheduleReply.getId())
                .user(userDto)
                .schedule(scheduleMapper.entityToDto(scheduleReply.getSchedule()))
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
            ScheduleReply scheduleReply = scheduleReplyRepository.findById(dto.getId()).orElse(null);
            if(scheduleReply != null) newList.add(scheduleReply);
        }

        return newList;
    }
}