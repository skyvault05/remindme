package com.skyvault05.remindme.domain;

import com.skyvault05.remindme.repository.ScheduleMemberRepository;
import com.skyvault05.remindme.repository.ScheduleRepository;
import com.skyvault05.remindme.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ScheduleMemberTest {
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ScheduleMemberRepository scheduleMemberRepository;

    @Test
    public void insertMember(){
        System.out.println(scheduleRepository);
        Schedule schedule = scheduleRepository.findById(1L).orElse(null);
        User member = userRepository.findById(2L).orElse(null);
        ScheduleMember scheduleMember =
                ScheduleMember
                        .builder()
                        .schedule(schedule)
                        .member(member)
                        .build();
        scheduleMemberRepository.save(scheduleMember);

    }
}
