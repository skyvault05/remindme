package com.skyvault05.remindme.service;

import com.skyvault05.remindme.config.security.dto.SessionUser;
import com.skyvault05.remindme.domain.Schedule;
import com.skyvault05.remindme.domain.User;
import com.skyvault05.remindme.repository.ScheduleRepository;
import com.skyvault05.remindme.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ScheduleService{
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    public Schedule insertSchedule(Schedule schedule, HttpSession session){
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");
        Long userId = sessionUser.getUserId();
        System.out.println(userId);
        User user = userRepository.findById(userId).orElse(null);
        System.out.println(user);
        schedule.setScheduleUser(user);
        Schedule newSchedule = scheduleRepository.save(schedule);

        return newSchedule;
    }

    public List<Schedule> getMySchedule(HttpSession session){
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");
        User user = userRepository.findById(sessionUser.getUserId()).orElse(null);
        return null;
    }
}
