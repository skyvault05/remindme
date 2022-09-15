package com.skyvault05.remindme.mapper;

import com.skyvault05.remindme.domain.Schedule;
import com.skyvault05.remindme.domain.ScheduleReply;
import com.skyvault05.remindme.domain.User;
import com.skyvault05.remindme.dto.ScheduleDto;
import com.skyvault05.remindme.dto.ScheduleReplyDto;
import com.skyvault05.remindme.dto.UserDto;
import com.skyvault05.remindme.repository.ScheduleReplyRepository;
import com.skyvault05.remindme.repository.ScheduleRepository;
import com.skyvault05.remindme.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
public class ScheduleReplyMapper {
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final ScheduleReplyRepository scheduleReplyRepository;
    private final ScheduleReplyMapper scheduleReplyMapper;
    private final UserMapper userMapper;

    public ScheduleReply dtoToEntity(ScheduleReplyDto scheduleReplyDto){
        ScheduleReply scheduleReply = scheduleReplyRepository.findById(scheduleReplyDto.getScheduleReplyId()).orElse(null);
        return scheduleReply;
    }

    public ScheduleReplyDto entityToDto(ScheduleReply scheduleReply){
        UserDto scheduleReplyUser = userMapper.userToUserDto(scheduleReply.getScheduleReplyUser());
        return ScheduleReplyDto
                .builder()
                .scheduleReplyId(scheduleReply.getScheduleReplyId())
                .scheduleReplyUser(scheduleReplyUser)
                .scheduleId(scheduleReply.getSchedule().getScheduleId())
                .scheduleReplyDescription(scheduleReply.getScheduleReplyDescription())
                .createdDate(scheduleReply.getCreatedDate())
                .modifiedDate(scheduleReply.getModifiedDate())
                .scheduleReplyStatus(scheduleReply.getScheduleReplyStatus())
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

    public List<ScheduleReply> DtoListToEntityList(List<ScheduleReplyDto> scheduleReplyDto){
        List<ScheduleReply> newList = new LinkedList<>();

        for(ScheduleReplyDto dto : scheduleReplyDto){
            ScheduleReply scheduleReply = scheduleReplyMapper.dtoToEntity(dto);
            newList.add(scheduleReply);
        }

        return newList;
    }
}
