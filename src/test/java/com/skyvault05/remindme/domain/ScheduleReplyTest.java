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
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ScheduleReplyTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ScheduleRepository scheduleRepository;
    @Autowired
    ScheduleReplyRepository scheduleReplyRepository;

    @Test
    public void insertScheduleReply(){
        User user = userRepository.findById(2L).orElse(null);
        Schedule schedule = scheduleRepository.findById(1L).orElse(null);
        ScheduleReply scheduleReply = ScheduleReply.builder()
                .scheduleReplyUser(user)
                .schedule(schedule)
                .scheduleReplyDescription("description")
                .build();

        scheduleReplyRepository.save(scheduleReply);

    }

    @Test
    @Transactional
    public void readScheduleReply(){
        Schedule schedule = scheduleRepository.findById(1L).orElse(null);
        System.out.println(schedule.getScheduleReply().size());
        for(ScheduleReply scheduleReply : schedule.getScheduleReply()){
            System.out.println(scheduleReply.getScheduleReplyId()+":"+scheduleReply.getScheduleReplyDescription());
        }
    }
}
