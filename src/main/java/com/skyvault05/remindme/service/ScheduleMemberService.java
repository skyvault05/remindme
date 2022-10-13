package com.skyvault05.remindme.service;

import com.skyvault05.remindme.config.security.dto.SessionUser;
import com.skyvault05.remindme.domain.Schedule;
import com.skyvault05.remindme.domain.ScheduleMember;
import com.skyvault05.remindme.domain.User;
import com.skyvault05.remindme.dto.SimpleUserDto;
import com.skyvault05.remindme.repository.ScheduleMemberRepository;
import com.skyvault05.remindme.repository.UserRepository;
import com.skyvault05.remindme.utils.exceptions.UserNotFoundException;
import com.skyvault05.remindme.utils.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ScheduleMemberService {
    private final HttpSession httpSession;
    private final UserRepository userRepository;
    private final ScheduleMemberRepository scheduleMemberRepository;
    private final UserMapper userMapper;

    public Boolean addMyself(Schedule schedule){
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");
        User user = userRepository.findById(sessionUser.getId()).orElseThrow(() -> new UserNotFoundException("세션에 유저 정보가 없습니다"));

        if(scheduleMemberRepository.findByScheduleAndMember(schedule.getId(), user.getId()).orElse(null) != null) return false;


            ScheduleMember scheduleMember = ScheduleMember
                .builder()
                .member(user.getId())
                .schedule(schedule.getId())
                .build();

        scheduleMemberRepository.save(scheduleMember);

        log.info("스케쥴: "+ schedule.getId() + ", 멤버추가: " + scheduleMember.getMember());
        return true;
    }

    @Transactional
    public boolean addMembers(Schedule newSchedule, List<SimpleUserDto> members) {
        if(members == null || members.isEmpty()) return false;

        for(SimpleUserDto simpleUserDto : members){
            ScheduleMember scheduleMember = userMapper.simpleUserDtoToScheduleMember(simpleUserDto, newSchedule.getId());
            scheduleMemberRepository.save(scheduleMember);
            log.info("스케쥴: "+ newSchedule.getId() + ", 멤버추가: " + simpleUserDto.getId());
        }


        return true;
    }

    @Transactional
    public void deleteScheduleMemebers(List<ScheduleMember> list){
        scheduleMemberRepository.deleteInBatch(list);
        for(ScheduleMember scheduleMember : list){
            log.info("스케쥴: "+ scheduleMember.getSchedule() + ", 멤버삭제: " + scheduleMember.getMember());
        }
    }
}
