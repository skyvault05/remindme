package com.skyvault05.remindme.domain;

import com.skyvault05.remindme.dto.ScheduleDto;
import com.skyvault05.remindme.dto.UserDto;
import com.skyvault05.remindme.mapper.ScheduleMapper;
import com.skyvault05.remindme.mapper.UserMapper;
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
                .intervalValue("11")
                .user(user)
                .thumbnail("scheduleImage.webp")
                .build();


//        schedule.getScheduleMember().add(user2);

        scheduleRepository.save(schedule);
    }

    @Test
    public void readSchedule(){
        Schedule schedule = scheduleRepository.findById(1L).orElse(null);
        System.out.println(schedule);

    }

    @Test
    public void storeScheduleMember(){
        User user = userRepository.findById(10L).orElse(null);
        Schedule schedule = scheduleRepository.findById(2L).orElse(null);

        ScheduleMember scheduleMember = ScheduleMember
                .builder()
                .member(user)
                .schedule(schedule)
                .acceptance(true)
                .build();

        scheduleMemberRepository.save(scheduleMember);
    }
}
