package com.skyvault05.remindme.service;

import com.skyvault05.remindme.config.security.dto.SessionUser;
import com.skyvault05.remindme.domain.Schedule;
import com.skyvault05.remindme.domain.ScheduleMember;
import com.skyvault05.remindme.domain.User;
import com.skyvault05.remindme.dto.ScheduleDto;
import com.skyvault05.remindme.dto.SimpleUserDto;
import com.skyvault05.remindme.utils.exceptions.ScheduleNotFoundException;
import com.skyvault05.remindme.utils.mapper.ScheduleMapper;
import com.skyvault05.remindme.repository.ScheduleMemberRepository;
import com.skyvault05.remindme.repository.ScheduleRepository;
import com.skyvault05.remindme.repository.UserRepository;
import com.skyvault05.remindme.utils.exceptions.UserNotFoundException;
import com.skyvault05.remindme.utils.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class ScheduleService{
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final ScheduleMemberRepository scheduleMemberRepository;
    private final ScheduleMemberService scheduleMemberService;
    private final ScheduleMapper scheduleMapper;
    private final UserMapper userMapper;

    @Transactional
    public ScheduleDto storeSchedule(ScheduleDto scheduleDto){
        Schedule schedule = scheduleMapper.dtoToEntity(scheduleDto);

        scheduleRepository.save(schedule);
        scheduleMemberService.addMyself(schedule);
        scheduleMemberService.addMembers(schedule, scheduleDto.getMembers());

        log.info(schedule.getUser() + " : " +"Schedule " + schedule + "이 저장되었습니다.");

        ScheduleDto newScheduleDto = scheduleMapper.entityToDto(schedule);
        List<ScheduleMember> scheduleMemberList = scheduleMemberRepository.findAllBySchedule(16L);
        List<SimpleUserDto> simpleUserDtoList = userMapper.scheduleMemberListToSimpleDtoList(scheduleMemberList);
        newScheduleDto.setMembers(simpleUserDtoList);

        return newScheduleDto;
    }

    public List<ScheduleDto> getSchedules(HttpSession session){
        List<ScheduleDto> myScheduleDtos= new LinkedList<>();

        SessionUser sessionUser = (SessionUser) session.getAttribute("user");
        User user = userRepository.findById(sessionUser.getId()).orElseThrow(() -> new UserNotFoundException("세션에 유저 정보가 없습니다"));
        List<ScheduleMember> scheduleMemberList = scheduleMemberRepository.findAllByMember(user.getId());

        Set<ScheduleDto> scheduleDtoSet = new HashSet<>();
        for(ScheduleMember scheduleMember : scheduleMemberList){
            Schedule schedule = scheduleRepository.findById(scheduleMember.getSchedule()).orElseThrow(() -> new ScheduleNotFoundException("해당 스케쥴을 찾을 수 없습니다."));
            ScheduleDto scheduleDto = scheduleMapper.entityToDto(schedule);
            scheduleDtoSet.add(scheduleDto);
        }
        myScheduleDtos.addAll(scheduleDtoSet);

        return myScheduleDtos;
    }

    public List<ScheduleDto> getMySchedules(HttpSession session) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");
        User user = userRepository.findById(sessionUser.getId()).orElseThrow(() -> new UserNotFoundException("세션에 유저 정보가 없습니다"));

        List<Schedule> schedules = scheduleRepository.findAllByUser(user);
        List<ScheduleDto> scheduleDtos = scheduleMapper.entityListToDtoList(schedules);

        return scheduleDtos;
    }
}
