package com.skyvault05.remindme.mapper;

import com.skyvault05.remindme.config.security.dto.SessionUser;
import com.skyvault05.remindme.domain.Schedule;
import com.skyvault05.remindme.domain.User;
import com.skyvault05.remindme.dto.ScheduleDto;
import com.skyvault05.remindme.dto.UserDto;
import com.skyvault05.remindme.repository.ScheduleRepository;
import com.skyvault05.remindme.repository.UserRepository;
import com.skyvault05.remindme.utils.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.List;

@Component
public class ScheduleMapper {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private ScheduleReplyMapper scheduleReplyMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private HttpSession httpSession;

    @Transactional
    public Schedule dtoToEntity(ScheduleDto scheduleDto){
        Schedule schedule = (scheduleDto.getId() != null) ?
                scheduleRepository.findById(scheduleDto.getId()).orElse(null) : new Schedule();
//        Schedule scheduledd = scheduleRepository.findById(scheduleDto.getId()).orElse(new Schedule());


        if(scheduleDto.getUser() != null){
            schedule.setUser(userMapper.dtoToEntity(scheduleDto.getUser()));
        }else {
            SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");
            Long userId = sessionUser.getId();
            User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException());

            schedule.setUser(user);
        }
        if(scheduleDto.getTitle() != null)schedule.setTitle(scheduleDto.getTitle());
        if(scheduleDto.getDescription() != null)schedule.setDescription(schedule.getDescription());
        if(scheduleDto.getThumbnail() != null)schedule.setThumbnail(scheduleDto.getThumbnail());
        if(scheduleDto.getIntervalType() != null)schedule.setIntervalType(scheduleDto.getIntervalType());
        if(scheduleDto.getIntervalValue() != null)schedule.setIntervalValue(scheduleDto.getIntervalValue());
        if(scheduleDto.getStartDate() != null)schedule.setStartDate(scheduleDto.getStartDate());
        if(scheduleDto.getEndDate() != null)schedule.setEndDate(scheduleDto.getEndDate());
        if(scheduleDto.getStatus() != null)schedule.setStatus(scheduleDto.getStatus());

        return schedule;
    }

    public ScheduleDto entityToDto(Schedule schedule){
        UserDto userDto = userMapper.entityToDto(schedule.getUser());
        List<UserDto> members = userMapper.entityListToDtoList(userMapper.memberListToEntityList(schedule.getMembers()));

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
