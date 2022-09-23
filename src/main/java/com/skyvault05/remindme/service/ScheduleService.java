package com.skyvault05.remindme.service;

import com.skyvault05.remindme.config.security.dto.SessionUser;
import com.skyvault05.remindme.domain.Schedule;
import com.skyvault05.remindme.domain.ScheduleMember;
import com.skyvault05.remindme.domain.User;
import com.skyvault05.remindme.dto.ScheduleDto;
import com.skyvault05.remindme.mapper.ScheduleMapper;
import com.skyvault05.remindme.repository.ScheduleMemberRepository;
import com.skyvault05.remindme.repository.ScheduleRepository;
import com.skyvault05.remindme.repository.UserRepository;
import com.skyvault05.remindme.utils.exceptions.UserNotFoundException;
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
    private final ScheduleMapper scheduleMapper;

    @Transactional
    public ScheduleDto storeSchedule(ScheduleDto scheduleDto, HttpSession session){
        Schedule schedule = scheduleMapper.dtoToEntity(scheduleDto);

        SessionUser sessionUser = (SessionUser) session.getAttribute("user");
        Long userId = sessionUser.getId();
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException());

        Schedule newSchedule = scheduleRepository.save(schedule);

        ScheduleDto newScheduleDto = scheduleMapper.entityToDto(newSchedule);

        return newScheduleDto;
    }

    public List<ScheduleDto> getMySchedules(HttpSession session){
        List<ScheduleDto> myScheduleDtos= new LinkedList<>();

        SessionUser sessionUser = (SessionUser) session.getAttribute("user");
        User user = userRepository.findById(sessionUser.getId()).orElseThrow(() -> new UserNotFoundException());

        List<ScheduleMember> scheduleMemberList = scheduleMemberRepository.findALlByMember(user);

        Set<ScheduleDto> scheduleDtoSet = new HashSet<>();

        for(ScheduleMember scheduleMember : scheduleMemberList){
            ScheduleDto scheduleDto = scheduleMapper.entityToDto(scheduleMember.getSchedule());
            scheduleDtoSet.add(scheduleDto);
        }

        myScheduleDtos.addAll(scheduleDtoSet);

        return myScheduleDtos;
    }
}
