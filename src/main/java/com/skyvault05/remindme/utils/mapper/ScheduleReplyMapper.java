package com.skyvault05.remindme.utils.mapper;

import com.skyvault05.remindme.config.security.dto.SessionUser;
import com.skyvault05.remindme.domain.Schedule;
import com.skyvault05.remindme.domain.ScheduleReply;
import com.skyvault05.remindme.domain.User;
import com.skyvault05.remindme.dto.*;
import com.skyvault05.remindme.repository.ScheduleReplyRepository;
import com.skyvault05.remindme.repository.ScheduleRepository;
import com.skyvault05.remindme.repository.UserRepository;
import com.skyvault05.remindme.utils.exceptions.ScheduleNotFoundException;
import com.skyvault05.remindme.utils.exceptions.ScheduleReplyNotFoundException;
import com.skyvault05.remindme.utils.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.LinkedList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ScheduleReplyMapper {
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;
    private final ScheduleReplyRepository scheduleReplyRepository;
    private final UserMapper userMapper;
    private final ScheduleMapper scheduleMapper;
    private final HttpSession httpSession;

    @Transactional
    public ScheduleReply dtoToEntity(ScheduleReplyDto scheduleReplyDto){
        ScheduleReply scheduleReply = (scheduleReplyDto.getId() != null) ?
                scheduleReplyRepository.findById(scheduleReplyDto.getId()).orElseThrow(() -> new ScheduleReplyNotFoundException("해당 댓글을 찾을 수 없습니다.")) : new ScheduleReply();

        if(scheduleReplyDto.getUser() != null){
            scheduleReply.setUser(scheduleReplyDto.getUser().getId());
        }else {
            SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");
            User user = userRepository.findById(sessionUser.getId()).orElseThrow(() -> new UserNotFoundException("세션에 유저 정보가 없습니다"));

            scheduleReply.setUser(user.getId());
        }
        if(scheduleReplyDto.getSchedule() != null) scheduleReply.setSchedule(scheduleReplyDto.getSchedule().getId());
        if(scheduleReplyDto.getDescription() != null) scheduleReply.setDescription(scheduleReplyDto.getDescription());
        if(scheduleReplyDto.getStatus() != null) scheduleReply.setStatus(scheduleReplyDto.getStatus());

        return scheduleReply;
    }

    public ScheduleReplyDto entityToDto(ScheduleReply scheduleReply){
        User user = userRepository.findById(scheduleReply.getUser()).orElseThrow(() -> new UserNotFoundException("해당 유저를 찾을 수 없습니다."));
        SimpleUserDto simpleUserDto = userMapper.entityToSimpleDto(user);
        Schedule schedule = scheduleRepository.findById(scheduleReply.getSchedule()).orElseThrow(() -> new ScheduleNotFoundException("해당 스케쥴을 찾을 수 없습니다."));
        SimpleScheduleDto scheduleDto = scheduleMapper.entityToSimpleDto(schedule);

        return ScheduleReplyDto
                .builder()
                .id(scheduleReply.getId())
                .user(simpleUserDto)
                .schedule(scheduleDto)
                .description(scheduleReply.getDescription())
                .createdDate(scheduleReply.getCreatedDate())
                .modifiedDate(scheduleReply.getModifiedDate())
                .status(scheduleReply.getStatus())
                .build();
    }

    public List<ScheduleReplyDto> entityListToDtoList(List<ScheduleReply> list){
        if(list == null)return null;

        List<ScheduleReplyDto> newList = new LinkedList<>();

        for(ScheduleReply scheduleReply : list){
            Schedule schedule = scheduleRepository.findById(scheduleReply.getId()).orElseThrow(() -> new ScheduleReplyNotFoundException("해당 스케쥴을 찾을 수 없습니다."));
            User user = userRepository.findById(schedule.getUser()).orElseThrow(() -> new UserNotFoundException("스케쥴 작성자를 찾을 수 없습니다."));

            ScheduleReplyDto scheduleReplyDto = ScheduleReplyDto
                    .builder()
                    .id(scheduleReply.getId())
                    .user(userMapper.entityToSimpleDto(user))
                    .schedule(scheduleMapper.entityToSimpleDto(schedule))
                    .description(scheduleReply.getDescription())
                    .createdDate(scheduleReply.getCreatedDate())
                    .modifiedDate(scheduleReply.getModifiedDate())
                    .status(scheduleReply.getStatus())
                    .build();

            newList.add(scheduleReplyDto);
        }

        return newList;
    }

    public List<ScheduleReply> dtoListToEntityList(List<ScheduleReplyDto> scheduleReplyDto){
        List<ScheduleReply> newList = new LinkedList<>();

        for(ScheduleReplyDto dto : scheduleReplyDto){
            ScheduleReply scheduleReply = scheduleReplyRepository.findById(dto.getId()).orElseThrow(() -> new ScheduleReplyNotFoundException("해당 댓글을 찾을 수 없습니다."));
            if(scheduleReply != null) newList.add(scheduleReply);
        }

        return newList;
    }
}
