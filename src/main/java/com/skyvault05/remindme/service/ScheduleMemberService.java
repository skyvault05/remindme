package com.skyvault05.remindme.service;

import com.skyvault05.remindme.config.security.dto.SessionUser;
import com.skyvault05.remindme.domain.Schedule;
import com.skyvault05.remindme.domain.ScheduleMember;
import com.skyvault05.remindme.domain.User;
import com.skyvault05.remindme.repository.ScheduleMemberRepository;
import com.skyvault05.remindme.repository.UserRepository;
import com.skyvault05.remindme.utils.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
@RequiredArgsConstructor
public class ScheduleMemberService {
    private final HttpSession httpSession;
    private final UserRepository userRepository;
    private final ScheduleMemberRepository scheduleMemberRepository;

    public Boolean addMyself(Schedule schedule){
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");
        User user = userRepository.findById(sessionUser.getId()).orElseThrow(() -> new UserNotFoundException("세션에 유저 정보가 없습니다"));

        if(scheduleMemberRepository.findByScheduleAndMember(schedule, user.getId()) != null) return false;


            ScheduleMember scheduleMember = ScheduleMember
                .builder()
                .member(user.getId())
                .schedule(schedule.getId())
                .build();

        scheduleMemberRepository.save(scheduleMember);

        return true;
    }
}
