package com.skyvault05.remindme.controller;

import com.skyvault05.remindme.domain.Schedule;
import com.skyvault05.remindme.domain.ScheduleReply;
import com.skyvault05.remindme.dto.ScheduleDto;
import com.skyvault05.remindme.mapper.ScheduleMapper;
import com.skyvault05.remindme.repository.ScheduleRepository;
import com.skyvault05.remindme.repository.UserRepository;
import com.skyvault05.remindme.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping(path = "${api}/schedule")
@RequiredArgsConstructor
public class ScheduleController {
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;
    private final ScheduleService scheduleService;

    @PostMapping("/insertSchedule")
    public Schedule insertSchedule(@RequestBody ScheduleDto scheduleDto, HttpSession session) {
        Schedule schedule = ScheduleMapper.scheduleDtoToSchedule(scheduleDto);
        Schedule newSchedule = scheduleService.insertSchedule(schedule, session);

        return newSchedule;
    }

    @GetMapping("/getMySchedule")
    public List<Schedule> getMySchedule(HttpSession session){
        return scheduleService.getMySchedule(session);
    }

//    @ExceptionHandler(Exception.class)
//    public void handleException(Exception ex){
//        System.out.println(ex.getMessage());
//    }
}
