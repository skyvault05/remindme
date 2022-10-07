package com.skyvault05.remindme.utils.mapper;

import com.skyvault05.remindme.config.security.dto.SessionUser;
import com.skyvault05.remindme.domain.Schedule;
import com.skyvault05.remindme.domain.User;
import com.skyvault05.remindme.dto.ScheduleDto;
import com.skyvault05.remindme.dto.SimpleScheduleDto;
import com.skyvault05.remindme.dto.SimpleUserDto;
import com.skyvault05.remindme.repository.ScheduleRepository;
import com.skyvault05.remindme.repository.UserRepository;
import com.skyvault05.remindme.utils.exceptions.ScheduleNotFoundException;
import com.skyvault05.remindme.utils.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.LinkedList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ScheduleMapper {
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;
    private final UserMapper userMapper;
    private final HttpSession httpSession;
    private final ScheduleReplyMapper scheduleReplyMapper;

    @Transactional
    public Schedule dtoToEntity(ScheduleDto scheduleDto){
        Schedule schedule = (scheduleDto.getId() != null) ?
                scheduleRepository.findById(scheduleDto.getId()).orElseThrow(() -> new ScheduleNotFoundException("해당 스케쥴을 찾을 수 없습니다.")) : new Schedule();

        if(scheduleDto.getUser() != null){
            schedule.setUser(scheduleDto.getUser().getId());
        }else {
            SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");
            User user = userRepository.findById(sessionUser.getId()).orElseThrow(() -> new UserNotFoundException("세션에 유저 정보가 없습니다"));

            schedule.setUser(user.getId());
        }
        if(scheduleDto.getTitle() != null) schedule.setTitle(scheduleDto.getTitle());
        if(scheduleDto.getDescription() != null) schedule.setDescription(schedule.getDescription());
        if(scheduleDto.getThumbnail() != null) schedule.setThumbnail(scheduleDto.getThumbnail());
        if(scheduleDto.getIntervalType() != null) schedule.setIntervalType(scheduleDto.getIntervalType());
        if(scheduleDto.getIntervalValue() != null) schedule.setIntervalValue(scheduleDto.getIntervalValue());
        if(scheduleDto.getStartDate() != null) schedule.setStartDate(scheduleDto.getStartDate());
        if(scheduleDto.getEndDate() != null) schedule.setEndDate(scheduleDto.getEndDate());
        if(scheduleDto.getStatus() != null) schedule.setStatus(scheduleDto.getStatus());

        return schedule;
    }

    public ScheduleDto entityToDto(Schedule schedule){
        User user = userRepository.findById(schedule.getUser()).orElseThrow(() -> new UserNotFoundException("해당 유저를 찾을 수 없습니다."));
        SimpleUserDto simpleUserDto = userMapper.entityToSimpleDto(user);
        List<SimpleUserDto> members = userMapper.entityListToSimpleDtoList(userMapper.memberListToEntityList(schedule.getMembers()));

        ScheduleDto scheduleDto = ScheduleDto
                .builder()
                .id(schedule.getId())
                .user(simpleUserDto)
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

//    public SimpleScheduleDto entityToSimpleDto(Schedule schedule){
//
//    }

    public List<ScheduleDto> entityListToDtoList(List<Schedule> list){
        List<ScheduleDto> scheduleDtos = new LinkedList<>();

        for(Schedule schedule : list){
            ScheduleDto dto = entityToDto(schedule);
            scheduleDtos.add(dto);
        }

        return scheduleDtos;
    }


    public SimpleScheduleDto entityToSimpleDto(Schedule schedule) {
        User user = userRepository.findById(schedule.getUser()).orElseThrow(() -> new UserNotFoundException("글쓴이를 찾을 수 없습니다."));

        return SimpleScheduleDto
                .builder()
                .id(schedule.getId())
                .user(userMapper.entityToSimpleDto(user))
                .title(schedule.getTitle())
                .thumbnail(schedule.getThumbnail())
                .status(schedule.getStatus())
                .build();
    }
}
