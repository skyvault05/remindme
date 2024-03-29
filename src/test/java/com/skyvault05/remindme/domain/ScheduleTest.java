package com.skyvault05.remindme.domain;

import com.skyvault05.remindme.utils.mapper.UserMapper;
import com.skyvault05.remindme.repository.ScheduleMemberRepository;
import com.skyvault05.remindme.repository.ScheduleReplyRepository;
import com.skyvault05.remindme.repository.ScheduleRepository;
import com.skyvault05.remindme.repository.UserRepository;
import com.skyvault05.remindme.utils.enums.ScheduleIntervalType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ScheduleTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private ScheduleReplyRepository scheduleReplyRepository;
    @Autowired
    private ScheduleMemberRepository scheduleMemberRepository;
    @Autowired
    private UserMapper userMapper;

    @Test
    public void insertSchedule(){
        User user = userRepository.findById(10L).orElse(null);
        LocalDateTime startDate = LocalDateTime.of(2022, 8, 22, 5, 22);
        LocalDateTime endDate = LocalDateTime.of(2022, 9, 11, 2, 15);
        Schedule schedule = Schedule.builder()
                .title("title")
                .description("description")
                .startDate(startDate)
                .endDate(endDate)
                .intervalType(ScheduleIntervalType.MONTHLY)
                .intervalValue(1)
                .user(user.getId())
                .thumbnail("scheduleImage.webp")
                .build();


//        schedule.getScheduleMember().add(user2);

        scheduleRepository.save(schedule);
    }

    @Test
    public void readSchedule(){
        Schedule schedule = scheduleRepository.findById(16L).orElse(null);
        System.out.println(schedule);
        for(ScheduleMember scheduleMember : schedule.getMembers()){
            System.out.println(scheduleMember.getMember() + ":" + scheduleMember.getSchedule());
        }

    }

    @Test
    public void storeScheduleMember(){
        User user = userRepository.findById(10L).orElse(null);
        Schedule schedule = scheduleRepository.findById(2L).orElse(null);

        ScheduleMember scheduleMember = ScheduleMember
                .builder()
                .member(user.getId())
                .schedule(schedule.getId())
                .acceptance(true)
                .build();

        scheduleMemberRepository.save(scheduleMember);
    }
}
