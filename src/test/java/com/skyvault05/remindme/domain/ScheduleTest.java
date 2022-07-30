package com.skyvault05.remindme.domain;

import com.skyvault05.remindme.repository.ScheduleReplyRepository;
import com.skyvault05.remindme.repository.ScheduleRepository;
import com.skyvault05.remindme.repository.UserRepository;
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

    @Test
    public void insertSchedule(){
        User user = userRepository.findById(1L).orElse(null);
        User user2 = userRepository.findById(2L).orElse(null);
        LocalDateTime startDate = LocalDateTime.of(2022, 8, 22, 5, 22);
        LocalDateTime endDate = LocalDateTime.of(2022, 9, 11, 2, 15);
        Schedule schedule = Schedule.builder()
                .scheduleTitle("title")
                .scheduleDescription("description")
                .scheduleStartDate(startDate)
                .scheduleEndDate(endDate)
                .scheduleIntervalType("month")
                .scheduleIntervalValue("11")
                .scheduleUser(user)
                .build();

        schedule.getScheduleMember().add(user2);

        scheduleRepository.save(schedule);
    }
}
