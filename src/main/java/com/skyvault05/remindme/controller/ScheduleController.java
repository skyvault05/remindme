package com.skyvault05.remindme.controller;

import com.skyvault05.remindme.domain.Schedule;
import com.skyvault05.remindme.dto.ScheduleDto;
import com.skyvault05.remindme.mapper.ScheduleMapper;
import com.skyvault05.remindme.service.ScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping(path = "${api}/schedule")
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;
    private final ScheduleMapper scheduleMapper;

    @Operation(summary = "스케쥴 추가 및 업데이트")
    @PostMapping("/storeSchedule")
    public ScheduleDto storeSchedule(@RequestBody ScheduleDto scheduleDto, HttpSession session) {
        ScheduleDto newScheduleDto = scheduleService.storeSchedule(scheduleDto, session);

        return newScheduleDto;
    }

    @GetMapping("/getMySchedule")
    public List<ScheduleDto> getMySchedule(HttpSession session){
        return scheduleService.getMySchedules(session);
    }
}
